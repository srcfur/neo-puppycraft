package com.srcfur.puppycraft;

import com.srcfur.puppycraft.diapers.DiaperData;
import com.srcfur.puppycraft.diapers.DiaperRegistrationEvent;
import com.srcfur.puppycraft.diapers.DiaperRenderer;
import com.srcfur.puppycraft.diapers.Diapers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = PuppyCraft.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = PuppyCraft.MODID, value = Dist.CLIENT)
public class PuppyCraftClient {
    public PuppyCraftClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }


    @SuppressWarnings("removal")
    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        PuppyCraft.LOGGER.info("[Diaper Register] Diapers Found: ".concat(Long.toString(Diapers.DIAPER_REGISTRY.stream().count())));
    }

}
