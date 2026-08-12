package com.srcfur.puppycraft.blocks.entities;

import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.blocks.PuppyCraftBlocks;
import com.srcfur.puppycraft.diapers.machinery.roller.DiaperRollerRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
@EventBusSubscriber(modid = PuppyCraft.MODID, value = Dist.CLIENT)
public class PuppyCraftBlockRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(PuppyCraftBlockEntities.DIAPER_ROLLER_ENTITY.get(), DiaperRollerRenderer::new);
    }
}
