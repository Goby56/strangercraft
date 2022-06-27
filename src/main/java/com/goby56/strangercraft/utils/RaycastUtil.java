package com.goby56.strangercraft.utils;

import com.goby56.strangercraft.block.enums.BlockPlacementFace;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;


public class RaycastUtil {

    private static final EnumSet<Direction.Axis> XYZ = EnumSet.of(Direction.Axis.X, Direction.Axis.Y, Direction.Axis.Z);

    public static BlockPlacementFace getBlockFace(HitResult result, Vec3d origin) {
        Vec3d diff = result.getPos().floorAlongAxes(XYZ).subtract(result.getPos());
        if (diff.x == 0) {
            return origin.getX() > result.getPos().x ? BlockPlacementFace.EAST : BlockPlacementFace.WEST;
        } else if (diff.y == 0) {
            return origin.getY() > result.getPos().y ? BlockPlacementFace.FLOOR : BlockPlacementFace.CEILING;
        } else if (diff.z == 0) {
            return origin.getZ() > result.getPos().z ? BlockPlacementFace.SOUTH : BlockPlacementFace.NORTH;
        }
        return BlockPlacementFace.FLOOR;
    }
}
