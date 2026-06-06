package com.srcfur.puppycraft.diapers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.concurrent.atomic.AtomicBoolean;

public class CurioDiaperRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
                                                                          SlotContext slotContext,
                                                                          PoseStack matrixStack,
                                                                          RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer,
                                                                          int light, float limbSwing,
                                                                          float limbSwingAmount,
                                                                          float partialTicks,
                                                                          float ageInTicks,
                                                                          float netHeadYaw,
                                                                          float headPitch) {
        // Render code goes here
        DiaperItem item = (DiaperItem) stack.getItem();

        item.createGeoRenderer( provider -> {
             DiaperRenderer renderer = (DiaperRenderer) provider.getGeoArmorRenderer(slotContext.entity(), stack, null, null);
            renderer.prepForRender(slotContext.entity(), stack, EquipmentSlot.LEGS, (HumanoidModel<?>) renderLayerParent.getModel(), renderTypeBuffer, partialTicks, limbSwing, limbSwingAmount, netHeadYaw, headPitch);
            renderer.defaultRender(matrixStack, item, renderTypeBuffer, null, null, netHeadYaw, partialTicks, light);
        });
    }
}
