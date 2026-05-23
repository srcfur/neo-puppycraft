package com.srcfur.puppycraft.diapers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Transformation;
import com.srcfur.puppycraft.PuppyCraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.joml.Vector3fc;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DiaperRenderer extends GeoArmorRenderer<DiaperItem> {
    public DiaperRenderer(){
        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(PuppyCraft.MODID, "item/diaperarmor.geo.json")));
    }
}
