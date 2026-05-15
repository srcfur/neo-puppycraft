package com.srcfur.puppycraft.diapers.diaperbag;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum DiaperFamilies implements StringRepresentable {
    Generic("generic"),
    PHIGHTING("phighting"),
    BUNNYHOPPS("bunnyhopps"),
    MEDICAL("medical"),
    MEGAMAX("mega"),
    PULLUPS("pullup"),
    CHEAP("cheap");

    DiaperFamilies(String s){
        name = s;
    }

    final String name;

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
