package com.goby56.strangercraft.utils;

import com.goby56.strangercraft.state.ModProperties;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.Direction;

public class DirectionUtil {
    public static BooleanProperty getBooleanProperty(Direction direction, boolean face) {
        switch (direction) {
            case UP: {
                return face ? ModProperties.UP_FACE : ModProperties.UP_CONNECTION;
            }
            case DOWN: {
                return face ? ModProperties.DOWN_FACE : ModProperties.DOWN_CONNECTION;
            }
            case NORTH: {
                return face ? ModProperties.NORTH_FACE : ModProperties.NORTH_CONNECTION;
            }
            case EAST: {
                return face ? ModProperties.EAST_FACE : ModProperties.EAST_CONNECTION;
            }
            case SOUTH: {
                return face ? ModProperties.SOUTH_FACE : ModProperties.SOUTH_CONNECTION;
            }
            case WEST: {
                return face ? ModProperties.WEST_FACE : ModProperties.WEST_CONNECTION;
            }
            default: {
                throw new IllegalArgumentException("Argument 0 is not a valid Direction");
            }
        }
    }
}
