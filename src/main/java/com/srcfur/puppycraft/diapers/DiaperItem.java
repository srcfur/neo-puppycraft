package com.srcfur.puppycraft.diapers;

import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.diapers.diaperbag.DiaperFamilies;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.awt.*;
import java.util.function.Consumer;

public class DiaperItem extends Item implements GeoItem, ICurioItem, GeoRenderProvider {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public ResourceLocation DIAPER_TEXTURE;
    public static final String USAGE_TAG = "urine";
    public DiaperFamilies family;
    public DiaperItem(Properties properties, ResourceLocation texture, DiaperFamilies family){
        super(properties);
        DIAPER_TEXTURE = texture;
        this.family = family;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getDamage(stack) > 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public int getDamage(ItemStack stack) {
        return stack.getOrDefault(DiaperCodecs.DIAPER_DATA_COMPONENT, new DiaperStackData(0)).urine();
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        stack.set(DiaperCodecs.DIAPER_DATA_COMPONENT, new DiaperStackData(damage));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
                if(this.renderer == null) // Important that we do this. If we just instantiate  it directly in the field it can cause incompatibilities with some mods.
                    this.renderer = new DiaperRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return PuppyCraft.getDiaperOnPlayer((Player) slotContext.entity()).isEmpty();
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if((stack.get(DiaperCodecs.DIAPER_DATA_COMPONENT).urine() > 0 || !((DiaperItem)stack.getItem()).family.IsPullup()) && PuppyCraft.DEVELOPMENT_BUILD){
            newStack.setCount(0);
        }
    }
}
