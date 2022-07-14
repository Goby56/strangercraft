package com.goby56.strangercraft.mixin;

import com.goby56.strangercraft.Strangercraft;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.SimulationDistanceLevelPropagator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.goby56.strangercraft.world.dimension.UpsideDownDimension.UPSIDE_DOWN_DIMENSION_KEY;

@Mixin(ChunkTicketManager.class)
public abstract class ChunkTicketMixin {

    @Shadow @Final
    Long2ObjectMap<ObjectSet<ServerPlayerEntity>> playersByChunkPos;

    @Shadow @Final private ChunkTicketManager.DistanceFromNearestPlayerTracker distanceFromNearestPlayerTracker;

    @Shadow @Final private ChunkTicketManager.NearbyChunkTicketUpdater nearbyChunkTicketUpdater;

    @Shadow @Final private SimulationDistanceLevelPropagator simulationDistanceTracker;

    @Shadow protected abstract int getPlayerSimulationLevel();

    @Inject(method = "handleChunkEnter", at = @At("HEAD"), cancellable = true)
    private void placeTicketInOverworld(ChunkSectionPos pos, ServerPlayerEntity player, CallbackInfo ci) {
        if (player.getWorld().getRegistryKey() == UPSIDE_DOWN_DIMENSION_KEY) {

            ChunkPos chunkPos = pos.toChunkPos();
            long l = chunkPos.toLong();
            this.playersByChunkPos.computeIfAbsent(l, sectionPos -> new ObjectOpenHashSet()).add(player);
            this.distanceFromNearestPlayerTracker.updateLevel(l, 0, true);
            this.nearbyChunkTicketUpdater.updateLevel(l, 0, true);

            ServerChunkManager overworldChunkManager = player.getServer().getOverworld().getChunkManager();

            overworldChunkManager.addTicket(ChunkTicketType.PORTAL, pos.toChunkPos(), this.getPlayerSimulationLevel(), player.getBlockPos());
            Strangercraft.UPSIDE_DOWN_CHUNK_EVENT_HANDLER.delayDistanceTracker(this.simulationDistanceTracker, getPlayerSimulationLevel(), chunkPos);

            ci.cancel();

        }
    }

//    @Redirect(method = "handleChunkEnter", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/SimulationDistanceLevelPropagator;add(Lnet/minecraft/server/world/ChunkTicketType;Lnet/minecraft/util/math/ChunkPos;ILjava/lang/Object;)V"))
//    private <T> void delayPlayerTicket(SimulationDistanceLevelPropagator instance, ChunkTicketType<T> type, ChunkPos pos, int level, T argument) {
//
//        if (player.getWorld().getRegistryKey() == UPSIDE_DOWN_DIMENSION_KEY) {
//            ServerChunkManager overworldChunkManager = player.getServer().getOverworld().getChunkManager();
//            overworldChunkManager.addTicket(ChunkTicketType.PORTAL, pos.toChunkPos(), simulationDistance, player.getBlockPos());
//            /**
//             * This still doesn't seem to be enough, a delay of the ticket sending in the upside down is probably needed to
//             * give time to chunk generation in the overworld.
//             */
//
//            Strangercraft.UPSIDE_DOWN_CHUNK_EVENT_HANDLER.delayPlayerChunkUpdate(10, (ChunkTicketManager)(Object)this , pos, player);
////            MinecraftClient.getInstance().player.sendMessage(Text.of("ADDING TICKET IN OVERWORLD"));
//
////            ci.cancel();
//
//        }
//    }

//    @Inject(method = "handleChunkLeave", at = @At("HEAD"), cancellable = true)
//    private void preventRemovalOfTicket(ChunkSectionPos pos, ServerPlayerEntity player, CallbackInfo ci) {
//        if (player.getWorld().getRegistryKey() == UPSIDE_DOWN_DIMENSION_KEY) {
//            ci.cancel();
//        }
//    }
}
