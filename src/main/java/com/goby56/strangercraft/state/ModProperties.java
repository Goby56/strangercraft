package com.goby56.strangercraft.state;

import com.goby56.strangercraft.block.enums.BlockPlacementFaces;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.Direction;

public class ModProperties {
    public static final EnumProperty<BlockPlacementFaces> VINELIKE_CONNECTION_FACES = EnumProperty.of("connection", BlockPlacementFaces.class);
    public static final EnumProperty<BlockPlacementFaces> VINELIKE_PLACEMENT_FACES = EnumProperty.of("placement", BlockPlacementFaces.class);

    /**
     * A property that specifies if this block has a face pointing up.
     */
    public static final BooleanProperty UP_FACE = BooleanProperty.of("up_face");
    /**
     * A property that specifies if this block has a face pointing down.
     */
    public static final BooleanProperty DOWN_FACE = BooleanProperty.of("down_face");
    /**
     * A property that specifies if this block has a face pointing north.
     */
    public static final BooleanProperty NORTH_FACE = BooleanProperty.of("north_face");
    /**
     * A property that specifies if this block has a face pointing east.
     */
    public static final BooleanProperty EAST_FACE = BooleanProperty.of("east_face");
    /**
     * A property that specifies if this block has a face pointing south.
     */
    public static final BooleanProperty SOUTH_FACE = BooleanProperty.of("south_face");
    /**
     * A property that specifies if this block has a face pointing west.
     */
    public static final BooleanProperty WEST_FACE = BooleanProperty.of("west_face");

    /**
     * A property that specifies if this block has a connection to the top.
     */
    public static final BooleanProperty UP_CONNECTION = BooleanProperty.of("up_connection");
    /**
     * A property that specifies if this block has a connection downwards.
     */
    public static final BooleanProperty DOWN_CONNECTION = BooleanProperty.of("down_connection");
    /**
     * A property that specifies if this block has a connection to the north.
     */
    public static final BooleanProperty NORTH_CONNECTION = BooleanProperty.of("north_connection");
    /**
     * A property that specifies if this block has a connection to the east.
     */
    public static final BooleanProperty EAST_CONNECTION = BooleanProperty.of("east_connection");
    /**
     * A property that specifies if this block has a connection to the south.
     */
    public static final BooleanProperty SOUTH_CONNECTION = BooleanProperty.of("south_connection");
    /**
     * A property that specifies if this block has a connection to the west.
     */
    public static final BooleanProperty WEST_CONNECTION = BooleanProperty.of("west_connection");


}
