package com.goby56.strangercraft.tag;

import com.goby56.strangercraft.Strangercraft;
import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NATURALLY_WATERLOGGED_BLOCKS = registerTag("naturally_waterlogged_blocks");
        public static final TagKey<Block> CORAL = registerTag("coral");

        private static TagKey<Block> registerTag(String name) {
            return TagRegistration.BLOCK_TAG_REGISTRATION.registerFabric(name);
        }

        public static void register() {
            Strangercraft.LOGGER.info("Registering Mod Block Tags for " + Strangercraft.MOD_ID);
        }
    }
}
