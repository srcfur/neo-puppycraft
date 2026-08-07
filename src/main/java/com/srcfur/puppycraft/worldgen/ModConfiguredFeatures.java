package com.srcfur.puppycraft.worldgen;

import com.srcfur.badhygiene.blocks.ModBlocks;
import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.blocks.PuppyCraftBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> PUPPYCRAFT_OVERWORLD_SALT_ORE_KEY = registerKey("seasalt");
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context){
        register(context, PUPPYCRAFT_OVERWORLD_SALT_ORE_KEY, Feature.DISK,
                new DiskConfiguration(RuleBasedBlockStateProvider.simple(PuppyCraftBlocks.RAW_SALT_BLOCK.get()),
                        BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.SAND),
                        UniformInt.of(2,3),
                        1));
    }
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PuppyCraft.MODID, name));
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
