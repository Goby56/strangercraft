package com.goby56.strangercraft.item;

import com.goby56.strangercraft.Strangercraft;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModItems {

//    public static final Item CODE_NAME = registerItem("id_name",
//            new Item(new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Strangercraft.MOD_ID, name), item);
    }
    public static void register() {
        Strangercraft.LOGGER.info("regestering Mod Items for " + Strangercraft.MOD_ID);
    }
}
