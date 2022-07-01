package com.goby56.strangercraft.block;

import com.goby56.strangercraft.block.entity.AbstractVinelikeBlockEntity;
import com.goby56.strangercraft.block.enums.ConnectionType;
import com.goby56.strangercraft.state.ModProperties;
import com.goby56.strangercraft.utils.RaycastUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AbstractVinelikeBlock
        extends Block
        implements Waterloggable {

    public static final EnumProperty<ConnectionType> NORTH_MODEL = ModProperties.NORTH_MODEL;
    public static final EnumProperty<ConnectionType> EAST_MODEL = ModProperties.EAST_MODEL;
    public static final EnumProperty<ConnectionType> SOUTH_MODEL = ModProperties.SOUTH_MODEL;
    public static final EnumProperty<ConnectionType> WEST_MODEL = ModProperties.WEST_MODEL;
    public static final EnumProperty<ConnectionType> CEILING_MODEL = ModProperties.CEILING_MODEL;
    public static final EnumProperty<ConnectionType> FLOOR_MODEL = ModProperties.FLOOR_MODEL;

    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0,0, 0, 16, 2, 16);
    private static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(0,16 - 2, 0, 16, 16, 16);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0,0, 16 - 2, 16, 16, 16);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0,0, 0, 2, 16, 16);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0,0, 0, 16, 16, 2);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(16 - 2,0, 0, 16, 16, 16);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private final boolean forbidsMultiConnections;
    private final int height;

    public AbstractVinelikeBlock(Settings settings, boolean forbidsMultiConnections, int height) {
        super(settings);
        this.height = height;
        this.forbidsMultiConnections = forbidsMultiConnections; // For dead kelp, because it should not form any connections, it should be continuous

//        this.UP_SHAPE =  Block.createCuboidShape(0,0, 0, 16, height, 16);
//        this.DOWN_SHAPE = Block.createCuboidShape(0,16 - height, 0, 16, 16, 16);
//        this.NORTH_SHAPE = Block.createCuboidShape(0,0, 16 - height, 16, 16, 16);
//        this.EAST_SHAPE = Block.createCuboidShape(0,0, 0, height, 16, 16);
//        this.SOUTH_SHAPE = Block.createCuboidShape(0,0, 0, 16, 16, height);
//        this.WEST_SHAPE =  Block.createCuboidShape(16 - height,0, 0, 16, 16, 16);

        for (Property<?> property : this.getStateManager().getProperties()) {
            if (property instanceof EnumProperty enumProperty) {
                this.setDefaultState(this.getDefaultState().with(enumProperty, ConnectionType.NONE));
            }
        }
        this.setDefaultState(this.getDefaultState()
                .with(WATERLOGGED, false)
                .with(FLOOR_MODEL, ConnectionType.STUB)
        );
    }

//    /**
//     * BlockEntity implementation
//     */
//    @Override
//    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
//        return new AbstractVinelikeBlockEntity(pos, state);
//    }
//
//    @Override
//    public BlockRenderType getRenderType(BlockState state) {
//        return BlockRenderType.MODEL;
//    }

    /**
     * Block overrides
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH_MODEL);
        builder.add(EAST_MODEL);
        builder.add(SOUTH_MODEL);
        builder.add(WEST_MODEL);
        builder.add(CEILING_MODEL);
        builder.add(FLOOR_MODEL);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos());

        if (!state.isOf(this)) {
            state = this.getDefaultState().with(FLOOR_MODEL, ConnectionType.NONE);
        }

        MinecraftClient instance = MinecraftClient.getInstance();
        Direction blockFace = RaycastUtil.getBlockFace(instance.crosshairTarget, instance.player.getEyePos());

        state = state.with(directionToProperty(blockFace.getOpposite()), getConnectionModel(blockFace.getOpposite(), ctx.getWorld(), ctx.getBlockPos()));

        // Add waterlogging capabilities
        return state;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();

        for (Direction face : DIRECTIONS) {
            if (state.get(directionToProperty(face)) != ConnectionType.NONE) {
                shape = VoxelShapes.union(shape, getShape(face));
            }
        }

        if (shape.isEmpty()) {
            return getShape(Direction.UP);
        }
        return shape;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {

        // NEED TO CHECK I BLOCK IS BEING PLACED WITH COMMANDS. (NO CROSSHAIR TARGET WILL EXIST)

        MinecraftClient instance = MinecraftClient.getInstance();
        HitResult target = instance.crosshairTarget;

        BlockPos lookingAtBlock = ( (BlockHitResult) target ).getBlockPos();
        Direction direction = RaycastUtil.getBlockFace(target, instance.player.getEyePos());

        if (world.getBlockState(lookingAtBlock).isFullCube(world, lookingAtBlock) || state.isOf(Blocks.GRASS) || world.getBlockState(lookingAtBlock).isOf(Blocks.GRASS)){
            return true;
        }

        return false;
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().isOf(this.asItem()) || super.canReplace(state, context);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        state = getUpdatedState(state, world, pos);
        world.setBlockState(pos, state, NOTIFY_LISTENERS);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (MinecraftClient.getInstance() != null && !MinecraftClient.getInstance().player.isCreative()) {
            dropStack((World)world, pos, new ItemStack(this.asItem(), getNumberOfOccupiedFaces(state)));
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return getUpdatedState(state, (World) world, pos);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.PUSH_ONLY;
    }

    /**
     * Added helper methods
     */
    public BlockState getUpdatedState(BlockState state, World world, BlockPos pos) {
        for (Direction face : getOccupiedFaces(state)) {
            state = state.with(directionToProperty(face), getConnectionModel(face, world, pos));
        }
        return state;
    }

    public ConnectionType getConnectionModel(Direction face, World world, BlockPos pos) {
        LinkedHashMap<Direction, Boolean> connections = getConnectionsOnFace(face, world, pos);
        return connectionTypeOf(face, connections);
    }

    public LinkedHashMap<Direction, Boolean> getConnectionsOnFace(Direction face, World world, BlockPos pos) {
        LinkedHashMap<Direction, Boolean> connections = new LinkedHashMap<>();
        for (Direction direction : DIRECTIONS) {
            if (direction == face || direction == face.getOpposite()) continue;

            BlockState neighboringBlockState = world.getBlockState(pos.offset(direction));
            BlockState cornerBlockState = world.getBlockState(pos.offset(direction).offset(face.getOpposite()));

            if (!neighboringBlockState.isOf(this) & !cornerBlockState.isOf(this)) continue;

            if (neighboringBlockState.get(directionToProperty(face)) != ConnectionType.NONE) {
                connections.put(direction, true);
            } else if (cornerBlockState.get(directionToProperty(direction)) != ConnectionType.NONE) {
                connections.put(direction, true);
            } else {
                connections.put(direction, false);
            }
        }
        return connections;
    }

    public ConnectionType connectionTypeOf(Direction face, LinkedHashMap<Direction, Boolean> connections) {
        int i = 0;
        for (boolean value : connections.values()) {
            if (value) i++;
        }

        if (i == 1) return ConnectionType.STUB;
        if (i == 2) {
            for (Direction direction : DIRECTIONS) {
                if (direction == face || direction == face.getOpposite()) continue;
                if (connections.get(direction) & connections.get(direction.getOpposite())) {
                    return ConnectionType.getStraight(direction);
                }
            }
        }
        if (i == 3) {
            Boolean[] b = (Boolean[]) connections.values().toArray();
            if (b[0] & b[1] & b[2]) return ConnectionType.T_JUNCTION_1;
            if (b[1] & b[2] & b[3]) return ConnectionType.T_JUNCTION_2;
            if (b[2] & b[3] & b[0]) return ConnectionType.T_JUNCTION_3;
            if (b[3] & b[0] & b[1]) return ConnectionType.T_JUNCTION_4;
        }
        if (i == 4) return ConnectionType.CROSSING;

        return ConnectionType.NONE;
    }

    public ArrayList<Direction> getOccupiedFaces(BlockState state) {
        ArrayList<Direction> occupiedFaces = new ArrayList<>();
        for (Direction face : DIRECTIONS) {
            if (state.get(directionToProperty(face)) != ConnectionType.NONE) continue;
            occupiedFaces.add(face);
        }
        return occupiedFaces;
    }

    public static boolean canGrowOn(BlockView world, Direction direction, BlockPos pos, BlockState state) {
        return Block.isFaceFullSquare(state.getSidesShape(world, pos), direction.getOpposite()) || Block.isFaceFullSquare(state.getCollisionShape(world, pos), direction.getOpposite());
    }

    public boolean isContinuous() {
        return this.forbidsMultiConnections;
    }

    /**
     * returns the cardinal directions given a direction of a plane
     * e.g down would give north, east, south and west
     * (the standard cardinal direction if you would stand on the ground)
     * additionally, west would result in up, south, down, and north
     */
    public ArrayList<Direction> getPlaneDirections(Direction face) {
        ArrayList<Direction> planeDirections = new ArrayList<>();
        for (Direction direction : DIRECTIONS) {
            if (direction == face || direction == face.getOpposite()) continue;
            planeDirections.add(direction);
        }
        return planeDirections;
    }


    public int getNumberOfOccupiedFaces(BlockState state) {
        int i = 0;
        for (Direction face : DIRECTIONS) {
            if (state.get(directionToProperty(face)) != ConnectionType.NONE) continue;
            i++;
        }
        return i;
    }

    private VoxelShape getShape(Direction face) {
        switch (face) {
            case UP: return this.UP_SHAPE;
            case DOWN: return this.DOWN_SHAPE;
            case NORTH: return this.NORTH_SHAPE;
            case EAST: return this.EAST_SHAPE;
            case SOUTH: return this.SOUTH_SHAPE;
            case WEST: return this.WEST_SHAPE;
            default: throw new IllegalArgumentException("The provided 'face' is not of 'Direction'!");
        }
    }

    public static EnumProperty<ConnectionType> directionToProperty(Direction direction) {
        switch (direction) {
            case UP: return CEILING_MODEL;
            case DOWN: return FLOOR_MODEL;
            case NORTH: return NORTH_MODEL;
            case EAST: return EAST_MODEL;
            case SOUTH: return SOUTH_MODEL;
            case WEST: return WEST_MODEL;
            default: throw new IllegalArgumentException("The provided 'direction' is not of 'Direction'!");
        }
    }
}
