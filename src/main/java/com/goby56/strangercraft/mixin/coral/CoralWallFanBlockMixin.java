package com.goby56.strangercraft.mixin.coral;

import com.goby56.strangercraft.utils.ICoralExtension;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CoralWallFanBlock.class)
public abstract class CoralWallFanBlockMixin
        extends DeadCoralWallFanBlock
        implements ICoralExtension {

    public CoralWallFanBlockMixin(Settings settings, Block deadCoralBlock) {
        super(settings);
        this.deadCoralBlock = deadCoralBlock;
    }

    @Shadow
    private final Block deadCoralBlock;

    @Override
    public void killCoral(BlockState state, ServerWorld world, BlockPos pos) {
        world.setBlockState(pos, this.deadCoralBlock.getDefaultState().with(WATERLOGGED, false).with(FACING, state.get(FACING)), Block.NOTIFY_LISTENERS);
    }
}