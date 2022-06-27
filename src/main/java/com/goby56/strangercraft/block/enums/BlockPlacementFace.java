package com.goby56.strangercraft.block.enums;

import net.minecraft.block.Block;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.shape.VoxelShape;

public enum BlockPlacementFace implements StringIdentifiable {
    FLOOR("floor"),
    CEILING("ceiling"),
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west");

    final String name;

    BlockPlacementFace(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static VoxelShape getShape(String name, int height) {
        switch (BlockPlacementFace.valueOf(name.toUpperCase())) {
            case FLOOR: {
                return Block.createCuboidShape(0,0, 0, 16, height, 16);
            }
            case CEILING: {
                return Block.createCuboidShape(0,16 - height, 0, 16, 16, 16);
            }
            case NORTH: {
                return Block.createCuboidShape(0,0, 16 - height, 16, 16, height);
            }
            case EAST: {
                return Block.createCuboidShape(0,0, 0, height, 16, 16);
            }
            case SOUTH: {
                return Block.createCuboidShape(0,0, 0, 16, 16, height);
            }
            case WEST: {
                return Block.createCuboidShape(16 - height,0, 0, height, 16, 16);
            }
            default: {
                throw new IllegalArgumentException("Argument 0 needs to be of  either floor, ceiling, north, east, south or west not " + name);
            }
        }
    }
}
