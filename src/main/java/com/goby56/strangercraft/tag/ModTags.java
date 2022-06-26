package com.goby56.strangercraft.tag;

import com.goby56.strangercraft.Strangercraft;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NATURALLY_WATERLOGGED_BLOCKS = registerTag("naturally_waterlogged_blocks", false);
        public static final TagKey<Block> CORAL = registerTag("coral", true);
        public static final TagKey<Block> OCEAN_FLOOR = registerTag("ocean_floor", true);
        public static final TagKey<Block> OCEAN_PLANTS = registerTag("ocean_plants", true);
        public static final TagKey<Block> KELP = registerTag("kelp", true);

        private static TagKey<Block> registerTag(String name, Boolean common) {
            if (common) {
                return TagKey.of(Registry.BLOCK_KEY, new Identifier("c", name));
            }
            return TagKey.of(Registry.BLOCK_KEY, new Identifier(Strangercraft.MOD_ID, name));
        }

        public static void register() {
            Strangercraft.LOGGER.info("Registering Mod Block Tags for " + Strangercraft.MOD_ID);
        }
    }
}
