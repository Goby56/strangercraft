package com.goby56.strangercraft.entity;

import com.goby56.strangercraft.Strangercraft;
import com.goby56.strangercraft.world.gen.ForceLoadedChunkManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static com.goby56.strangercraft.world.dimension.UpsideDownDimension.UPSIDE_DOWN_DIMENSION_KEY;

public class InterdimensionalPresenceEntity extends Entity {

    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUuid;
    private int viewDistance;
    private final ForceLoadedChunkManager forceLoadedChunkManager;
    private long currentChunk;

    public InterdimensionalPresenceEntity(EntityType<InterdimensionalPresenceEntity> type, World world) {
        super(type, world);
        this.viewDistance = getServer().getPlayerManager().getViewDistance();
        this.forceLoadedChunkManager = Strangercraft.FORCE_LOADED_CHUNK_MANAGER;
        System.out.println(world.getRegistryKey().toString());
    }

    public InterdimensionalPresenceEntity(World world, double x, double y, double z, LivingEntity owner) {
        this(ModEntities.UPSIDE_DOWN_PRESENCE, world);
        this.setPosition(x, y, z);
        this.setOwner(owner);
        this.currentChunk = ChunkPos.toLong(new BlockPos(x, y, z));
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void tick() {
        if (this.owner == null || this.owner.getWorld().getRegistryKey() != UPSIDE_DOWN_DIMENSION_KEY
                || this.owner.isDead() || getServer() == null) {
            forceLoadedChunkManager.removeDependency(ChunkPos.toLong(getBlockPos()), ownerUuid);
            this.discard();
            return;
        }

        this.setPosition(this.owner.getPos());
        this.viewDistance = getServer().getPlayerManager().getViewDistance();

        boolean newChunk = currentChunk != ChunkPos.toLong(getBlockPos());
        if (newChunk) {
            forceLoadedChunkManager.removeDependency(currentChunk, ownerUuid);
            currentChunk = ChunkPos.toLong(getBlockPos());
            forceLoadedChunkManager.appendChunk(currentChunk, ownerUuid);
        }

//        getServer().getOverworld().getChunkManager()
//                .addTicket(ChunkTicketType.PLAYER, new ChunkPos(getBlockPos()), viewDistance+1, new ChunkPos(getBlockPos()));

    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUuid = owner == null ? null : owner.getUuid();
    }

    @Nullable
    public LivingEntity getOwner() {
        Entity entity;
        if (this.owner == null && this.ownerUuid != null && this.world instanceof ServerWorld && (entity = ((ServerWorld)this.world).getEntity(this.ownerUuid)) instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
        }
        return this.owner;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
