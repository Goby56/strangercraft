package com.goby56.strangercraft.block;

import com.goby56.strangercraft.Strangercraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class ModBlocks {

    public static final Block CONTAMINATED_STONE = registerBlock("contaminated_stone", new ShearableVineBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().sounds(BlockSoundGroup.TUFF).strength(1.2f, 7f)), ItemGroup.BUILDING_BLOCKS);
    public static final Block CONTAMINATED_DIRT = registerBlock("contaminated_dirt", new ShearableVineBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).requiresTool().sounds(BlockSoundGroup.MUDDY_MANGROVE_ROOTS).strength(0.5f)), ItemGroup.BUILDING_BLOCKS);
    public static final Block CONTAMINATED_GRASS_BLOCK = registerBlock("contaminated_grass_block", new ShearableVineBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LICHEN_GREEN).requiresTool().sounds(BlockSoundGroup.MUD).strength(0.6f)), ItemGroup.BUILDING_BLOCKS);
    public static final Block CONTAMINATED_LOG = registerBlock("contaminated_log", new ShearableVineBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.PALE_GREEN).requiresTool().sounds(BlockSoundGroup.NETHER_STEM).strength(2f, 3f)), ItemGroup.BUILDING_BLOCKS);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(Strangercraft.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(Strangercraft.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void register() {
        Strangercraft.LOGGER.info("Registering Mod Blocks for " + Strangercraft.MOD_ID);
    }
}
