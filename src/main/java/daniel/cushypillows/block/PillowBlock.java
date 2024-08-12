package daniel.cushypillows.block;

import daniel.cushypillows.block.entity.PillowBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PillowBlock extends BlockWithEntity {
    private final DyeColor color;

    protected PillowBlock(DyeColor color) {
        super(Settings.create()
                .breakInstantly()
                .hardness(1.0f)
                .mapColor(MapColor.WHITE)
                .sounds(BlockSoundGroup.WOOL)
                .burnable()
        );

        this.color = color;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PillowBlockEntity(pos, state);
    }
}
