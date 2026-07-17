package com.srcfur.puppycraft.networking;

import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.deminuo.items.BabyBottle;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(PuppyCraft.MODID)
public class PuppyComponents {
    public PuppyComponents(IEventBus bus){
        DATA_COMPONENTS.register(bus);
    }
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PuppyCraft.MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BabyBottle.BottleData>> BOTTLE_COMPONENT = DATA_COMPONENTS.registerComponentType(
            "bb_bottle_data",
            builder -> builder
                    .persistent(BabyBottle.BOTTLE_DATA_CODEC)
                    .networkSynchronized(BabyBottle.BOTTLE_DATA_STREAM)
    );
}
