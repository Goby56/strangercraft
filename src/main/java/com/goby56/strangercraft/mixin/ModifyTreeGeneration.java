package com.goby56.strangercraft.mixin;


import com.goby56.strangercraft.Strangercraft;
import com.goby56.strangercraft.block.ModBlocks;
import com.goby56.strangercraft.world.dimension.UpsideDownDimension;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;

import static com.goby56.strangercraft.world.dimension.UpsideDownDimension.UPSIDE_DOWN_DIMENSION_KEY;

@Mixin(TreeFeature.class)
public class ModifyTreeGeneration {

    @ModifyVariable(at = @At("LOAD"), method = "generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Ljava/util/function/BiConsumer;Ljava/util/function/BiConsumer;Ljava/util/function/BiConsumer;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Z")
    private List<FoliagePlacer.TreeNode> preventLeafGeneration(List<FoliagePlacer.TreeNode> list, StructureWorldAccess world) {
        if (world.toServerWorld().getRegistryKey() == UPSIDE_DOWN_DIMENSION_KEY) {
            return Lists.newArrayList();
        }
        return list;
    }

//    @ModifyArg(method = "generate", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/trunk/TrunkPlacer;generate(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;Lnet/minecraft/util/math/random/Random;ILnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Ljava/util/List;"))
//    private BlockState changeWoodType(BlockState trunkPlacerReplacer) {
//        return ModBlocks.CONTAMINATED_LOG.getDefaultState();
//    }*
}
