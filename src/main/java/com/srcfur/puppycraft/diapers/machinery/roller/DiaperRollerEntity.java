package com.srcfur.puppycraft.diapers.machinery.roller;

import com.srcfur.puppycraft.blocks.entities.PuppyCraftBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DiaperRollerEntity extends BlockEntity {
    protected DiaperRollerEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
    public DiaperRollerEntity(BlockPos pos, BlockState state){
        super(PuppyCraftBlockEntities.DIAPER_ROLLER_ENTITY.get(), pos, state);
    }
}
