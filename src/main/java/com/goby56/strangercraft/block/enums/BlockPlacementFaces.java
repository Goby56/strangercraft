package com.goby56.strangercraft.block.enums;

import net.minecraft.block.Block;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public enum BlockPlacementFaces implements StringIdentifiable {
    FLOOR("floor"),
    CEILING("ceiling"),
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west");

    final String name;

    BlockPlacementFaces(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static BlockPlacementFaces of(Direction direction) {
        switch (direction) {
            case UP: {
                return BlockPlacementFaces.FLOOR;
            }
            case DOWN: {
                return BlockPlacementFaces.CEILING;
            }
            case NORTH: {
                return BlockPlacementFaces.NORTH;
            }
            case SOUTH: {
                return BlockPlacementFaces.SOUTH;
            }
            case EAST: {
                return BlockPlacementFaces.EAST;
            }
            case WEST: {
                return BlockPlacementFaces.WEST;
            }
            default: {
                throw new IllegalArgumentException("Argument 0 needs to be of either UP, DOWN, NORTH, EAST, SOUTH or WEST not " + direction);
            }
        }
    }


    public static VoxelShape getShape(String face, int height) {
        switch (BlockPlacementFaces.valueOf(face)) {
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
                throw new IllegalArgumentException("Argument 0 needs to be of  either FLOOR, CEILING, NORTH, EAST, SOUTH or WEST not " + face);
            }
        }
    }
}
