package com.goby56.strangercraft.block;

import com.goby56.strangercraft.Strangercraft;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block CONTAMINATED_STONE = registerBlock("contaminated_stone", new ShearableVineBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().sounds(BlockSoundGroup.TUFF).strength(1.2f, 7f)), ItemGroup.BUILDING_BLOCKS, false);
    public static final Block CONTAMINATED_DIRT = registerBlock("contaminated_dirt", new ShearableVineBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).requiresTool().sounds(BlockSoundGroup.MUDDY_MANGROVE_ROOTS).strength(0.5f)), ItemGroup.BUILDING_BLOCKS, false);
    public static final Block CONTAMINATED_GRASS_BLOCK = registerBlock("contaminated_grass_block", new ShearableVineBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LICHEN_GREEN).requiresTool().sounds(BlockSoundGroup.MUD).strength(0.6f)), ItemGroup.BUILDING_BLOCKS, false);
    public static final Block CONTAMINATED_LOG = registerBlock("contaminated_log", new ShearableVineBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.PALE_GREEN).requiresTool().sounds(BlockSoundGroup.NETHER_STEM).strength(2f, 3f)), ItemGroup.BUILDING_BLOCKS, false);
    public static final Block DEAD_KELP = registerBlock("dead_kelp", new DeadKelpBlock(FabricBlockSettings.of(Material.UNDERWATER_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.WET_GRASS)), ItemGroup.DECORATIONS, true);
    public static final Block TESTABLE_VINELIKE = registerBlock("testable_vinelike", new VinelikeTestBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).noCollision().requiresTool().sounds(BlockSoundGroup.SCULK_VEIN).strength(0.2f), false, 4), ItemGroup.DECORATIONS, true);

    private static Block registerBlock(String name, Block block, ItemGroup group, Boolean cutout) {
        registerBlockItem(name, block, group);
        Block BLOCK = Registry.register(Registry.BLOCK, new Identifier(Strangercraft.MOD_ID, name), block);
        if (cutout) {
            BlockRenderLayerMap.INSTANCE.putBlock(BLOCK, RenderLayer.getCutout());
        }
        return BLOCK;
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(Strangercraft.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void register() {
        Strangercraft.LOGGER.info("Registering Mod Blocks for " + Strangercraft.MOD_ID);
    }
}
