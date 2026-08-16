package com.srcfur.puppycraft.compat;

import com.srcfur.badhygiene.data.HygieneDataTypes;
import com.srcfur.badhygiene.events.PlayerFullBladderEvent;
import com.srcfur.badhygiene.events.PlayerMessingEvent;
import com.srcfur.puppycraft.Config;
import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.attachments.PuppyCraftAttachments;
import com.srcfur.puppycraft.deminuo.attachments.DeminuoAttachments;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.Optional;

public class BadHygieneIntegration {
    @SubscribeEvent
    public void OnPlayerMess(PlayerMessingEvent event){
        Optional<ItemStack> diaper = PuppyCraft.getDiaperOnPlayer(event.getEntity());
        if(diaper.isEmpty() || diaper.get().getOrDefault(HygieneDataTypes.HYGIENE_SOILED_CLOTHING, false)) return;
        diaper.get().set(HygieneDataTypes.HYGIENE_SOILED_CLOTHING, true);
        event.setCanceled(true);
    }
    @SubscribeEvent
    public void CanPlayerBeSlowedByUrineNeed(PlayerFullBladderEvent event) {
        event.setCanceled(PuppyCraft.getDiaperOnPlayer(event.getEntity()).isPresent() || (event.getEntity().getData(DeminuoAttachments.MATURITY.get()) < 8000 && Config.ENABLED_DEMINUO.getAsBoolean()));
    }
}
