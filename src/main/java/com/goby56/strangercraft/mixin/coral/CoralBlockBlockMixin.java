package com.goby56.strangercraft.mixin.coral;

import com.goby56.strangercraft.utils.ICoralExtension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CoralBlockBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CoralBlockBlock.class)
public abstract class CoralBlockBlockMixin
        extends Block
        implements ICoralExtension {

    public CoralBlockBlockMixin(Settings settings, Block deadCoralBlock) {
        super(settings);
        this.deadCoralBlock = deadCoralBlock;
    }

    @Shadow
    private final Block deadCoralBlock;


    @Override
    public void killCoral(BlockState state, ServerWorld world, BlockPos pos) {
        world.setBlockState(pos, this.deadCoralBlock.getDefaultState(), Block.NOTIFY_LISTENERS);
    }
}
