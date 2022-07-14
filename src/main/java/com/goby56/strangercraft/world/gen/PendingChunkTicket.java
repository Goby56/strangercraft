package com.goby56.strangercraft.world.gen;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.SimulationDistanceLevelPropagator;

public class PendingChunkTicket {
    public int tickDelay;
    private final SimulationDistanceLevelPropagator simulationDistanceTracker;
    private final int simulationDistance;
    private final ChunkPos chunkPos;

    public PendingChunkTicket(SimulationDistanceLevelPropagator simulationDistanceTracker, int simulationDistance, ChunkPos chunkPos) {
        this.tickDelay = 10;
        this.simulationDistanceTracker = simulationDistanceTracker;
        this.simulationDistance = simulationDistance;
        this.chunkPos = chunkPos;
    }

    public void tick() {
        if (--this.tickDelay <= 0) {
            this.simulationDistanceTracker.add(ChunkTicketType.PLAYER, this.chunkPos, this.simulationDistance, this.chunkPos);
        }
    }
}
