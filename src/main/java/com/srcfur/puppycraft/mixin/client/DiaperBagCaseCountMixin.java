package com.srcfur.puppycraft.mixin.client;

import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.diapers.DiaperCodecs;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(GuiGraphics.class)
public abstract class DiaperBagCaseCountMixin {

    @Inject(at = @At("HEAD"), method = "renderItemDecorations*")
    public void renderDiaperBagCount(Font font, ItemStack stack, int x, int y, @Nullable String text, CallbackInfo ci){
        if(stack.getItem() == PuppyCraft.DIAPER_BAG_ITEM.get() && stack.get(DiaperCodecs.DIAPER_BAG_COMPONENT) != null){
            GuiGraphics gpu = ((GuiGraphics) (Object) this);
            gpu.pose().pushPose();
            String s = text == null ? String.valueOf(stack.get(DiaperCodecs.DIAPER_BAG_COMPONENT).diapers()) : text;
            gpu.pose().translate(0.0F, 0.0F, 200.0F);
            gpu.drawString(font, s, x + 19 - 2 - font.width(s), y + 6 + 3, 16777215, true);
            gpu.pose().popPose();
        }
    }
}