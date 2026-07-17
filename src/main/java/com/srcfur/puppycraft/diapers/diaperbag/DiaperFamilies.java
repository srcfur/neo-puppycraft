package com.srcfur.puppycraft.diapers.diaperbag;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum DiaperFamilies implements StringRepresentable {
    Generic("generic"),
    PHIGHTING("phighting"),
    BUNNYHOPPS("bunnyhopps"),
    MEDICAL("medical"),
    MEGAMAX("mega"),
    PULLUPS("pullup", true),
    CHEAP("cheap");

    DiaperFamilies(String s){
        name = s; max_per_bag = 10; pull_on = false;
    }
    DiaperFamilies(String s, Boolean pullup){
        this.name = s;
        max_per_bag = 10;
        pull_on = pullup;
    }
    DiaperFamilies(String name, int max){
        this.name = name; max_per_bag = max; pull_on = false;
    }

    final String name;
    final int max_per_bag;
    final boolean pull_on;

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
    public int GetMaxCount() { return max_per_bag; }
    public boolean IsPullup() { return pull_on; }
}
