package com.goby56.strangercraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShearableVineBlock extends Block {
    public ShearableVineBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            if (!world.isClient) {
                itemStack.damage(1, player, livingEntity -> livingEntity.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }
        return super.onUse(state, world, pos, player,hand, hit);
    }
}
