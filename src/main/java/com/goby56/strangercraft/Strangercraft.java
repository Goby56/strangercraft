package com.goby56.strangercraft;

import com.goby56.strangercraft.block.ModBlocks;
import com.goby56.strangercraft.item.ModItems;
import com.goby56.strangercraft.tag.ModTags;
import com.goby56.strangercraft.world.biome.ModBiomeKeys;
import com.goby56.strangercraft.world.dimension.UpsideDownDimension;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Strangercraft implements ModInitializer {

	public static final String MOD_ID = "strangercraft";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModItems.register();

		ModBlocks.register();

		ModTags.ModBlocks.register();

		UpsideDownDimension.register();

		ModBiomeKeys.register();
	}
}
