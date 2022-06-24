package com.goby56.strangercraft.mixin.coral;

import com.goby56.strangercraft.utils.CoralBlockDuck;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CoralFanBlock.class)
public abstract class CoralFanBlockMixin
        extends DeadCoralFanBlock
        implements CoralBlockDuck {

    public CoralFanBlockMixin(Settings settings, Block deadCoralBlock) {
        super(settings);
        this.deadCoralBlock = deadCoralBlock;
    }

    @Shadow
    private final Block deadCoralBlock;

    @Override
    public BlockState getDeadVariant(BlockState state) {
        return this.deadCoralBlock.getDefaultState().with(WATERLOGGED, false);
    }
}
