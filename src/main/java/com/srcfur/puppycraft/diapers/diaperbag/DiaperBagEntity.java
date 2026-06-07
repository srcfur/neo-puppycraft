package com.srcfur.puppycraft.diapers.diaperbag;

import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.diapers.DiaperBagData;
import com.srcfur.puppycraft.diapers.DiaperCodecs;
import com.srcfur.puppycraft.diapers.DiaperItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;

public class DiaperBagEntity extends BlockEntity implements IItemHandler {
    public DiaperBagEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        //Initialize :3
        for(int i = 0; i < 10; i++){
            diapers.add(ItemStack.EMPTY);
        }
    }
    public DiaperBagEntity(BlockPos pos, BlockState state){
        super(PuppyCraft.DIAPER_BAG_ENTITY.value(), pos, state);
        //Initialize :3
        for(int i = 0; i < 10; i++){
            diapers.add(ItemStack.EMPTY);
        }
    }

    public ArrayList<ItemStack> diapers = new ArrayList<ItemStack>(10);;
    int diapersheld = 0;

    public void UpdateDiaperBag(){
        getBlockState();
    }

    @Override
    public int getSlots() {
        return 10;
    }

    public ItemStack getDiaper(){
        if(diapersheld == 0){ return ItemStack.EMPTY; }
        diapersheld--;
        if(diapersheld == 0){
            getLevel().setBlockAndUpdate(getBlockPos(), getBlockState().setValue(DiaperBagBlock.FAMILY, DiaperFamilies.Generic));
        }
        return diapers.get(diapersheld);
    }
    public ItemStack insertDiaper(ItemStack diaper){
        if(diapersheld == 10 || diaper.getItem().getClass() != DiaperItem.class || diaper.isEmpty()) { return diaper; }
        if(diapersheld > 0) { if(((DiaperItem)diaper.getItem()).family != ((DiaperItem)diapers.get(0).getItem()).family) { return diaper; } }
        if(diaper.get(DiaperCodecs.DIAPER_DATA_COMPONENT).urine() > 0) { return diaper; }

        diapers.set(diapersheld, diaper);
        diapersheld++;

        getLevel().setBlockAndUpdate(getBlockPos(), getBlockState().setValue(DiaperBagBlock.FAMILY, ((DiaperItem)diaper.getItem()).family));
        this.setChanged();

        return ItemStack.EMPTY;
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return diapers.get(i);
    }

    @Override
    public ItemStack insertItem(int i, ItemStack itemStack, boolean b) {
        if(!b){
            itemStack = insertDiaper(itemStack);
        }
        return itemStack;
    }

    @Override
    public ItemStack extractItem(int i, int amount, boolean b) {
        if(diapersheld == 0){
            return ItemStack.EMPTY;
        }
        if(!b){
            return getDiaper();
        }
        return diapers.get(diapersheld - 1);
    }

    @Override
    public int getSlotLimit(int i) {
        return 1;
    }

    @Override
    public boolean isItemValid(int i, ItemStack itemStack) {
        return false;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        diapersheld = tag.getInt("diapersheld");
        for(int i = 0; i < diapersheld; i++){
            diapers.set(i, ItemStack.parse(registries, tag.getList("diapers", ListTag.TAG_COMPOUND).get(i)).get());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ListTag pampers = new ListTag();
        for(int i = 0; i < diapersheld; i++){
            pampers.add(diapers.get(i).save(registries));
        }
        tag.put("diapers", pampers);
        tag.putInt("diapersheld", diapersheld);
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        DiaperBagData data = componentInput.getOrDefault(DiaperCodecs.DIAPER_BAG_COMPONENT.value(), new DiaperBagData(0, ""));
        diapersheld = data.diapers();
        for(int i = 0; i < diapersheld; i++){
            diapers.set(i, new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(data.diaperaddress()))));
        }
        if(!diapers.get(0).isEmpty()){
            getLevel().setBlockAndUpdate(getBlockPos(), getBlockState().setValue(DiaperBagBlock.FAMILY, ((DiaperItem)diapers.get(0).getItem()).family));
        }
    }

    public ItemStack asItemStack(){
        ItemStack diaperbag = new ItemStack(PuppyCraft.DIAPER_BAG_ITEM.value());
        diaperbag.setCount(1);
        diaperbag.set(DiaperCodecs.DIAPER_BAG_COMPONENT, new DiaperBagData(diapersheld, diapers.get(0).getItemHolder().getRegisteredName()));
        return diaperbag;
    }

    /*
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        diapersheld = tag.getInt("diapersheld");
        for(int i = 0; i < diapersheld; i++){
            diapers.set(i, ItemStack.parse(registries, tag.getList("diapers", Tag.TAG_COMPOUND).get(i)).get());
        }
    }
    @Override
    protected void saveAdditional(ValueOutput tag) {
        super.saveAdditional(tag);
        ListTag pampers = new ListTag();
        for(int i = 0; i < diapersheld; i++){
            //pampers.add(diapers.get(i).save(registries));
        }
        //tag.putChild("diapers", pampers);
        tag.putInt("diapersheld", diapersheld);
    }


    @Override
    protected void applyImplicitComponents(DataComponentGetter componentInput) {
        super.applyImplicitComponents(componentInput);
        if(!componentInput.has(DiaperCodecs.DIAPER_BAG_COMPONENT)) return;
        diapersheld = componentInput.get(DiaperCodecs.DIAPER_BAG_COMPONENT).diapers();
        for(int i = 0; i < diapersheld; i++){
            diapers.set(i, new ItemStack(BuiltInRegistries.ITEM.get(Identifier.parse(componentInput.get(DiaperCodecs.DIAPER_BAG_COMPONENT).diaperaddress())).get()));
        }
        if(!diapers.get(0).isEmpty()){
            getLevel().setBlockAndUpdate(getBlockPos(), getBlockState().setValue(DiaperBagBlock.FAMILY, ((DiaperItem)diapers.get(0).getItem()).family));

        }
    }

 */
}
