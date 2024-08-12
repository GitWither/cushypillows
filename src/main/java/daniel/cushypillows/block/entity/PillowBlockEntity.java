package daniel.cushypillows.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class PillowBlockEntity extends BlockEntity {
    public PillowBlockEntity(BlockPos pos, BlockState state) {
        super(CushyPillowsBlockEntities.PILLOW_BLOCK_ENTITY_TYPE, pos, state);
    }
}
