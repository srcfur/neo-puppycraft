package com.srcfur.puppycraft.diapers.machinery.roller;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.srcfur.puppycraft.items.PuppyCraftItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class DiaperRollerRenderer implements BlockEntityRenderer<DiaperRollerEntity> {
    private ItemRenderer itemRenderer;

    public DiaperRollerRenderer(BlockEntityRendererProvider.Context context){
        this.itemRenderer = context.getItemRenderer();
    }
    @Override
    public void render(DiaperRollerEntity blockEntity, float partialTick, PoseStack stack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        // Do the rendering here.
        stack.pushPose();

        if(blockEntity.getBlockState().getValue(DiaperRoller.HAS_ROLL_READY).booleanValue()){
            stack.pushPose();
            stack.translate(-0.5, 0.5f, 0.5f);
            stack.scale(2,2,2);
            itemRenderer.renderStatic(new ItemStack(PuppyCraftItems.DIAPER_BACK_SHEET.get(), 1), ItemDisplayContext.GROUND,
                    packedLight, packedOverlay, stack, bufferSource, blockEntity.getLevel(), 0);
            stack.popPose();
        }
        stack.rotateAround(Axis.YP.rotation(blockEntity.getBlockState().getValue(DiaperRoller.FACING).toYRot() * 0.01f), 0, 0, 0);
        stack.popPose();
    }
}
