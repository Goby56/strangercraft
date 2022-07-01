package com.goby56.strangercraft.state;

import com.goby56.strangercraft.block.enums.BlockPlacementFaces;
import com.goby56.strangercraft.block.enums.ConnectionType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public class ModProperties {
    public static final EnumProperty<ConnectionType> NORTH_MODEL = EnumProperty.of("north_model", ConnectionType.class);

    public static final EnumProperty<ConnectionType> EAST_MODEL = EnumProperty.of("east_model", ConnectionType.class);

    public static final EnumProperty<ConnectionType> SOUTH_MODEL = EnumProperty.of("south_model", ConnectionType.class);

    public static final EnumProperty<ConnectionType> WEST_MODEL = EnumProperty.of("west_model", ConnectionType.class);

    public static final EnumProperty<ConnectionType> CEILING_MODEL = EnumProperty.of("ceiling_model", ConnectionType.class);

    public static final EnumProperty<ConnectionType> FLOOR_MODEL = EnumProperty.of("floor_model", ConnectionType.class);
}
