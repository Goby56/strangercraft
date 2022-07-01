package com.goby56.strangercraft.block.enums;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

public enum ConnectionType implements StringIdentifiable {

    NONE("none"),
    STUB("stub"),
    CROSSING("crossing"),
    STRAIGHT_NORTH_SOUTH("north_south"),
    STRAIGHT_EAST_WEST("east_west"),
    STRAIGHT_UP_DOWN("up_down"),
    T_JUNCTION_1("t1"),
    T_JUNCTION_2("t2"),
    T_JUNCTION_3("t3"),
    T_JUNCTION_4("t4");


    final String name;

    ConnectionType(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static ConnectionType getStraight(Direction direction) {
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            return STRAIGHT_NORTH_SOUTH;
        } else if (direction == Direction.EAST || direction == Direction.WEST) {
            return STRAIGHT_EAST_WEST;
        } else {
            return STRAIGHT_UP_DOWN;
        }
    }
}
