package com.goby56.strangercraft.world.gen;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import java.util.*;

public class ForceLoadedChunkManager {

    private final Map<Long, ArrayList<UUID>> managedChunks;
    private final Set<Long> forceLoadedChunks;

    public ForceLoadedChunkManager() {
        this.managedChunks = new HashMap<>();
        this.forceLoadedChunks = new HashSet<>();
    }

    public void tick(MinecraftServer server) {
        ServerWorld overworld = server.getOverworld();
        for (Long l : managedChunks.keySet()) {
            if (forceLoadedChunks.contains(l)) continue;
            // Chunk is managed but has not been force loaded

            // Force load chunk
            ChunkPos pos = new ChunkPos(l);
            overworld.setChunkForced(pos.x, pos.z, true);
            forceLoadedChunks.add(l);
        }
        Set<Long> toRemove = new HashSet<>();
        for (Long l : forceLoadedChunks) {
            if (managedChunks.containsKey(l)) continue;
            // Chunk is force loaded but is no longer managed

            // Remove force load
            ChunkPos pos = new ChunkPos(l);
            overworld.setChunkForced(pos.x, pos.z, false);
            toRemove.add(l);
        }
        for (Long l : toRemove) {
            forceLoadedChunks.remove(l);
        }
    }

    public void appendChunk(long pos, UUID sender) {
        if (managedChunks.containsKey(pos)) {
            managedChunks.get(pos).add(sender);
        } else {
            ArrayList<UUID> owners = new ArrayList<>();
            owners.add(sender);
            managedChunks.put(pos, owners);
        }
    }

    public void removeDependency(long pos, UUID sender) {
        if (managedChunks.containsKey(pos)) {
            ArrayList<UUID> owners = managedChunks.get(pos);
            if (owners.contains(sender)) {
                owners.remove(sender);
                if (owners.isEmpty()) managedChunks.remove(pos);
            }
        }
    }


}
