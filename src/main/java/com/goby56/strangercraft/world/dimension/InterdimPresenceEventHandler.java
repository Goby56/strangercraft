package com.goby56.strangercraft.world.dimension;

import com.goby56.strangercraft.entity.InterdimensionalPresenceEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import static com.goby56.strangercraft.world.dimension.UpsideDownDimension.UPSIDE_DOWN_DIMENSION_KEY;

public class InterdimPresenceEventHandler {

    public static void onPlayerChangeDimension(ServerPlayerEntity player, ServerWorld origin, ServerWorld destination) {
        MinecraftServer server = origin.getServer();
        ServerWorld overworld = server.getOverworld();
        if (destination.getRegistryKey() == UPSIDE_DOWN_DIMENSION_KEY) {
            double x = player.getX(), y = player.getY(), z = player.getZ();
            InterdimensionalPresenceEntity upsideDownPresence = new InterdimensionalPresenceEntity(server.getOverworld(), x, y, z, (LivingEntity)player);
            overworld.spawnEntity(upsideDownPresence);
        }
    }

    public static void onPlayerLeave() {

    }

    public static void onPlayerJoin() {

    }
}
