package com.srcfur.puppycraft.diapers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.diapers.diaperbag.DiaperFamilies;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.*;
import net.neoforged.neoforge.registries.callback.AddCallback;

import java.util.Optional;
import java.util.logging.Logger;

public class Diapers {
    public static final ResourceKey<Registry<DiaperData>> DIAPER_REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "diapers"));
    public static final Registry<DiaperData> DIAPER_REGISTRY = new RegistryBuilder<>(DIAPER_REGISTRY_KEY)
            .maxId(256)
            .onAdd((registry, i, resourceKey, diaperItem) -> NeoForge.EVENT_BUS.post(new DiaperRegistrationEvent(diaperItem)))
            .create();
    public static final Holder<SoundEvent> SOILING_NOISE = PuppyCraft.SOUND_EVENTS.register("diaper_soiling", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "diaper_soiling")));
    private static final DeferredRegister<DiaperData> MOD_DIAPER_REGISTRY = DeferredRegister.create(DIAPER_REGISTRY, PuppyCraft.MODID);


    @SubscribeEvent // on the mod event bus
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(DIAPER_REGISTRY);
    }
    public static void register(RegisterEvent event) {
        RegisterDiaper(event, Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "cheapdiaper"), Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "textures/models/armor/diapers/cheap"), DiaperFamilies.CHEAP);
        RegisterDiaper(event, Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "bunnyhoppsdiaper"), Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "textures/models/armor/diapers/bunnyhopps"), DiaperFamilies.BUNNYHOPPS);
        RegisterDiaper(event, Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "medicaldiaper"), Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "textures/models/armor/diapers/medical"), DiaperFamilies.MEDICAL);
        RegisterDiaper(event, Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "megamaxdiaper"), Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "textures/models/armor/diapers/mega"), DiaperFamilies.MEGAMAX);
        RegisterDiaper(event, Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "pullupdiaper"), Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "textures/models/armor/diapers/pullup"), DiaperFamilies.PULLUPS);
        RegisterDiaper(event, Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "subspacediaper"), Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "textures/models/armor/diapers/subspace"), DiaperFamilies.PHIGHTING);
    }
    public static void RegisterDiaper(RegisterEvent event, Identifier location, Identifier texture, DiaperFamilies family){
        event.register(BuiltInRegistries.ITEM.key(), registry -> {
            registry.register(location, new DiaperItem(new Item.Properties().stacksTo(1)
                    .component(DiaperCodecs.DIAPER_DATA_COMPONENT.value(), new DiaperStackData(0)).setId(ResourceKey.create(Registries.ITEM, location)), texture, family));
        });
        event.register(DIAPER_REGISTRY_KEY, diaper -> {
            diaper.register(location, new DiaperData(location));
            PuppyCraft.LOGGER.info("OwO Diaper Porn :3");
        });
    }
    public static void initialize(){

    }
}
