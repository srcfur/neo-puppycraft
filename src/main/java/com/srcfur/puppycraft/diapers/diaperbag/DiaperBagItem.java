package com.srcfur.puppycraft.diapers.diaperbag;

import com.srcfur.puppycraft.diapers.DiaperBagData;
import com.srcfur.puppycraft.diapers.DiaperCodecs;
import com.srcfur.puppycraft.diapers.DiaperItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DiaperBagItem extends BlockItem {
    public DiaperBagItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        DiaperBagData data = stack.getOrDefault(DiaperCodecs.DIAPER_BAG_COMPONENT, new DiaperBagData(0, "puppycraft:medicaldiaper"));
        if(data.diapers() > 0){
            DiaperItem item = (DiaperItem) BuiltInRegistries.ITEM.get(ResourceLocation.parse(data.diaperaddress())).asItem();
            return Component.translatable("item.puppycraft.diaperbag." + item.family.getSerializedName());
        }else{
            return Component.translatable("item.puppycraft.diaperbag.empty");
        }
    }
    
}
