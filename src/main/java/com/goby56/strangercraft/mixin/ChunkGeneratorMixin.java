package com.goby56.strangercraft.mixin;

import com.goby56.strangercraft.world.dimension.UpsideDownDimension;
import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.goby56.strangercraft.world.dimension.UpsideDownDimension.UPSIDE_DOWN_DIMENSION_KEY;

@Mixin(NoiseChunkGenerator.class)
public abstract class ChunkGeneratorMixin {

    @Inject(at = @At("HEAD"), cancellable = true, method = "populateNoise(Lnet/minecraft/world/gen/chunk/Blender;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/noise/NoiseConfig;Lnet/minecraft/world/chunk/Chunk;II)Lnet/minecraft/world/chunk/Chunk;")
    private void placeOverworldChunk(Blender blender, StructureAccessor structureAccessor, NoiseConfig noiseConfig, Chunk chunk, int i, int j, CallbackInfoReturnable<Chunk> cir) {
        if (MinecraftClient.getInstance().world != null) {
            if (MinecraftClient.getInstance().world.getRegistryKey() == UPSIDE_DOWN_DIMENSION_KEY) {
                // The player has entered a world and is in the upside down
            }
        }
    }

}
