package daniel.cushypillows.block;

import com.google.common.collect.Maps;
import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import daniel.cushypillows.block.entity.PillowBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

public class PillowBlock extends BlockWithEntity {
    private static final Map<DyeColor, Block> COLORED_PILLOWS = new EnumMap<>(DyeColor.class);

    public static final IntProperty ROTATION = Properties.ROTATION;
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 5.0, 14.0);

    private final DyeColor color;

    protected PillowBlock(DyeColor color) {
        super(Settings.create()
                .breakInstantly()
                .hardness(1.0f)
                .mapColor(MapColor.WHITE)
                .sounds(BlockSoundGroup.WOOL)
                .burnable()
        );

        this.setDefaultState(this.stateManager.getDefaultState().with(ROTATION, 0));
        this.color = color;
        COLORED_PILLOWS.put(color, this);
    }

    public DyeColor getColor() {
        return color;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(
                ROTATION, RotationPropertyHelper.fromYaw(ctx.getPlayerYaw() + 180.0f)
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient) {
            world.getBlockEntity(pos, CushyPillowsBlockEntities.PILLOW).ifPresent(pillow -> pillow.readFrom(itemStack));
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PillowBlockEntity(pos, state);
    }

    public static Block getForColor(DyeColor color) {
        return COLORED_PILLOWS.getOrDefault(color, CushyPillowsBlocks.WHITE_PILLOW);
    }
}
