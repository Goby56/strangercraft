package com.goby56.strangercraft.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FreezeTopLayerFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.goby56.strangercraft.world.dimension.UpsideDownDimension.UPSIDE_DOWN_DIMENSION_KEY;

@Mixin(FreezeTopLayerFeature.class)
public class CopyChunkMixin {

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void overrideFreezingFeatureStep(FeatureContext<DefaultFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) {
        StructureWorldAccess world = context.getWorld();
        if (world.toServerWorld().getRegistryKey() == UPSIDE_DOWN_DIMENSION_KEY) {
            BlockPos originPos = context.getOrigin();

            ServerWorld overworld = world.getServer().getWorld(World.OVERWORLD);
            if (overworld == null) {
                cir.setReturnValue(true);
                return;
            }

            ServerChunkManager chunkManager = overworld.getChunkManager();

            int chunkX = originPos.getX() / 16, chunkZ = originPos.getZ() / 16;

            BlockView overworldChunkView = chunkManager.getChunk(chunkX, chunkZ);

            if (overworldChunkView == null) {
                cir.setReturnValue(true);
                return;
            }

            BlockPos.Mutable topBlockPos = new BlockPos.Mutable();
            BlockPos.Mutable blockPos = new BlockPos.Mutable();
            for (int i = 0; i < 16; ++i) {
                for (int j = 0; j < 16; ++j) {
                    int x = originPos.getX() + i;
                    int z = originPos.getZ() + j;
                    int y = overworldChunkView.getTopSectionCoord()*16;

                    topBlockPos.set(x, y, z);
                    blockPos.set(topBlockPos);
                    for (int k = y; k > -63; --k) {
                        blockPos.set(blockPos).move(Direction.DOWN, 1);
                        BlockState blockState = overworldChunkView.getBlockState(blockPos);
                        world.setBlockState(blockPos, blockState, Block.NOTIFY_LISTENERS);
                    }
                }
            }
            cir.setReturnValue(true);
        }
    }
}
