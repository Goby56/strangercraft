package com.goby56.strangercraft.block;

import com.goby56.strangercraft.block.enums.BlockPlacementFace;
import com.goby56.strangercraft.state.ModProperties;
import com.goby56.strangercraft.utils.RaycastUtil;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.HashSet;
import java.util.Set;

public class AbstractVinelikeBlock
        extends Block
        implements Waterloggable {

    public static final EnumProperty<BlockPlacementFace> PLACEMENT_FACES = ModProperties.VINELIKE;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public Set<BlockPlacementFace> occupiedFaces;
    public Random clusterID;
    private final boolean forbidsMultiConnections;
    private final int height;

    public AbstractVinelikeBlock(Settings settings, boolean forbidsMultiConnections, int height) {
        super(settings);
        this.height = height;
        this.occupiedFaces = new HashSet<>();
        this.forbidsMultiConnections = forbidsMultiConnections; // For dead kelp, because it should not form any connections, it should be continuous
        this.clusterID = Random.create(); // Intended to use for dead kelp with roots being on one side of the fallen kelp
    }

    public boolean isContinuous() {
        return this.forbidsMultiConnections;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        for (BlockPlacementFace face : this.occupiedFaces) {
            shape = VoxelShapes.union(shape, BlockPlacementFace.getShape(face.asString(), this.height));
        }
        if (shape.isEmpty()) {
            return BlockPlacementFace.getShape("floor", this.height);
        }
        return shape;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos lookingAtBlock = ( (BlockHitResult) MinecraftClient.getInstance().crosshairTarget ).getBlockPos();

        if (world.getBlockState(lookingAtBlock).isFullCube(world, lookingAtBlock) || state.isOf(Blocks.GRASS) || state.isOf(this)) {
            return true;
        }
        return false;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient) {
            if (this.occupiedFaces.isEmpty()) this.occupiedFaces.add(BlockPlacementFace.FLOOR);
            return;
        }
        MinecraftClient instance = MinecraftClient.getInstance();
        BlockPlacementFace face = RaycastUtil.getBlockFace(instance.crosshairTarget, instance.player.getPos());

        this.occupiedFaces.add(face);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (MinecraftClient.getInstance() != null && !MinecraftClient.getInstance().player.isCreative()) {
            dropStack((World)world, pos, new ItemStack(Items.DIAMOND, this.occupiedFaces.size()));
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {

    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.PUSH_ONLY;
    }
}
