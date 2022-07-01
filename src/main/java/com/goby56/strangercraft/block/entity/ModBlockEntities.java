package com.goby56.strangercraft.block.entity;

import com.goby56.strangercraft.Strangercraft;
import com.goby56.strangercraft.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<AbstractVinelikeBlockEntity> VINELIKE_BLOCK_ENTITY;

    public static void register() {
        VINELIKE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Strangercraft.MOD_ID, "abstract_vinelike_block_entity"),
                FabricBlockEntityTypeBuilder.create(AbstractVinelikeBlockEntity::new,
                        ModBlocks.VINELIKE_TEST_BLOCK).build(null));
    }
}
