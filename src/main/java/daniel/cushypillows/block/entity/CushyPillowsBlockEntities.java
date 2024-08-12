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
    public static final BlockEntityType<PillowBlockEntity> PILLOW_BLOCK_ENTITY_TYPE = registerBlockEntity(
            PillowBlockEntity::new,
            CushyPillowsBlocks.PILLOW,
            "pillow_block_entity"
    );

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(BlockEntityType.BlockEntityFactory<T> blockEntitySupplier, Block block, String id) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(CushyPillows.MOD_ID, id),
                BlockEntityType.Builder.create(blockEntitySupplier, block).build(null)
        );
    }

    public static void initialize() {}
}
