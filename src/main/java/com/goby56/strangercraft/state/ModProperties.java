package com.goby56.strangercraft.state;

import com.goby56.strangercraft.block.enums.BlockPlacementFace;
import net.minecraft.state.property.EnumProperty;

public class ModProperties {
    public static final EnumProperty<BlockPlacementFace> VINELIKE = EnumProperty.of("faces", BlockPlacementFace.class);
}
