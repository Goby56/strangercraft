package com.goby56.strangercraft.world.gen;


import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.math.ChunkPos;


import java.util.ArrayList;

public class UpsideDownChunkGenerator {

    private final ArrayList<PendingTicket> pendingTickets;

    public UpsideDownChunkGenerator() {
        this.pendingTickets = new ArrayList<>();
    }

    public void tick(MinecraftServer minecraftServer) {
        ArrayList<PendingTicket> toRemove = new ArrayList<>();
        for (PendingTicket pendingTicket : pendingTickets) {
            pendingTicket.tick();
            if (pendingTicket.tickDelay < 0) {
                toRemove.add(pendingTicket);
            }
        }
        for (PendingTicket expiredTicket : toRemove) {
            pendingTickets.remove(expiredTicket);
        }
    }

    public <T> void delayTicket(int tickDelay, ChunkTicketManager ticketManager, ChunkTicketType<T> ticketType, ChunkPos pos, int radius, T argument) {
        pendingTickets.add(new PendingTicket<>(tickDelay, ticketManager, ticketType, pos, radius, argument));
    }

    private class PendingTicket<T> {
        protected int tickDelay;
        private final ChunkTicketManager ticketManager;
        private final ChunkTicketType<T> ticketType;
        private final ChunkPos pos;
        private final int radius;
        private final T argument;

        public PendingTicket(int tickDelay, ChunkTicketManager ticketManager, ChunkTicketType<T> ticketType, ChunkPos pos, int radius, T argument) {
            this.tickDelay = tickDelay;
            this.ticketManager = ticketManager;
            this.ticketType = ticketType;
            this.pos = pos;
            this.radius = radius;
            this.argument = argument;
        }

        public void tick() {
            if (--this.tickDelay <= 0) {
                this.ticketManager.addTicket(this.ticketType, this.pos, this.radius, this.argument);
            }
        }
    }

}
