package com.srcfur.puppycraft.datagen;

import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.items.PuppyCraftItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PuppyCraftRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public PuppyCraftRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }
    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        oreSmelting(recipeOutput, List.of(PuppyCraftItems.RAW_SALT.get()), RecipeCategory.MISC, PuppyCraftItems.SALT.get(), 0.25f, 60, "salt");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,
                PuppyCraftItems.CHEAP_ABSORBENT_POLYMER.get(),
                1).requires(PuppyCraftItems.SALT.get(), 4)
                .unlockedBy(getHasName(PuppyCraftItems.SALT.get()), has(PuppyCraftItems.SALT.get())).save(recipeOutput, PuppyCraft.MODID + ":cheap_sap");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,
                PuppyCraftItems.SUPER_ABSORBENT_POLYMER.get(),
                1).requires(PuppyCraftItems.CHEAP_ABSORBENT_POLYMER.get(), 2).requires(PuppyCraftItems.WOOD_PULP.get(), 2)
                .unlockedBy(getHasName(PuppyCraftItems.CHEAP_ABSORBENT_POLYMER.get()), has(PuppyCraftItems.CHEAP_ABSORBENT_POLYMER.get())).save(recipeOutput, PuppyCraft.MODID + ":sap");

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(Items.SUGAR_CANE),
                        RecipeCategory.MISC, PuppyCraftItems.WOOD_PULP.get(), 0.1f, 20)
                .unlockedBy(getHasName(Items.SUGAR_CANE), has(Items.SUGAR_CANE)).save(recipeOutput, PuppyCraft.MODID + ":pulp_from_sugarcane");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ItemTags.LOGS),
                        RecipeCategory.MISC, new ItemStack(PuppyCraftItems.WOOD_PULP.get(), 2), 0.1f, 30)
                .unlockedBy(getHasName(Items.SUGAR_CANE), has(Items.SUGAR_CANE)).save(recipeOutput, PuppyCraft.MODID + ":pulp_from_wood");

        createDiaperCoreRecipe(recipeOutput, PuppyCraftItems.SALT, PuppyCraftItems.CHEAP_DIAPER_CORE);
        createDiaperCoreRecipe(recipeOutput, PuppyCraftItems.CHEAP_ABSORBENT_POLYMER, PuppyCraftItems.NORMAL_DIAPER_CORE);
        createDiaperCoreRecipe(recipeOutput, PuppyCraftItems.SUPER_ABSORBENT_POLYMER, PuppyCraftItems.PREMIUM_DIAPER_CORE);
    }

    protected static void createDiaperCoreRecipe(RecipeOutput output, ItemLike filling, ItemLike result){
        var recipe = ShapedRecipeBuilder.shaped(
                RecipeCategory.MISC,
                result).define('P', Items.PAPER).define('C', filling);
        for(int i = 0; i < 3; i++){
            recipe.pattern("PCP");
        }
        recipe.unlockedBy(getHasName(filling), has(filling)).save(output, PuppyCraft.MODID + ":crafting_table_core_" + getItemName(result));
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, PuppyCraft.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
