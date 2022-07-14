package com.goby56.strangercraft.mixin;

import com.goby56.strangercraft.utils.WorldSaveUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FreezeTopLayerFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;

import static com.goby56.strangercraft.world.dimension.UpsideDownDimension.UPSIDE_DOWN_DIMENSION_KEY;

@Mixin(FreezeTopLayerFeature.class)
public class CopyChunkMixin {

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void overrideFreezingFeatureStep(FeatureContext<DefaultFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) throws IOException {
        StructureWorldAccess world = context.getWorld();
        MinecraftServer server = world.getServer();
        if (world.toServerWorld().getRegistryKey().equals(UPSIDE_DOWN_DIMENSION_KEY)) {
            BlockPos originPos = context.getOrigin();

//            NbtCompound chunkNbtCompound = WorldSaveUtil.getOverworldChunkCompound(server, originPos);
//
//            if (chunkNbtCompound == null) {
//                return null;
//            }
//
//            ServerWorld overworld = server.getOverworld();
//
//            ProtoChunk chunk = ChunkSerializer.deserialize(overworld, overworld.getPointOfInterestStorage(), new ChunkPos(originPos), chunkNbtCompound);

            ServerWorld overworld = world.getServer().getOverworld();
            if (overworld == null) {
                cir.setReturnValue(false);
                return;
            }
            int chunkX = originPos.getX() >> 4, chunkZ = originPos.getZ() >> 4;


            ServerChunkManager overworldChunkManager = overworld.getChunkManager();



            BlockView overworldChunkView = overworldChunkManager.getChunk(chunkX, chunkZ);
//            overworldChunkManager.getChunkFutureSyncOnMainThread(chunkX, chunkZ, ChunkStatus.FULL, true)
//                    .thenApply(UpsideDownChunkGenerator::getChunk)
//                    .thenAccept();

//            Chunk chunk = CompletableFuture.supplyAsync(() -> overworldChunkManager.getChunk(chunkX, chunkZ, ChunkStatus.LIGHT, true))
//                    .thenAccept();



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
