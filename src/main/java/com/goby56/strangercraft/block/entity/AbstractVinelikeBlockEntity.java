package com.goby56.strangercraft.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public class AbstractVinelikeBlockEntity
        extends BlockEntity {

    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty EAST = Properties.EAST;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty WEST = Properties.WEST;
    public static final BooleanProperty UP = Properties.UP;
    public static final BooleanProperty DOWN = Properties.DOWN;

    private DefaultedList<Direction> NORTH_CONNECTIONS = DefaultedList.ofSize(6);
    private DefaultedList<Direction> EAST_CONNECTIONS = DefaultedList.ofSize(6);
    private DefaultedList<Direction> SOUTH_CONNECTIONS = DefaultedList.ofSize(6);
    private DefaultedList<Direction> WEST_CONNECTIONS = DefaultedList.ofSize(6);
    private DefaultedList<Direction> UP_CONNECTIONS = DefaultedList.ofSize(6);
    private DefaultedList<Direction> DOWN_CONNECTIONS = DefaultedList.ofSize(6);

    private Random clusterID; // Intended to use for dead kelp with roots being on one side of the fallen kelp

    public AbstractVinelikeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VINELIKE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);


    }

    @Override
    protected void writeNbt(NbtCompound nbt) {

        super.writeNbt(nbt);
    }
}
