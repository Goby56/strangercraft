package com.goby56.strangercraft.block;

import com.goby56.strangercraft.state.ModProperties;
import com.goby56.strangercraft.utils.RaycastUtil;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class AbstractVinelikeBlock
        extends Block
        implements Waterloggable {

    public static final BooleanProperty UP_FACE = ModProperties.UP_FACE;
    public static final BooleanProperty DOWN_FACE = ModProperties.DOWN_FACE;
    public static final BooleanProperty NORTH_FACE = ModProperties.NORTH_FACE;
    public static final BooleanProperty EAST_FACE = ModProperties.EAST_FACE;
    public static final BooleanProperty SOUTH_FACE = ModProperties.SOUTH_FACE;
    public static final BooleanProperty WEST_FACE = ModProperties.WEST_FACE;

    public static final BooleanProperty UP_CONNECTION = ModProperties.UP_CONNECTION;
    public static final BooleanProperty DOWN_CONNECTION = ModProperties.DOWN_CONNECTION;
    public static final BooleanProperty NORTH_CONNECTION = ModProperties.NORTH_CONNECTION;
    public static final BooleanProperty EAST_CONNECTION = ModProperties.EAST_CONNECTION;
    public static final BooleanProperty SOUTH_CONNECTION = ModProperties.SOUTH_CONNECTION;
    public static final BooleanProperty WEST_CONNECTION = ModProperties.WEST_CONNECTION;

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private final boolean forbidsMultiConnections;
    private final int height;

    public AbstractVinelikeBlock(Settings settings, boolean forbidsMultiConnections, int height) {
        super(settings);
        this.height = height;
        this.forbidsMultiConnections = forbidsMultiConnections; // For dead kelp, because it should not form any connections, it should be continuous
        for (Property<?> property : this.getStateManager().getProperties()) {
            if (property instanceof BooleanProperty boolProp) {
                this.setDefaultState(this.getDefaultState().with(boolProp, false));
            }
        }
        this.setDefaultState(this.getDefaultState()
                .with(WATERLOGGED, false)
                .with(UP_FACE, true)
                .with(UP_CONNECTION, true)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        for (Direction direction : DIRECTIONS) {
            builder.add(directionToProperty(direction, "face"));
            builder.add(directionToProperty(direction, "connection"));
        }
    }

    public boolean isContinuous() {
        return this.forbidsMultiConnections;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos());

        if (!state.isOf(this)) {
            state = this.getDefaultState()
                    .with(UP_FACE, false)
                    .with(UP_CONNECTION, false);
        }

        MinecraftClient instance = MinecraftClient.getInstance();
        Direction direction = RaycastUtil.getBlockFace(instance.crosshairTarget, instance.player.getEyePos());

        state = state.with(directionToProperty(direction, "face"), true);
        state = state.with(directionToProperty(direction.getOpposite(), "connection"), true);

        // Add waterlogging capabilities
        return state;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();

        for (Direction direction : DIRECTIONS) {
            if (state.get(directionToProperty(direction, "face"))) {
                shape = VoxelShapes.union(shape, getShape(direction));
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
        for (Direction direction : DIRECTIONS) {
            BlockState neighborState = world.getBlockState(pos.offset(direction));
            if (!neighborState.isOf(this)) continue;
            state = getUpdatedState(state, neighborState, direction);
        }
        world.setBlockState(pos, state, NOTIFY_LISTENERS);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (MinecraftClient.getInstance() != null && !MinecraftClient.getInstance().player.isCreative()) {
            dropStack((World)world, pos, new ItemStack(this.asItem(), facesOccupied(state)));
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        BlockState neighborState = world.getBlockState(pos);
        Direction direction = Direction.fromVector(sourcePos);
        MinecraftClient.getInstance().player.sendMessage(Text.of(direction.asString()));
        state = getUpdatedState(state, neighborState, direction);

        world.setBlockState(pos, state, NOTIFY_LISTENERS);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return getUpdatedState(state, neighborState, direction);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.PUSH_ONLY;
    }

    public BlockState getUpdatedState(BlockState state, BlockState neighborState, Direction sourceDirection) {
        if (!neighborState.isOf(this)) return state;
        for (Direction direction : DIRECTIONS) {
            if (state.get(directionToProperty(direction, "face")) & neighborState.get(directionToProperty(direction, "face"))) {
                // Neighbor and current block is on same face and should connect
                state = state.with(directionToProperty(sourceDirection, "connection"), true);
            }
        }
        return state;
    }

    public static boolean canGrowOn(BlockView world, Direction direction, BlockPos pos, BlockState state) {
        return Block.isFaceFullSquare(state.getSidesShape(world, pos), direction.getOpposite()) || Block.isFaceFullSquare(state.getCollisionShape(world, pos), direction.getOpposite());
    }

    public int facesOccupied(BlockState state) {
        int i = 0;
        for (Direction direction : DIRECTIONS) {
            if (!state.get(directionToProperty(direction, "face"))) continue;
            i++;
        }
        return i;
    }

    private VoxelShape getShape(Direction face) {
        switch (face) {
            case UP: {
                return Block.createCuboidShape(0,0, 0, 16, height, 16);
            }
            case DOWN: {
                return Block.createCuboidShape(0,16 - height, 0, 16, 16, 16);
            }
            case NORTH: {
                return Block.createCuboidShape(0,0, 16 - height, 16, 16, 16);
            }
            case EAST: {
                return Block.createCuboidShape(0,0, 0, height, 16, 16);
            }
            case SOUTH: {
                return Block.createCuboidShape(0,0, 0, 16, 16, height);
            }
            case WEST: {
                return Block.createCuboidShape(16 - height,0, 0, 16, 16, 16);
            }
            default: {
                throw new IllegalArgumentException("Argument 0 needs to be of  either FLOOR, CEILING, NORTH, EAST, SOUTH or WEST not " + face);
            }
        }
    }

    public static BooleanProperty directionToProperty(Direction direction, String type) {
        switch (direction) {
            case UP: {
                return type == "face" ? AbstractVinelikeBlock.UP_FACE : AbstractVinelikeBlock.UP_CONNECTION;
            }
            case DOWN: {
                return type == "face" ? AbstractVinelikeBlock.DOWN_FACE : AbstractVinelikeBlock.DOWN_CONNECTION;
            }
            case NORTH: {
                return type == "face" ? AbstractVinelikeBlock.NORTH_FACE : AbstractVinelikeBlock.NORTH_CONNECTION;
            }
            case EAST: {
                return type == "face" ? AbstractVinelikeBlock.EAST_FACE : AbstractVinelikeBlock.EAST_CONNECTION;
            }
            case SOUTH: {
                return type == "face" ? AbstractVinelikeBlock.SOUTH_FACE : AbstractVinelikeBlock.SOUTH_CONNECTION;
            }
            case WEST: {
                return type == "face" ? AbstractVinelikeBlock.WEST_FACE : AbstractVinelikeBlock.WEST_CONNECTION;
            }
            default: {
                throw new IllegalArgumentException("Argument 0 is not a valid Direction");
            }
        }

    }

    public static Direction propertyToDirection(BooleanProperty property) {
        if (property == UP_FACE || property == UP_CONNECTION) {
            return Direction.UP;
        } else if (property == DOWN_FACE || property == DOWN_CONNECTION) {
            return Direction.DOWN;
        } else if (property == NORTH_FACE || property == NORTH_CONNECTION) {
            return Direction.NORTH;
        } else if (property == EAST_FACE || property == EAST_CONNECTION) {
            return Direction.EAST;
        } else if (property == SOUTH_FACE || property == SOUTH_CONNECTION) {
            return Direction.SOUTH;
        } else if (property == WEST_FACE || property == WEST_CONNECTION) {
            return Direction.WEST;
        }
        throw new IllegalArgumentException("This property can not be turned into a Direction");
    }







}
