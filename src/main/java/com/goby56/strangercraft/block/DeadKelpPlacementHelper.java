package com.goby56.strangercraft.block;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeadKelpPlacementHelper {
    private final World world;
    private final BlockPos pos;
    private final AbstractDeadKelpBlock block;
    private BlockState state;
    private final List<BlockPos> neighbors = Lists.newArrayList();


    public DeadKelpPlacementHelper(World world, BlockPos pos, BlockState state) {
        this.world = world;
        this.pos = pos;
        this.state = state;
        this.block = (AbstractDeadKelpBlock) state.getBlock();
        RailShape shape = state.get(this.block.getShapeProperty());
        this.computeNeighbors(shape);
    }


    public List<BlockPos> getNeighbors() {
        return this.neighbors;
    }

    private void computeNeighbors(RailShape shape) {
        this.neighbors.clear();
        switch (shape) {
            case NORTH_SOUTH: {
                this.neighbors.add(this.pos.north());
                this.neighbors.add(this.pos.south());
                break;
            }
            case EAST_WEST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.east());
                break;
            }
            case ASCENDING_EAST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.east().up());
                break;
            }
            case ASCENDING_WEST: {
                this.neighbors.add(this.pos.west().up());
                this.neighbors.add(this.pos.east());
                break;
            }
            case ASCENDING_NORTH: {
                this.neighbors.add(this.pos.north().up());
                this.neighbors.add(this.pos.south());
                break;
            }
            case ASCENDING_SOUTH: {
                this.neighbors.add(this.pos.north());
                this.neighbors.add(this.pos.south().up());
                break;
            }
            case SOUTH_EAST: {
                this.neighbors.add(this.pos.east());
                this.neighbors.add(this.pos.south());
                break;
            }
            case SOUTH_WEST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.south());
                break;
            }
            case NORTH_WEST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.north());
                break;
            }
            case NORTH_EAST: {
                this.neighbors.add(this.pos.east());
                this.neighbors.add(this.pos.north());
            }
        }
    }

    private void updateNeighborPositions() {
        for (int i = 0; i < this.neighbors.size(); ++i) {
            DeadKelpPlacementHelper deadKelpPlacementHelper = this.getNeighboringKelp(this.neighbors.get(i));
            if (deadKelpPlacementHelper == null || !deadKelpPlacementHelper.isNeighbor(this)) {
                this.neighbors.remove(i--);
                continue;
            }
            this.neighbors.set(i, deadKelpPlacementHelper.pos);
        }
    }

    private boolean isVerticallyNearKelp(BlockPos pos) {
        return AbstractDeadKelpBlock.isDeadKelp(this.world, pos) || AbstractDeadKelpBlock.isDeadKelp(this.world, pos.up()) || AbstractDeadKelpBlock.isDeadKelp(this.world, pos.down());
    }

    @Nullable
    private DeadKelpPlacementHelper getNeighboringKelp(BlockPos pos) {
        BlockPos blockPos = pos;
        BlockState blockState = this.world.getBlockState(blockPos);
        if (AbstractDeadKelpBlock.isDeadKelp(blockState)) {
            return new DeadKelpPlacementHelper(this.world, blockPos, blockState);
        }
        blockPos = pos.up();
        blockState = this.world.getBlockState(blockPos);
        if (AbstractDeadKelpBlock.isDeadKelp(blockState)) {
            return new DeadKelpPlacementHelper(this.world, blockPos, blockState);
        }
        blockPos = pos.down();
        blockState = this.world.getBlockState(blockPos);
        if (AbstractDeadKelpBlock.isDeadKelp(blockState)) {
            return new DeadKelpPlacementHelper(this.world, blockPos, blockState);
        }
        return null;
    }

    private boolean isNeighbor(DeadKelpPlacementHelper other) {
        return this.isNeighbor(other.pos);
    }

    private boolean isNeighbor(BlockPos pos) {
        for (int i = 0; i < this.neighbors.size(); ++i) {
            BlockPos blockPos = this.neighbors.get(i);
            if (blockPos.getX() != pos.getX() || blockPos.getZ() != pos.getZ()) continue;
            return true;
        }
        return false;
    }

    protected int getNeighborCount() {
        int i = 0;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (!this.isVerticallyNearKelp(this.pos.offset(direction))) continue;
            ++i;
        }
        return i;
    }

    private boolean canConnect(DeadKelpPlacementHelper placementHelper) {
        return this.isNeighbor(placementHelper) || this.neighbors.size() != 2;
    }

    private void computeshape(DeadKelpPlacementHelper placementHelper) {
        this.neighbors.add(placementHelper.pos);
        BlockPos blockPos = this.pos.north();
        BlockPos blockPos2 = this.pos.south();
        BlockPos blockPos3 = this.pos.west();
        BlockPos blockPos4 = this.pos.east();
        boolean bl = this.isNeighbor(blockPos);
        boolean bl2 = this.isNeighbor(blockPos2);
        boolean bl3 = this.isNeighbor(blockPos3);
        boolean bl4 = this.isNeighbor(blockPos4);
        RailShape shape = null;
        if (bl || bl2) {
            shape = shape.NORTH_SOUTH;
        }
        if (bl3 || bl4) {
            shape = shape.EAST_WEST;
        }

        if (bl2 && bl4 && !bl && !bl3) {
            shape = shape.SOUTH_EAST;
        }
        if (bl2 && bl3 && !bl && !bl4) {
            shape = shape.SOUTH_WEST;
        }
        if (bl && bl3 && !bl2 && !bl4) {
            shape = shape.NORTH_WEST;
        }
        if (bl && bl4 && !bl2 && !bl3) {
            shape = shape.NORTH_EAST;
        }

        if (shape == shape.NORTH_SOUTH) {
            if (AbstractDeadKelpBlock.isDeadKelp(this.world, blockPos.up())) {
                shape = shape.ASCENDING_NORTH;
            }
            if (AbstractDeadKelpBlock.isDeadKelp(this.world, blockPos2.up())) {
                shape = shape.ASCENDING_SOUTH;
            }
        }
        if (shape == shape.EAST_WEST) {
            if (AbstractDeadKelpBlock.isDeadKelp(this.world, blockPos4.up())) {
                shape = shape.ASCENDING_EAST;
            }
            if (AbstractDeadKelpBlock.isDeadKelp(this.world, blockPos3.up())) {
                shape = shape.ASCENDING_WEST;
            }
        }
        if (shape == null) {
            shape = shape.NORTH_SOUTH;
        }
        this.state = (BlockState)this.state.with(this.block.getShapeProperty(), shape);
        this.world.setBlockState(this.pos, this.state, Block.NOTIFY_ALL);
    }

    private boolean canConnect(BlockPos pos) {
        DeadKelpPlacementHelper deadKelpPlacementHelper = this.getNeighboringKelp(pos);
        if (deadKelpPlacementHelper == null) {
            return false;
        }
        deadKelpPlacementHelper.updateNeighborPositions();
        return deadKelpPlacementHelper.canConnect(this);
    }

    public DeadKelpPlacementHelper updateBlockState(boolean forceUpdate, RailShape shape) {
        boolean bl10;
        boolean bl6;
        BlockPos blockPos = this.pos.north();
        BlockPos blockPos2 = this.pos.south();
        BlockPos blockPos3 = this.pos.west();
        BlockPos blockPos4 = this.pos.east();
        boolean bl = this.canConnect(blockPos);
        boolean bl2 = this.canConnect(blockPos2);
        boolean bl3 = this.canConnect(blockPos3);
        boolean bl4 = this.canConnect(blockPos4);
        RailShape shape2 = null;
        boolean bl5 = bl || bl2;
        boolean bl7 = bl6 = bl3 || bl4;
        if (bl5 && !bl6) {
            shape2 = shape.NORTH_SOUTH;
        }
        if (bl6 && !bl5) {
            shape2 = shape.EAST_WEST;
        }
        boolean bl72 = bl2 && bl4;
        boolean bl8 = bl2 && bl3;
        boolean bl9 = bl && bl4;
        boolean bl11 = bl10 = bl && bl3;

        if (bl72 && !bl && !bl3) {
            shape2 = shape.SOUTH_EAST;
        }
        if (bl8 && !bl && !bl4) {
            shape2 = shape.SOUTH_WEST;
        }
        if (bl10 && !bl2 && !bl4) {
            shape2 = shape.NORTH_WEST;
        }
        if (bl9 && !bl2 && !bl3) {
            shape2 = shape.NORTH_EAST;
        }

        if (shape2 == null) {
            if (bl5 && bl6) {
                shape2 = shape;
            } else if (bl5) {
                shape2 = shape.NORTH_SOUTH;
            } else if (bl6) {
                shape2 = shape.EAST_WEST;
            }

            if (bl10) {
                shape2 = shape.NORTH_WEST;
            }
            if (bl9) {
                shape2 = shape.NORTH_EAST;
            }
            if (bl8) {
                shape2 = shape.SOUTH_WEST;
            }
            if (bl72) {
                shape2 = shape.SOUTH_EAST;
            }

        }
        if (shape2 == shape.NORTH_SOUTH) {
            if (AbstractDeadKelpBlock.isDeadKelp(this.world, blockPos.up())) {
                shape2 = shape.ASCENDING_NORTH;
            }
            if (AbstractDeadKelpBlock.isDeadKelp(this.world, blockPos2.up())) {
                shape2 = shape.ASCENDING_SOUTH;
            }
        }
        if (shape2 == shape.EAST_WEST) {
            if (AbstractDeadKelpBlock.isDeadKelp(this.world, blockPos4.up())) {
                shape2 = shape.ASCENDING_EAST;
            }
            if (AbstractDeadKelpBlock.isDeadKelp(this.world, blockPos3.up())) {
                shape2 = shape.ASCENDING_WEST;
            }
        }
        if (shape2 == null) {
            shape2 = shape;
        }
        this.computeNeighbors(shape2);
        this.state = this.state.with(this.block.getShapeProperty(), shape2);
        if (forceUpdate || this.world.getBlockState(this.pos) != this.state) {
            this.world.setBlockState(this.pos, this.state, Block.NOTIFY_ALL);
            for (int i = 0; i < this.neighbors.size(); ++i) {
                DeadKelpPlacementHelper deadKelpPlacementHelper = this.getNeighboringKelp(this.neighbors.get(i));
                if (deadKelpPlacementHelper == null) continue;
                deadKelpPlacementHelper.updateNeighborPositions();
                if (!deadKelpPlacementHelper.canConnect(this)) continue;
                deadKelpPlacementHelper.computeshape(this);
            }
        }
        return this;
    }

    public BlockState getBlockState() {
        return this.state;
    }
}
