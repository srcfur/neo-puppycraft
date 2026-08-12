package com.srcfur.puppycraft.client;

import com.srcfur.badhygiene.api.HygieneAPI;
import com.srcfur.puppycraft.Config;
import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.deminuo.attachments.DeminuoAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class DeminuoGui {

    public static final ResourceLocation BRAIN_GUI = ResourceLocation.fromNamespaceAndPath(PuppyCraft.MODID, "brain");
    public static void render(GuiGraphics graphics){

        int yoffset = Minecraft.getInstance().getWindow().getGuiScaledHeight() - 23;
        int spacing = 9;

        Player plr = Minecraft.getInstance().player;

        if( plr != null ){
            int xoffset = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 12 + 109 * (plr.getMainArm() == HumanoidArm.RIGHT ? 1 : -1);
            graphics.blitSprite(BRAIN_GUI, xoffset, yoffset, 24, 24);
            graphics.drawCenteredString(Minecraft.getInstance().font, plr.getData(DeminuoAttachments.MATURITY).toString(), xoffset + 13, yoffset + 7, Color.WHITE.getRGB());
        }
    }
}