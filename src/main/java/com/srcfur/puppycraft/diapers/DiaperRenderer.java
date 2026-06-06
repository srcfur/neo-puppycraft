package com.srcfur.puppycraft.diapers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Transformation;
import com.srcfur.puppycraft.PuppyCraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3fc;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DiaperRenderer extends GeoArmorRenderer<DiaperItem> {
    public DiaperRenderer(){
        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(PuppyCraft.MODID, "diaperarmor")));
    }

    @Override
    public ResourceLocation getTextureLocation(DiaperItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(animatable.DIAPER_TEXTURE.getNamespace(), animatable.DIAPER_TEXTURE.getPath() + "0.png");
    }

    @Override
    protected void setAllBonesVisible(boolean visible) {
        super.setAllBonesVisible(true);
        int soil_version =  (int)(1 - ((float)currentStack.getDamageValue() / (float)currentStack.getMaxDamage()) * 4);
        for(int i = 0; i < 4; i++){
            if(i >= soil_version){
                var bone = model.getBone("soiling" + i);
                bone.ifPresent(geoBone -> setBoneVisible(geoBone, false));
            }
        }
    }

    @Override
    public void preRender(PoseStack poseStack, DiaperItem animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        if (!(this.currentEntity instanceof GeoAnimatable)) {

        }
    }
}
