package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.items.PuppyCraftItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class PuppyCraftLootTableProvider implements LootTableSubProvider {
    public PuppyCraftLootTableProvider(HolderLookup.Provider lookupProvider) {

    }
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        biConsumer.accept(
                ResourceKey.create(
                        Registries.LOOT_TABLE,
                        ResourceLocation.fromNamespaceAndPath(PuppyCraft.MODID, "barrels/nursery")
                ),
                LootTable.lootTable()
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(3,9))
                                        .add(LootItem.lootTableItem(PuppyCraftItems.MILK_BABY_BOTTLE).setWeight(3))
                                        .add(LootItem.lootTableItem(PuppyCraftItems.FOY_BABY_BOTTLE).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.COOKIE).setWeight(5))
                        ));
    }
}
