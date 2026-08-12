package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.blocks.PuppyCraftBlocks;
import com.srcfur.puppycraft.items.PuppyCraftItems;
import com.srcfur.puppycraft.puppyblocks.PuppyPadBlock;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class PuppyCraftBlockLootTableProvider extends BlockLootSubProvider {
    protected PuppyCraftBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }
    @Override
    protected void generate() {
        add(PuppyCraftBlocks.RAW_SALT_BLOCK.get(),
                block -> createMultipleOreDrops(PuppyCraftBlocks.RAW_SALT_BLOCK.get(), PuppyCraftItems.RAW_SALT.get(), 2, 5));
    }
    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return PuppyCraftBlocks.BLOCKS.getEntries().stream().map(Holder::value).filter(block ->
                (block != PuppyCraftBlocks.DIAPER_BAG_BLOCK.get()) &&
                        (block != PuppyCraftBlocks.PUPPY_PAD_BLOCK.get()) &&
                        (block != PuppyCraftBlocks.DIAPER_ROLLER_BLOCK.get())
        )::iterator;
    }
}
