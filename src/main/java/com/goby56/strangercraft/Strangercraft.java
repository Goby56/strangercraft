package com.goby56.strangercraft;

import com.goby56.strangercraft.block.ModBlocks;
import com.goby56.strangercraft.entity.ModEntities;
import com.goby56.strangercraft.item.ModItems;
import com.goby56.strangercraft.tag.ModTags;
import com.goby56.strangercraft.utils.WorldSaveUtil;
import com.goby56.strangercraft.world.gen.ForceLoadedChunkManager;
import com.goby56.strangercraft.world.biome.ModBiomeKeys;
import com.goby56.strangercraft.world.dimension.DimensionEventHandler;
import com.goby56.strangercraft.world.dimension.UpsideDownDimension;
import com.goby56.strangercraft.world.gen.ChunkEventHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Strangercraft implements ModInitializer {

	public static final String MOD_ID = "strangercraft";

	public static final ForceLoadedChunkManager FORCE_LOADED_CHUNK_MANAGER = new ForceLoadedChunkManager();
    public static final ChunkEventHandler UPSIDE_DOWN_CHUNK_EVENT_HANDLER = new ChunkEventHandler();
    public static Path WORLD_SAVE_PATH;
//	public static final UpsideDownChunkEventHandler UPSIDE_DOWN_CHUNK_EVENT_HANDLER = new UpsideDownChunkEventHandler();

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

		ServerLifecycleEvents.SERVER_STARTED.register(WorldSaveUtil::setWorldSavePath);

		ServerTickEvents.END_SERVER_TICK.register(UPSIDE_DOWN_CHUNK_EVENT_HANDLER::tick);

		ServerTickEvents.END_SERVER_TICK.register(FORCE_LOADED_CHUNK_MANAGER::tick);

		ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(DimensionEventHandler::onPlayerChangeDimension);

//		ServerChunkEvents.CHUNK_LOAD.register(UpsideDownChunkGenerator::onChunkLoad);

		/**
		 * CUSTOM CHUNK GENERATOR FOR UPSIDE DOWN?
		 * https://www.youtube.com/watch?v=YyVAaJqYAfE
		 * https://gist.github.com/TelepathicGrunt/b768ce904baa4598b21c3ca42f137f23
		 * https://github.com/Exploding-Creeper/Eclipse-Dimension/blob/1.17/src/main/java/com/mystic/eclipse/worldgen/chunkgenerators/SplitChunkGenerator.java
		 */
	}
}
