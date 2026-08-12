package com.srcfur.puppycraft.deminuo.fluid;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.function.Consumer;

public class YouthFluidType extends FluidType {
    private final ResourceLocation stillTexture = ResourceLocation.withDefaultNamespace("block/water_still");;
    private final ResourceLocation flowingTexture = ResourceLocation.withDefaultNamespace("block/water_flow");
    public YouthFluidType(Properties properties) {
        super(properties);
    }
    public ResourceLocation getStillTexture() {
        return stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return flowingTexture;
    }

    public ResourceLocation getOverlayTexture() {
        return null;
    }

    @SuppressWarnings("removal")
    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public @Nullable ResourceLocation getOverlayTexture() {
                return null;
            }

            @Override
            public int getTintColor() {
                return new Color(150, 197, 230,120).getRGB();
            }
        });
    }
}
