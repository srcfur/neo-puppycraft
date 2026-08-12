package com.srcfur.puppycraft.blocks;

import com.srcfur.puppycraft.diapers.diaperbag.DiaperBagBlock;
import com.srcfur.puppycraft.diapers.diaperbag.DiaperBagEntity;
import com.srcfur.puppycraft.diapers.machinery.roller.DiaperRoller;
import com.srcfur.puppycraft.puppyblocks.PuppyPadBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.srcfur.puppycraft.PuppyCraft.MODID;
@Mod(MODID)
public class PuppyCraftBlocks {
    public PuppyCraftBlocks(IEventBus bus){
        BLOCKS.register(bus);
    }
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static DeferredBlock<DiaperBagBlock> DIAPER_BAG_BLOCK =
            BLOCKS.register("diaper_bag", () -> new DiaperBagBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static DeferredBlock<DiaperRoller> DIAPER_ROLLER_BLOCK =
            BLOCKS.register("diaper_roller", ()->new DiaperRoller(BlockBehaviour.Properties.of().noOcclusion()));
    public static DeferredBlock<PuppyPadBlock> PUPPY_PAD_BLOCK = BLOCKS.register("puppy_pad", ()-> new PuppyPadBlock(BlockBehaviour.Properties.of().noOcclusion().randomTicks()));
    public static DeferredBlock<Block> RAW_SALT_BLOCK = BLOCKS.register("seasalt", ()->new Block(BlockBehaviour.Properties.of().destroyTime(0.75f)));
}
