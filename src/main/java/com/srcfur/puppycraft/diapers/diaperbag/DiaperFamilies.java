package com.srcfur.puppycraft.diapers.diaperbag;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum DiaperFamilies implements StringRepresentable {
    Generic("generic"),
    PHIGHTING("phighting"),
    BUNNYHOPPS("bunnyhopps"),
    MEDICAL("medical"),
    MEGAMAX("mega"),
    PULLUPS("pullup", 12),
    CHEAP("cheap", 20);

    DiaperFamilies(String s){
        name = s; max_per_bag = 10;
    }
    DiaperFamilies(String name, int max){
        this.name = name; max_per_bag = max;
    }

    final String name;
    final int max_per_bag;

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
