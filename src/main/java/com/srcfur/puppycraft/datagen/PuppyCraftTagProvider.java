package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.blocks.PuppyCraftBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class PuppyCraftTagProvider extends BlockTagsProvider {
    public PuppyCraftTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PuppyCraft.MODID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(PuppyCraftBlocks.RAW_SALT_BLOCK.get());
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(PuppyCraftBlocks.RAW_SALT_BLOCK.get());
    }
}
