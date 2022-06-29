package com.goby56.strangercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.random.Random;

public class VinelikeTestBlock extends AbstractVinelikeBlock{

//    private final Random clusterID; // Intended to use for dead kelp with roots being on one side of the fallen kelp

    public VinelikeTestBlock(Settings settings, boolean forbidsMultiConnections, int height) {
        super(settings, forbidsMultiConnections, height);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }
}
