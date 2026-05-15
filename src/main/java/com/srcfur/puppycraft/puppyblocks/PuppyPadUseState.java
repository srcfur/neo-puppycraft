package com.srcfur.puppycraft.puppyblocks;

import net.minecraft.util.StringRepresentable;

public enum PuppyPadUseState implements StringRepresentable {
    DRY("dry"),
    LIGHT("light"),
    WET("wet"),
    SOAKED("soaked");
    PuppyPadUseState(String s){
        name = s;
    }
    final String name;
    @Override
    public String getSerializedName() {
        return name;
    }
}
