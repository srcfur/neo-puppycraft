package com.srcfur.puppycraft;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.api.HygieneAPI;
import com.srcfur.puppycraft.diapers.*;
import com.srcfur.puppycraft.diapers.diaperbag.DiaperBagBlock;
import com.srcfur.puppycraft.diapers.diaperbag.DiaperBagEntity;
import com.srcfur.puppycraft.diapers.diaperbag.DiaperBagItem;
import com.srcfur.puppycraft.puppyblocks.PuppyPadBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.List;
import java.util.Optional;

import static com.srcfur.puppycraft.diapers.Diapers.SOILING_NOISE;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PuppyCraft.MODID)
public class PuppyCraft {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "puppycraft";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "puppycraft" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY  = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "puppycraft" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "puppycraft" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);

    public static DeferredBlock<DiaperBagBlock> DIAPER_BAG_BLOCK;
    public static DeferredBlock<PuppyPadBlock> PUPPY_PAD_BLOCK;
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<DiaperBagEntity>> DIAPER_BAG_ENTITY;
    public static DeferredItem<BlockItem> DIAPER_BAG_ITEM;

    public static final int MINIMUM_CONTINENCE_LEVEL = 30;
    public static final int ACCIDENT_CONTINENCE_PUNISHMENT = -7;
    public static final int MAXIMUM_CONTINENCE_LEVEL = 100;


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public PuppyCraft(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        if(ModList.get().isLoaded("badhygiene")){
            LOGGER.info("UwU we have badhygiene :3!!");
        }

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        BLOCK_ENTITY.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        DATA_COMPONENTS.register(modEventBus);



        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (PuppyCraft) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(Diapers::register);
        modEventBus.addListener(PuppyCraft::registerRegistries);
        modEventBus.addListener(PuppyCraft::registerCapabilities);

        DiaperCodecs.Initialize();

        HygieneAPI.registerWettingEvent((player)->{
            Optional<ItemStack> playerDiaper = getDiaperOnPlayer(player);
            if(playerDiaper.isPresent()){
                int absorbency = Math.max(Math.min(playerDiaper.get().getMaxDamage() - playerDiaper.get().get(DiaperCodecs.DIAPER_DATA_COMPONENT.value()).urine(), HygieneAPI.getCalculatedContinence(player)), 0);
                playerDiaper.get().set(DiaperCodecs.DIAPER_DATA_COMPONENT.value(), new DiaperStackData(playerDiaper.get().get(DiaperCodecs.DIAPER_DATA_COMPONENT.value()).urine() + absorbency));
                player.playSound(SOILING_NOISE.value());
                return absorbency >= HygieneAPI.getBladderLevel(player);
            }
            HygieneAPI.setContinence(player, Math.max(HygieneAPI.getContinence(player), HygieneAPI.getContinence(player) + ACCIDENT_CONTINENCE_PUNISHMENT));
            return false;
        });

        //HygieneAPI.event_player_pee_puddle.add(player -> {
        //    return player.getInBlockState().getBlock() == PUPPY_PAD_BLOCK.get();
        //});

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        //Building our diaperbag here!
        DIAPER_BAG_BLOCK =
                BLOCKS.register("diaper_bag", () -> new DiaperBagBlock(BlockBehaviour.Properties.of().noOcclusion()));
        DIAPER_BAG_ENTITY =
                BLOCK_ENTITY.register("diaper_bag_entity",
                        ()->BlockEntityType.Builder.of(
                                DiaperBagEntity::new,
                                DIAPER_BAG_BLOCK.value()
                        ).build(null));
        DIAPER_BAG_ITEM = ITEMS.register("diaper_bag", ()-> new DiaperBagItem(DIAPER_BAG_BLOCK.value(), new Item.Properties().stacksTo(1)));

        PUPPY_PAD_BLOCK = BLOCKS.register("puppy_pad", ()-> new PuppyPadBlock(BlockBehaviour.Properties.of().noOcclusion()));
        ITEMS.register("puppy_pad", ()->new BlockItem(PUPPY_PAD_BLOCK.value(), new Item.Properties()));

        CREATIVE_MODE_TABS.register(PuppyCraft.MODID,
                ()-> CreativeModeTab.builder()
                        .title(Component.translatable("itemgroup." + MODID + ".diapers"))
                        .icon(()->{
                            DiaperData data = Diapers.DIAPER_REGISTRY.get(ResourceLocation.fromNamespaceAndPath(PuppyCraft.MODID, "medicaldiaper"));
                            if(data == null){
                                return ItemStack.EMPTY;
                            }
                            return new ItemStack(data.GetItem(), 0);
                })
                        .displayItems((x,y) -> {
                            Diapers.DIAPER_REGISTRY.stream().forEach(diaper ->{
                                y.accept(diaper.GetItem());
                            });
                            Diapers.DIAPER_REGISTRY.stream().forEach(diaper -> {
                                ItemStack bagOfDiapers = new ItemStack(DIAPER_BAG_ITEM.get());
                                bagOfDiapers.set(DiaperCodecs.DIAPER_BAG_COMPONENT, new DiaperBagData(diaper.GetItem().family.GetMaxCount(), new ItemStack(diaper.GetItem()).getItemHolder().getRegisteredName()));
                                y.accept(bagOfDiapers);
                            });
                        })
                        .build());
        Diapers.initialize();
        PuppyCraftItems.Initialize();
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    public static Optional<ItemStack> getDiaperOnPlayer(Player player){
        Optional<ICuriosItemHandler> inventory = CuriosApi.getCuriosInventory(player);
        if(inventory.isPresent()){
            if(inventory.get().getStacksHandler("underwear").isPresent()){
                ICurioStacksHandler stack = inventory.get().getStacksHandler("underwear").get();
                if(!stack.getStacks().getStackInSlot(0).isEmpty()){
                    ItemStack undies = stack.getStacks().getStackInSlot(0);
                    if(undies.getItem().getClass() == DiaperItem.class){
                        return Optional.of(undies);
                    }
                }
            }
        }
        return Optional.empty();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(PuppyCraftItems.WOOD_PULP);
            event.accept(PuppyCraftItems.CHEAP_ABSORBENT_POLYMER);
            event.accept(PuppyCraftItems.SUPER_ABSORBENT_POLYMER);
            event.accept(PuppyCraftItems.CHEAP_DIAPER_CORE);
            event.accept(PuppyCraftItems.NORMAL_DIAPER_CORE);
            event.accept(PuppyCraftItems.PREMIUM_DIAPER_CORE);
        }
    }

    public static void registerRegistries(NewRegistryEvent event) {
        event.register(Diapers.DIAPER_REGISTRY);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    public static void registerCapabilities(final RegisterCapabilitiesEvent evt) {
        List<DiaperData> diaperdata = Diapers.DIAPER_REGISTRY.stream().toList();
        for(int i = 0; i < diaperdata.size(); i++){
            evt.registerItem(
                    CuriosCapability.ITEM,
                    (stack, context) -> new ICurio() {
                        @Override
                        public ItemStack getStack(){
                            return stack;
                        }
                    },
                    diaperdata.get(i).GetItem()
            );
        }
    }
}
