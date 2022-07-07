package com.goby56.strangercraft;

import com.goby56.strangercraft.block.ModBlocks;
import com.goby56.strangercraft.entity.ModEntities;
import com.goby56.strangercraft.item.ModItems;
import com.goby56.strangercraft.tag.ModTags;
import com.goby56.strangercraft.utils.InterdimPresenceForceLoader;
import com.goby56.strangercraft.world.biome.ModBiomeKeys;
import com.goby56.strangercraft.world.dimension.InterdimPresenceEventHandler;
import com.goby56.strangercraft.world.dimension.UpsideDownDimension;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Strangercraft implements ModInitializer {

	public static final String MOD_ID = "strangercraft";

	public static final InterdimPresenceForceLoader forceLoadedChunkHandler = new InterdimPresenceForceLoader();

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

		//ModBlockEntities.register();

		ModTags.Blocks.register();

		UpsideDownDimension.register();

		ModBiomeKeys.register();

		ModEntities.register();

		ServerTickEvents.END_SERVER_TICK.register(forceLoadedChunkHandler::tick);

		ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(InterdimPresenceEventHandler::onPlayerChangeDimension);
	}
}
