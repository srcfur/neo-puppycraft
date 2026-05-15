package com.srcfur.puppycraft.diapers;


import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Optional;

public class DiaperData {
    public DiaperData(Identifier name){
        this.name = name;
    }
    public DiaperData(Identifier name, DeferredItem<DiaperItem> item) { this(name); }
    public Identifier name;
    public Optional<DiaperItem> GetItem(){
        Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(name);
        if(item.isPresent()){
            return Optional.of((DiaperItem) item.get().value());
        }
        return Optional.empty();
    }
}