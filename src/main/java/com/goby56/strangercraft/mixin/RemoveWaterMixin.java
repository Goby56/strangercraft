package com.goby56.strangercraft.mixin;

import com.goby56.strangercraft.tag.ModTags;
import com.goby56.strangercraft.utils.CoralBlockDuck;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FreezeTopLayerFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.goby56.strangercraft.world.dimension.UpsideDownDimension.UPSIDE_DOWN_DIMENSION_KEY;

@Mixin(FreezeTopLayerFeature.class)
public class RemoveWaterMixin {

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void overrideFreezingFeatureStep(FeatureContext<DefaultFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) {
        StructureWorldAccess world = context.getWorld();
        if (world.toServerWorld().getRegistryKey() == UPSIDE_DOWN_DIMENSION_KEY) {
            Random random = Random.create("upside down worldgen".hashCode());
            BlockPos blockPosOrigin = context.getOrigin();
            BlockPos.Mutable surfaceBlockPos = new BlockPos.Mutable();
            BlockPos.Mutable blockPos = new BlockPos.Mutable();
            for (int i = 0; i < 16; ++i) {
                for (int j = 0; j < 16; ++j) {
                    int x = blockPosOrigin.getX() + i;
                    int z = blockPosOrigin.getZ() + j;
                    int y = world.getTopY(Heightmap.Type.MOTION_BLOCKING, x, z);
                    surfaceBlockPos.set(x, y, z);
                    blockPos.set(surfaceBlockPos);
                    for (int k = y; k > -63; --k) {
                        blockPos.set(blockPos).move(Direction.DOWN, 1);
                        BlockState blockState = world.getBlockState(blockPos);
                        if (blockState.isOf(Blocks.WATER) || blockState.isIn(ModTags.ModBlocks.OCEAN_PLANTS)) {
                            // Remove all water
                            world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
                        } else if (blockState.isIn(ModTags.ModBlocks.CORAL)) {
                            CoralBlockDuck coralDuck = (CoralBlockDuck)blockState.getBlock();
                            world.setBlockState(blockPos, coralDuck.getDeadVariant(blockState), Block.NOTIFY_LISTENERS);
                        } else if (blockState.isIn(ModTags.ModBlocks.OCEAN_PLANTS)) {
//                            // Block is of kelp, seagrass, tall seagrass or sea pickle
//                            if (world.getBlockState(blockPos.down(1)).isIn(ModTags.ModBlocks.OCEAN_FLOOR)) {
//                                if (blockState.isOf(Blocks.KELP)) {
//                                    // Place down fallen kelp plant (should also get height of the plant)
//                                } else if (blockState.isOf(Blocks.SEAGRASS) || blockState.isOf(Blocks.TALL_SEAGRASS)) {
//                                    // Replace with dead sea grass
//                                }
//                            }
                        } else if (blockState.getFluidState().isOf(Fluids.WATER)) {
                            world.setBlockState(blockPos, blockState.getBlock().getDefaultState(), Block.NOTIFY_LISTENERS);
                        }
                    }
                }
            }

            cir.setReturnValue(true);

        }
    }
}
