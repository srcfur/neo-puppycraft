package com.srcfur.puppycraft.diapers.diaperbag;

import com.mojang.serialization.MapCodec;
import com.srcfur.puppycraft.PuppyCraft;
import com.srcfur.puppycraft.diapers.DiaperBagData;
import com.srcfur.puppycraft.diapers.DiaperCodecs;
import com.srcfur.puppycraft.diapers.DiaperItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiaperBagBlock extends BaseEntityBlock {
    public static final EnumProperty<DiaperFamilies> FAMILY = EnumProperty.create("diaperfamily", DiaperFamilies.class);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public DiaperBagBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(DiaperBagBlock::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FAMILY).add(FACING));
    }

    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        return (BlockState)this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DiaperBagEntity(PuppyCraft.DIAPER_BAG_ENTITY.value(), blockPos, blockState);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(stack.getItem().getClass() == DiaperItem.class){
            DiaperBagEntity ent = ((DiaperBagEntity)level.getBlockEntity(pos));
            stack = ent.insertDiaper(stack);
            player.setItemInHand(hand, stack);
            return InteractionResult.CONSUME;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(player.getMainHandItem().isEmpty()){
            if(player.isCrouching()){
                DiaperBagEntity ent = (DiaperBagEntity) level.getBlockEntity(pos);
                ItemStack diaperbag = new ItemStack(PuppyCraft.DIAPER_BAG_ITEM.value());
                diaperbag.setCount(1);
                diaperbag.set(DiaperCodecs.DIAPER_BAG_COMPONENT, new DiaperBagData(ent.diapersheld, ent.diapers.get(0).typeHolder().getRegisteredName()));
                player.setItemInHand(InteractionHand.MAIN_HAND, diaperbag);
                level.destroyBlock(pos, false);
            }else {
                player.setItemInHand(InteractionHand.MAIN_HAND, ((DiaperBagEntity) level.getBlockEntity(pos)).getDiaper());
            }
        }
        return InteractionResult.CONSUME;
    }
}
