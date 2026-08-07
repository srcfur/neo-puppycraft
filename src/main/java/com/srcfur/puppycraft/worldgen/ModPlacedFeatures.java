package com.srcfur.puppycraft.worldgen;

import com.srcfur.puppycraft.PuppyCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> SEA_SALT_PLACED = registerKey("seasalt_placed");
    public static void bootstrap(BootstrapContext<PlacedFeature> context){
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, SEA_SALT_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.PUPPYCRAFT_OVERWORLD_SALT_ORE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(15), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG), BlockPredicateFilter.forPredicate(
                        BlockPredicate.matchesFluids(Fluids.WATER)
                ), BiomeFilter.biome()));
    }
    public static ResourceKey<PlacedFeature> registerKey(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PuppyCraft.MODID, name));
    }
    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers){
        context.register(key, new PlacedFeature(configuration, modifiers));
    }
}
