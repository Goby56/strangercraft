package com.goby56.strangercraft.mixin.coral;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CoralBlockBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.goby56.strangercraft.world.dimension.UpsideDownDimension.UPSIDE_DOWN_DIMENSION_KEY;
import static net.minecraft.block.CoralParentBlock.WATERLOGGED;

@Mixin(CoralBlockBlock.class)
public class CoralBlockBlockMixin {

    @Shadow
    private final Block deadCoralBlock;

    public CoralBlockBlockMixin(Block deadCoralBlock) {
        this.deadCoralBlock = deadCoralBlock;
    }

    @Inject(method = "scheduledTick", at = @At("TAIL"))
    private void injection(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (world.getRegistryKey() == UPSIDE_DOWN_DIMENSION_KEY && random.nextLong() == Random.create("upside down worldgen".hashCode()).nextLong()) {
            world.setBlockState(pos, this.deadCoralBlock.getDefaultState(), Block.NOTIFY_LISTENERS);
        }
    }
}
