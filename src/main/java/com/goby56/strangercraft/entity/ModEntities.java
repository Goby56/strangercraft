package com.goby56.strangercraft.entity;

import com.goby56.strangercraft.Strangercraft;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {
    public static final EntityType<InterdimensionalPresenceEntity> UPSIDE_DOWN_PRESENCE = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(Strangercraft.MOD_ID, "upsidedown_presence"),
            FabricEntityTypeBuilder.<InterdimensionalPresenceEntity>create(SpawnGroup.AMBIENT, InterdimensionalPresenceEntity::new)
                    .spawnableFarFromPlayer().build());







    public static void register() {
        Strangercraft.LOGGER.info("Registering Mod Entities for " + Strangercraft.MOD_ID);
    }
}
