package com.srcfur.puppycraft.deminuo.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.srcfur.badhygiene.api.HygieneAPI;
import com.srcfur.puppycraft.Config;
import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.deminuo.attachments.DeminuoAttachments;
import com.srcfur.puppycraft.networking.PuppyComponents;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BabyBottle extends Item {
    public record BottleData(int urine){

    }
    public static final Codec<BottleData> BOTTLE_DATA_CODEC = RecordCodecBuilder.create( instance ->
        instance.group(Codec.INT.fieldOf("urine").forGetter(BottleData::urine)).apply(instance, BottleData::new));
    public static final StreamCodec<ByteBuf, BottleData> BOTTLE_DATA_STREAM = StreamCodec.composite(
            ByteBufCodecs.INT, BottleData::urine,
            BottleData::new
    );


    public BabyBottle(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 100;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        try{
            if(Config.ENABLED_DEMINUO.getAsBoolean()){
                Player plr = (Player) livingEntity;
                HygieneAPI.setBladderLevel(plr, HygieneAPI.getBladderLevel(plr) + stack.get(PuppyComponents.BOTTLE_COMPONENT).urine());
                plr.setData(DeminuoAttachments.MATURITY, Math.max(plr.getData(DeminuoAttachments.MATURITY) - 250, 0));
            }
        }catch(Exception ex){
            PuppyCraft.LOGGER.warn("Non player tried using a baby bottle... is my best guess :P", ex);
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
