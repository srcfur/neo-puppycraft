package com.srcfur.puppycraft.diapers;

import com.srcfur.puppycraft.diapers.diaperbag.DiaperFamilies;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class DiaperItem extends Item implements ICurioItem {
    public Identifier DIAPER_TEXTURE;
    public static final String USAGE_TAG = "urine";
    public DiaperFamilies family;
    public DiaperItem(Properties properties, Identifier texture, DiaperFamilies family){
        super(properties);
        DIAPER_TEXTURE = texture;
        this.family = family;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 100;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }
}
