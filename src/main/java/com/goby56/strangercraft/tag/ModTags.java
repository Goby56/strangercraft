package com.goby56.strangercraft.tag;

import com.goby56.strangercraft.Strangercraft;
import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModTags {
    public static class ModBlocks {
        public static final TagKey<Block> NATURALLY_WATERLOGGED_BLOCKS = registerTag("naturally_waterlogged_blocks");
        public static final TagKey<Block> CORAL = registerTag("coral");
        public static final TagKey<Block> OCEAN_FLOOR = registerTag("ocean_floor");
        public static final TagKey<Block> OCEAN_PLANTS = registerTag("ocean_plants");

        private static TagKey<Block> registerTag(String name) {
            return TagKey.of(Registry.BLOCK_KEY, new Identifier(Strangercraft.MOD_ID, name));
        }

        public static void register() {
            Strangercraft.LOGGER.info("Registering Mod Block Tags for " + Strangercraft.MOD_ID);
        }
    }
}
