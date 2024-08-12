package daniel.cushypillows.block;

import daniel.cushypillows.block.entity.PillowBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PillowBlock extends BlockWithEntity {
    private final DyeColor color;

    protected PillowBlock(DyeColor color) {
        super(Settings.create().burnable());

        this.color = color;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PillowBlockEntity(pos, state);
    }
}
