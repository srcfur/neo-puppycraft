package com.srcfur.puppycraft.blocks.entities;

import com.srcfur.puppycraft.blocks.PuppyCraftBlocks;
import com.srcfur.puppycraft.diapers.diaperbag.DiaperBagEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.srcfur.puppycraft.PuppyCraft.MODID;

@Mod(MODID)
public class PuppyCraftBlockEntities {
    public PuppyCraftBlockEntities(IEventBus bus){
        BLOCK_ENTITY.register(bus);
    }
    // Create a Deferred Register to hold Blocks which will all be registered under the "puppycraft" namespace
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY  = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<DiaperBagEntity>> DIAPER_BAG_ENTITY = BLOCK_ENTITY.register("diaper_bag_entity",
            ()->BlockEntityType.Builder.of(
                    DiaperBagEntity::new,
                    PuppyCraftBlocks.DIAPER_BAG_BLOCK.value()
            ).build(null));
}
