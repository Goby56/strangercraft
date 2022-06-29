package com.goby56.strangercraft.utils;

import com.goby56.strangercraft.block.enums.BlockPlacementFaces;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;


public class RaycastUtil {

    private static final EnumSet<Direction.Axis> XYZ = EnumSet.of(Direction.Axis.X, Direction.Axis.Y, Direction.Axis.Z);

    public static Direction getBlockFace(HitResult result, Vec3d origin) {
        Vec3d diff = result.getPos().floorAlongAxes(XYZ).subtract(result.getPos());
        if (diff.x == 0) {
            return origin.getX() > result.getPos().x ? Direction.EAST : Direction.WEST;
        } else if (diff.y == 0) {
            return origin.getY() > result.getPos().y ? Direction.UP : Direction.DOWN;
        } else if (diff.z == 0) {
            return origin.getZ() > result.getPos().z ? Direction.SOUTH : Direction.NORTH;
        }
        return Direction.UP;
    }


}
