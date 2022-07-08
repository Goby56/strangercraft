package com.goby56.strangercraft.mixin;

import com.goby56.strangercraft.Strangercraft;
import com.goby56.strangercraft.world.dimension.UpsideDownDimension;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

import static com.goby56.strangercraft.world.dimension.UpsideDownDimension.UPSIDE_DOWN_DIMENSION_KEY;

@Mixin(ServerChunkManager.class)
public abstract class TicketDelayerMixin {

    @Shadow
    final ServerWorld world;
    final ChunkTicketManager ticketManager;

    public TicketDelayerMixin(ServerWorld world, ChunkTicketManager ticketManager) {
        this.world = world;
        this.ticketManager = ticketManager;
    }

    @Inject(method = "addTicket", at = @At("HEAD"), cancellable = true)
    private <T> void delayTicket(ChunkTicketType<T> ticketType, ChunkPos pos, int radius, T argument, CallbackInfo ci) {
        if (this.world.getRegistryKey() == UPSIDE_DOWN_DIMENSION_KEY) {
            Strangercraft.UPSIDE_DOWN_CHUNK_GENERATOR.delayTicket(40, this.ticketManager, ticketType, pos, radius, argument);
            ci.cancel();
        }
    }
}
