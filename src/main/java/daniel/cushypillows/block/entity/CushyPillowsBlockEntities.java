package daniel.cushypillows.block.entity;

import daniel.cushypillows.CushyPillows;
import daniel.cushypillows.block.CushyPillowsBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class CushyPillowsBlockEntities {
    public static final BlockEntityType<PillowBlockEntity> PILLOW = registerBlockEntity(
            "pillow_block_entity",
            PillowBlockEntity::new,
            CushyPillowsBlocks.WHITE_PILLOW,
            CushyPillowsBlocks.ORANGE_PILLOW,
            CushyPillowsBlocks.MAGENTA_PILLOW,
            CushyPillowsBlocks.LIGHT_BLUE_PILLOW,
            CushyPillowsBlocks.YELLOW_PILLOW,
            CushyPillowsBlocks.LIME_PILLOW,
            CushyPillowsBlocks.PINK_PILLOW,
            CushyPillowsBlocks.GRAY_PILLOW,
            CushyPillowsBlocks.LIGHT_GRAY_PILLOW,
            CushyPillowsBlocks.CYAN_PILLOW,
            CushyPillowsBlocks.PURPLE_PILLOW,
            CushyPillowsBlocks.BLUE_PILLOW,
            CushyPillowsBlocks.BROWN_PILLOW,
            CushyPillowsBlocks.GREEN_PILLOW,
            CushyPillowsBlocks.RED_PILLOW,
            CushyPillowsBlocks.BLACK_PILLOW
    );

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String id, BlockEntityType.BlockEntityFactory<T> blockEntitySupplier, Block... blocks) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(CushyPillows.MOD_ID, id),
                BlockEntityType.Builder.create(blockEntitySupplier, blocks).build(null)
        );
    }

    public static void initialize() {}
}
