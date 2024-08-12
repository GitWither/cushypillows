package daniel.cushypillows.block;

import daniel.cushypillows.CushyPillows;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class CushyPillowsBlocks {
    public static final Block PILLOW = registerBlock(new PillowBlock(), "pillow");

    private static Block registerBlock(Block block, String id) {
        Identifier identifier = Identifier.of(CushyPillows.MOD_ID, id);

        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new Item.Settings()));

        return Registry.register(Registries.BLOCK, identifier, block);
    }

    public static void initialize() {}
}
