package com.goby56.strangercraft.mixin;

import com.goby56.strangercraft.world.biome.ModBiomeKeys;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.OverworldBiomeCreator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BuiltinBiomes.class)
public class ExtendBuiltinBiomes {
    @Inject(method = "getDefaultBiome", at = @At("RETURN"))
    private static void addDefaultBiomes(Registry<Biome> registry, CallbackInfoReturnable<RegistryEntry<Biome>> cir) {
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_VOID, OverworldBiomeCreator.createTheVoid());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_PLAINS, OverworldBiomeCreator.createPlains(false, false, false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_SUNFLOWER_PLAINS, OverworldBiomeCreator.createPlains(true, false, false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_SNOWY_PLAINS, OverworldBiomeCreator.createPlains(false, true, false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_ICE_SPIKES, OverworldBiomeCreator.createPlains(false, true, true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_DESERT, OverworldBiomeCreator.createDesert());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_SWAMP, OverworldBiomeCreator.createSwamp());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_MANGROVE_SWAMP, OverworldBiomeCreator.createMangroveSwamp());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_FOREST, OverworldBiomeCreator.createNormalForest(false, false, false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_FLOWER_FOREST, OverworldBiomeCreator.createNormalForest(false, false, true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_BIRCH_FOREST, OverworldBiomeCreator.createNormalForest(true, false, false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_DARK_FOREST, OverworldBiomeCreator.createDarkForest());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_OLD_GROWTH_BIRCH_FOREST, OverworldBiomeCreator.createNormalForest(true, true, false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_OLD_GROWTH_PINE_TAIGA, OverworldBiomeCreator.createOldGrowthTaiga(false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_OLD_GROWTH_SPRUCE_TAIGA, OverworldBiomeCreator.createOldGrowthTaiga(true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_TAIGA, OverworldBiomeCreator.createTaiga(false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_SNOWY_TAIGA, OverworldBiomeCreator.createTaiga(true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_SAVANNA, OverworldBiomeCreator.createSavanna(false, false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_SAVANNA_PLATEAU, OverworldBiomeCreator.createSavanna(false, true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_WINDSWEPT_HILLS, OverworldBiomeCreator.createWindsweptHills(false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_WINDSWEPT_GRAVELLY_HILLS, OverworldBiomeCreator.createWindsweptHills(false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_WINDSWEPT_FOREST, OverworldBiomeCreator.createWindsweptHills(true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_WINDSWEPT_SAVANNA, OverworldBiomeCreator.createSavanna(true, false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_JUNGLE, OverworldBiomeCreator.createJungle());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_SPARSE_JUNGLE, OverworldBiomeCreator.createSparseJungle());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_BAMBOO_JUNGLE, OverworldBiomeCreator.createNormalBambooJungle());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_BADLANDS, OverworldBiomeCreator.createBadlands(false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_ERODED_BADLANDS, OverworldBiomeCreator.createBadlands(false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_WOODED_BADLANDS, OverworldBiomeCreator.createBadlands(true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_MEADOW, OverworldBiomeCreator.createMeadow());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_GROVE, OverworldBiomeCreator.createGrove());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_SNOWY_SLOPES, OverworldBiomeCreator.createSnowySlopes());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_FROZEN_PEAKS, OverworldBiomeCreator.createFrozenPeaks());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_JAGGED_PEAKS, OverworldBiomeCreator.createJaggedPeaks());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_STONY_PEAKS, OverworldBiomeCreator.createStonyPeaks());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_RIVER, OverworldBiomeCreator.createRiver(false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_FROZEN_RIVER, OverworldBiomeCreator.createRiver(true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_BEACH, OverworldBiomeCreator.createBeach(false, false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_SNOWY_BEACH, OverworldBiomeCreator.createBeach(true, false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_STONY_SHORE, OverworldBiomeCreator.createBeach(false, true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_WARM_OCEAN, OverworldBiomeCreator.createWarmOcean());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_LUKEWARM_OCEAN, OverworldBiomeCreator.createLukewarmOcean(false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_DEEP_LUKEWARM_OCEAN, OverworldBiomeCreator.createLukewarmOcean(true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_OCEAN, OverworldBiomeCreator.createNormalOcean(false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_DEEP_OCEAN, OverworldBiomeCreator.createNormalOcean(true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_COLD_OCEAN, OverworldBiomeCreator.createColdOcean(false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_DEEP_COLD_OCEAN, OverworldBiomeCreator.createColdOcean(true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_FROZEN_OCEAN, OverworldBiomeCreator.createFrozenOcean(false));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_DEEP_FROZEN_OCEAN, OverworldBiomeCreator.createFrozenOcean(true));
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_MUSHROOM_FIELDS, OverworldBiomeCreator.createMushroomFields());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_DRIPSTONE_CAVES, OverworldBiomeCreator.createDripstoneCaves());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_LUSH_CAVES, OverworldBiomeCreator.createLushCaves());
        BuiltinRegistries.add(registry, ModBiomeKeys.FLIPPED_DEEP_DARK, OverworldBiomeCreator.createDeepDark());
    }
}
