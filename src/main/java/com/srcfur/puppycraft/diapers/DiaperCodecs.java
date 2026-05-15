package com.srcfur.puppycraft.diapers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.srcfur.puppycraft.PuppyCraft;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DiaperCodecs {
    public static final Codec<DiaperStackData> DIAPER_STACK_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Codec.INT.fieldOf("urine").forGetter(DiaperStackData::urine)).apply(instance, DiaperStackData::new));
    public static final StreamCodec<ByteBuf, DiaperStackData> DIAPER_STACK_DATA_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DiaperStackData::urine,
            DiaperStackData::new
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DiaperStackData>> DIAPER_DATA_COMPONENT = PuppyCraft.DATA_COMPONENTS.registerComponentType(
            "diaper_data",
            builder -> builder
                    // The codec to read/write the data to disk
                    .persistent(DIAPER_STACK_DATA_CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(DIAPER_STACK_DATA_STREAM_CODEC)
    );

    public static final Codec<DiaperBagData> DIAPER_BAG_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Codec.INT.fieldOf("diapers").forGetter(DiaperBagData::diapers))
                    .and(Codec.STRING.fieldOf("diaperaddress").forGetter(DiaperBagData::diaperaddress))
                    .apply(instance, DiaperBagData::new));
    public static final StreamCodec<ByteBuf, DiaperBagData> DIAPER_BAG_DATA_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DiaperBagData::diapers,
            ByteBufCodecs.STRING_UTF8, DiaperBagData::diaperaddress,
            DiaperBagData::new
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DiaperBagData>> DIAPER_BAG_COMPONENT = PuppyCraft.DATA_COMPONENTS.registerComponentType(
       "diaper_bag_data",
       builder ->
               builder.persistent(DIAPER_BAG_DATA_CODEC)
               .networkSynchronized(DIAPER_BAG_DATA_STREAM_CODEC)
    ) ;
    public static void Initialize(){

    }
}
