package com.goby56.strangercraft.world.gen;


import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.SimulationDistanceLevelPropagator;


import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChunkEventHandler {

    private final CopyOnWriteArrayList<PendingChunkTicket> pendingUpdates;
//    private final List<PendingPlayerChunkUpdate> pendingUpdates;

    public ChunkEventHandler() {
        this.pendingUpdates = new CopyOnWriteArrayList<>();
//        this.pendingUpdates = Collections.synchronizedList(new ArrayList<>());
    }

    public void tick(MinecraftServer minecraftServer) {
        System.out.println(this.pendingUpdates.size());

        ArrayList<PendingChunkTicket> toRemove = new ArrayList<>();

        for (PendingChunkTicket pendingUpdate : this.pendingUpdates) {
            pendingUpdate.tick();
            if (pendingUpdate.tickDelay < 0) {
                toRemove.add(pendingUpdate);
            }
        }

        for (PendingChunkTicket expiredTicket : toRemove) {
            pendingUpdates.remove(expiredTicket);
        }

    }

    public void delayDistanceTracker(SimulationDistanceLevelPropagator simulationDistanceTracker, int simulationDistance, ChunkPos chunkPos) {
        PendingChunkTicket pendingUpdate = new PendingChunkTicket(simulationDistanceTracker, simulationDistance, chunkPos);
        this.pendingUpdates.add(pendingUpdate);
    }

}
