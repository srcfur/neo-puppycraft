package com.srcfur.puppycraft.fluid;

import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.deminuo.fluid.YouthFluid;
import com.srcfur.puppycraft.deminuo.fluid.YouthFluidType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.srcfur.puppycraft.PuppyCraft.MODID;

@Mod(MODID)
public class PuppyCraftFluids {
    public PuppyCraftFluids(IEventBus modbus){
        FLUIDS.register(modbus);
        FLUID_TYPES.register(modbus);
    }
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, PuppyCraft.MODID);
    public static final DeferredRegister<FluidType>FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, PuppyCraft.MODID);

    public static final DeferredHolder<FluidType, FluidType> YOUTH_FLUID_TYPE = FLUID_TYPES.register("youth",
            ()->new YouthFluidType(FluidType.Properties.create().canSwim(false).canDrown(false).canPushEntity(false).canConvertToSource(false).lightLevel(4)));

    public static final DeferredHolder<Fluid, FlowingFluid> YOUTH_FLUID_STILL = FLUIDS.register("youth_still", YouthFluid.Source::new);
    public static final DeferredHolder<Fluid, FlowingFluid> YOUTH_FLUID_FLOWING = FLUIDS.register("youth_flowing", YouthFluid.Flowing::new);
}
