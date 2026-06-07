package com.srcfur.puppycraft;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public class PuppyCraftItems {
    //Sap pipeline
    public static DeferredItem<Item> WOOD_PULP;
    public static DeferredItem<Item> CHEAP_ABSORBENT_POLYMER;
    public static DeferredItem<Item> SUPER_ABSORBENT_POLYMER;

    //Diaper core pipeline
    public static DeferredItem<Item> CHEAP_DIAPER_CORE;
    public static DeferredItem<Item> NORMAL_DIAPER_CORE;
    public static DeferredItem<Item> PREMIUM_DIAPER_CORE;

    public static DeferredItem<Item> DIAPER_BACK_SHEET;

    public static void Initialize(){
        WOOD_PULP = PuppyCraft.ITEMS.register("woodpulp", ()->new Item(new Item.Properties()));
        CHEAP_ABSORBENT_POLYMER = PuppyCraft.ITEMS.register("cheapdiapersap", ()->new Item(new Item.Properties()));
        SUPER_ABSORBENT_POLYMER = PuppyCraft.ITEMS.register("diapersap", ()->new Item(new Item.Properties()));
        CHEAP_DIAPER_CORE = PuppyCraft.ITEMS.register("cheapdiapercore", ()->new Item(new Item.Properties()));
        NORMAL_DIAPER_CORE = PuppyCraft.ITEMS.register("diapercore", ()->new Item(new Item.Properties()));
        PREMIUM_DIAPER_CORE = PuppyCraft.ITEMS.register("threediapercore", ()->new Item(new Item.Properties()));
        DIAPER_BACK_SHEET = PuppyCraft.ITEMS.register("clothbacksheet", ()-> new Item(new Item.Properties()));
    }
}
