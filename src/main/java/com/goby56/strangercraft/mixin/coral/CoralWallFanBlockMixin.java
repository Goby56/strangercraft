package com.goby56.strangercraft.mixin.coral;

import com.goby56.strangercraft.utils.CoralBlockDuck;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CoralWallFanBlock.class)
public abstract class CoralWallFanBlockMixin
        extends DeadCoralWallFanBlock
        implements CoralBlockDuck {

    public CoralWallFanBlockMixin(Settings settings, Block deadCoralBlock) {
        super(settings);
        this.deadCoralBlock = deadCoralBlock;
    }

    @Shadow
    private final Block deadCoralBlock;

    @Override
    public BlockState getDeadVariant(BlockState state) {
        return this.deadCoralBlock.getDefaultState().with(WATERLOGGED, false).with(FACING, state.get(FACING));
    }
}