package com.goby56.strangercraft.utils;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public interface ICoralExtension {

    void killCoral(BlockState state, ServerWorld world, BlockPos pos);

}
