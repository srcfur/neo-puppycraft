package com.srcfur.puppycraft.diapers;


import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Optional;

public class DiaperData {
    public DiaperData(ResourceLocation name){
        this.name = name;
    }
    public DiaperData(ResourceLocation name, DeferredItem<DiaperItem> item) { this(name); }
    public ResourceLocation name;
    public DiaperItem GetItem(){
        Item item = BuiltInRegistries.ITEM.get(name);
        return (DiaperItem) item;
    }
}