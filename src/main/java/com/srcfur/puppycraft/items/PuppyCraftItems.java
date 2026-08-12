package com.srcfur.puppycraft.items;

import com.srcfur.badhygiene.attributes.HygieneAttributes;
import com.srcfur.puppycraft.blocks.PuppyCraftBlocks;
import com.srcfur.puppycraft.deminuo.items.BabyBottle;
import com.srcfur.puppycraft.diapers.diaperbag.DiaperBagItem;
import com.srcfur.puppycraft.fluid.PuppyCraftFluids;
import com.srcfur.puppycraft.networking.PuppyComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.srcfur.puppycraft.PuppyCraft.MODID;
@Mod(MODID)
public class PuppyCraftItems {
    public PuppyCraftItems(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static DeferredItem<BlockItem> DIAPER_BAG_ITEM = ITEMS.register("diaper_bag", ()-> new DiaperBagItem(PuppyCraftBlocks.DIAPER_BAG_BLOCK.value(), new Item.Properties().stacksTo(1)));
    public static DeferredItem<BlockItem> PUPPY_PAD_ITEM = ITEMS.register("puppy_pad", ()->new BlockItem(PuppyCraftBlocks.PUPPY_PAD_BLOCK.value(), new Item.Properties()));
    public static DeferredItem<BlockItem> DIAPER_ROLLER = ITEMS.register("diaper_roller", ()->new BlockItem(PuppyCraftBlocks.DIAPER_ROLLER_BLOCK.value(), new Item.Properties()));

    //Sap pipeline
    public static DeferredItem<Item> WOOD_PULP = ITEMS.register("woodpulp", ()->new Item(new Item.Properties()));;
    public static DeferredItem<Item> CHEAP_ABSORBENT_POLYMER = ITEMS.register("cheapdiapersap", ()->new Item(new Item.Properties()));;
    public static DeferredItem<Item> SUPER_ABSORBENT_POLYMER = ITEMS.register("diapersap", ()->new Item(new Item.Properties()));

    //Diaper core pipeline
    public static DeferredItem<Item> CHEAP_DIAPER_CORE = ITEMS.register("cheapdiapercore", ()->new Item(new Item.Properties()));
    public static DeferredItem<Item> NORMAL_DIAPER_CORE = ITEMS.register("diapercore", ()->new Item(new Item.Properties()));;
    public static DeferredItem<Item> PREMIUM_DIAPER_CORE = ITEMS.register("threediapercore", ()->new Item(new Item.Properties()));;

    public static DeferredItem<Item> DIAPER_BACK_SHEET = ITEMS.register("clothbacksheet", ()-> new Item(new Item.Properties()));;

    public static DeferredItem<Item> DIAPER_TRASH = ITEMS.register("balled_diaper", ()-> new Item(new Item.Properties()
            .stacksTo(1)));

    public static DeferredItem<Item> SEASALT_BLOCK = ITEMS.register("seasalt", ()->new BlockItem(PuppyCraftBlocks.RAW_SALT_BLOCK.get(), new Item.Properties()));
    public static DeferredItem<Item> RAW_SALT = ITEMS.register("raw_salt", ()->new Item(new Item.Properties()));
    public static DeferredItem<Item> SALT = ITEMS.register("salt", ()->new Item(new Item.Properties()));

    //Deminuo
    public static DeferredItem<Item> FOY_BUCKET = ITEMS.register("youth_bucket", ()->
            new BucketItem(PuppyCraftFluids.YOUTH_FLUID_STILL.get(), new Item.Properties().stacksTo(1)));
    public static DeferredItem<Item> BABY_BOTTLE = ITEMS.register("empty_baby_bottle", ()->new Item(new Item.Properties()
            .stacksTo(1)));
    public static DeferredItem<Item> MILK_BABY_BOTTLE = ITEMS.register("baby_bottle_milk", ()->new BabyBottle(new Item.Properties()
            .stacksTo(1)
            .component(PuppyComponents.BOTTLE_COMPONENT, new BabyBottle.BottleData(20))
            .food(new FoodProperties.Builder()
                    .alwaysEdible()
                    .nutrition(12)
                    .usingConvertsTo(BABY_BOTTLE)
                    .effect(()->new MobEffectInstance(HygieneAttributes.INCONTINENCE_EFFECT, 1200, 1), 1)
                    .build())));
}
