package com.srcfur.puppycraft.deminuo.attachments;

import com.mojang.serialization.Codec;
import com.srcfur.badhygiene.api.HygieneIntegerStreamCodec;
import com.srcfur.puppycraft.attachments.PuppyCraftAttachments;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.function.Supplier;

public class DeminuoAttachments {
    public static final Supplier<AttachmentType<Integer>> MATURITY = PuppyCraftAttachments.ATTACHMENTS
            .register("mental_maturity", ()-> AttachmentType.<Integer>builder(()->18).serialize(Codec.intRange(0,20)).sync(new HygieneIntegerStreamCodec()).build());
    public static void Initialize(){

    }
}
