package com.goby56.strangercraft.block;

import com.goby56.strangercraft.tag.ModTags;
import net.minecraft.block.*;
import net.minecraft.block.enums.RailShape;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class AbstractDeadKelpBlock
        extends Block
        implements Waterloggable {

    protected static final VoxelShape STRAIGHT_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
    protected static final VoxelShape ASCENDING_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public AbstractDeadKelpBlock(Settings settings) {
        super(settings);
    }

    public static boolean isDeadKelp(World world, BlockPos pos) {
        return AbstractDeadKelpBlock.isDeadKelp(world.getBlockState(pos));
    }

    public static boolean isDeadKelp(BlockState state) {
        // Switch to isIn and add ModTags.Blocks.DEAD_KELP maybe
        return state.isOf(ModBlocks.DEAD_KELP) && state.getBlock() instanceof AbstractDeadKelpBlock;
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        RailShape kelpShape;
        RailShape kelpShape2 = kelpShape = state.isOf(this) ? state.get(this.getShapeProperty()) : null;
        if (kelpShape != null && kelpShape.isAscending()) {
            return ASCENDING_SHAPE;
        }
        return STRAIGHT_SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return AbstractDeadKelpBlock.hasTopRim(world, pos.down());
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        this.updateCurves(state, world, pos, notify);
    }

    protected BlockState updateCurves(BlockState state, World world, BlockPos pos, boolean notify) {
        state = this.updateBlockState(world, pos, state, true);
        return state;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world.isClient || !world.getBlockState(pos).isOf(this)) {
            return;
        }
        RailShape kelpShape = state.get(this.getShapeProperty());
        if (AbstractDeadKelpBlock.shouldDropKelp(pos, world, kelpShape)) {
            AbstractDeadKelpBlock.dropStacks(state, world, pos);
            world.removeBlock(pos, notify);
        } else {
            this.updateBlockState(world, pos, state, notify);
        }
    }

    /**
     * Checks if this rail should be dropped.
     *
     * <p>This method will return true if:
     * <ul><li>The rail block is ascending.</li>
     * <li>The block in the direction of ascent does not have a top rim.</li></ul>
     */
    private static boolean shouldDropKelp(BlockPos pos, World world, RailShape shape) {
        if (!AbstractDeadKelpBlock.hasTopRim(world, pos.down())) {
            return true;
        }
        switch (shape) {
            case ASCENDING_EAST: {
                return !AbstractDeadKelpBlock.hasTopRim(world, pos.east());
            }
            case ASCENDING_WEST: {
                return !AbstractDeadKelpBlock.hasTopRim(world, pos.west());
            }
            case ASCENDING_NORTH: {
                return !AbstractDeadKelpBlock.hasTopRim(world, pos.north());
            }
            case ASCENDING_SOUTH: {
                return !AbstractDeadKelpBlock.hasTopRim(world, pos.south());
            }
        }
        return false;
    }

    protected BlockState updateBlockState(World world, BlockPos pos, BlockState state, boolean forceUpdate) {
        if (world.isClient) {
            return state;
        }
        RailShape kelpShape = state.get(this.getShapeProperty());
        return new DeadKelpPlacementHelper(world, pos, state).updateBlockState(forceUpdate, kelpShape).getBlockState();
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.NORMAL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (moved) {
            return;
        }
        super.onStateReplaced(state, world, pos, newState, moved);
        if (state.get(this.getShapeProperty()).isAscending()) {
            world.updateNeighborsAlways(pos.up(), this);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        BlockState blockState = super.getDefaultState();
        Direction direction = ctx.getPlayerFacing();
        boolean bl2 = direction == Direction.EAST || direction == Direction.WEST;
        return blockState.with(this.getShapeProperty(), bl2 ? RailShape.EAST_WEST : RailShape.NORTH_SOUTH).with(WATERLOGGED, bl);
    }

    protected abstract void updateBlockState(BlockState state, World world, BlockPos pos, Block neighbor);

    public abstract Property<RailShape> getShapeProperty();

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }
}
