package com.goby56.strangercraft.world.dimension;

import com.goby56.strangercraft.Strangercraft;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class UpsideDownDimension {

    public static final RegistryKey<World> UPSIDE_DOWN_DIMENSION_KEY = RegistryKey.of(Registry.WORLD_KEY,
            new Identifier(Strangercraft.MOD_ID, "upsidedown"));
    public static final RegistryKey<DimensionType> UPSIDE_DOWN_TYPE_KEY = RegistryKey.of(Registry.DIMENSION_TYPE_KEY,
            UPSIDE_DOWN_DIMENSION_KEY.getValue());

    public static void register() {
        Strangercraft.LOGGER.debug("Registering dimensions for " + Strangercraft.MOD_ID);
        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.MANGROVE_ROOTS)
                .destDimID(UPSIDE_DOWN_DIMENSION_KEY.getValue())
                .tintColor(255, 34, 0)
                .lightWithItem(Items.PHANTOM_MEMBRANE)
                .registerPortal();
    }
}
