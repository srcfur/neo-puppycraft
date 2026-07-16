package com.srcfur.puppycraft.client;

import com.srcfur.badhygiene.api.HygieneAPI;
import com.srcfur.puppycraft.Config;
import com.srcfur.puppycraft.PuppyCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class LegacyUrineMeter {
    public static ResourceLocation EmptyUrine = ResourceLocation.fromNamespaceAndPath(PuppyCraft.MODID, "empty_bladder");
    public static ResourceLocation FullUrine = ResourceLocation.fromNamespaceAndPath(PuppyCraft.MODID, "filled_bladder");
    public static void render(GuiGraphics graphics){

        int xoffset = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
        int yoffset = Minecraft.getInstance().getWindow().getGuiScaledHeight() -52;
        int spacing = 9;

        Player plr = Minecraft.getInstance().player;

        if( plr != null && Config.LEGACY_URINE_METER.getAsBoolean()){
            float fullness = HygieneAPI.getBladderFullness(plr);
            for (int i = 0; i < 10; i++){
                graphics.blitSprite(i * 10f < fullness ? FullUrine : EmptyUrine, xoffset + spacing * i, yoffset, 12, 12);
            }
        }
    }
}
