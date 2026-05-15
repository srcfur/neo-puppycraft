package com.srcfur.puppycraft.diapers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Transformation;
import com.srcfur.puppycraft.PuppyCraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.joml.Vector3fc;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DiaperRenderer implements ICurioRenderer {
    @Override
    public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int packedLight,
            S renderState,
            RenderLayerParent<S, M> renderLayerParent,
            EntityRendererProvider.Context context,
            float yRotation,
            float xRotation) {
        ModelPart part = new ModelPart(
                List.of(new ModelPart.Cube(
                        0,
                        0,
                        0,
                        0,
                        0,
                        5,
                        4.25f,
                        6,
                        0,
                        0,
                        0,
                        false,
                        128,
                        128,
                        Set.of(Direction.DOWN)
                )),
                Map.of()
        );
        ICurioRenderer.translateIfSneaking(poseStack, slotContext.entity());
        ICurioRenderer.rotateIfSneaking(poseStack, slotContext.entity());
        submitNodeCollector.submitModelPart(part, poseStack, RenderTypes.armorTranslucent(Identifier.fromNamespaceAndPath(PuppyCraft.MODID, "textures/models/armor/diapers/pullup0.png")), packedLight, 0, null);
        ICurioRenderer.super.render(stack, slotContext, poseStack, submitNodeCollector, packedLight, renderState, renderLayerParent, context, yRotation, xRotation);
    }
}
