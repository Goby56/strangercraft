package com.goby56.strangercraft.mixin.coral;

import com.goby56.strangercraft.utils.CoralBlockDuck;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CoralBlock;
import net.minecraft.block.CoralParentBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CoralBlock.class)
public abstract class CoralBlockMixin
        extends CoralParentBlock
        implements CoralBlockDuck {

    public CoralBlockMixin(Settings settings, Block deadCoralBlock) {
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
