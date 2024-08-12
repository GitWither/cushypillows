package daniel.cushypillows.block;

import daniel.cushypillows.CushyPillows;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public final class CushyPillowsBlocks {
    public static final Block WHITE_PILLOW = registerBlock("white_pillow", new PillowBlock(DyeColor.WHITE));
    public static final Block ORANGE_PILLOW = registerBlock("orange_pillow", new PillowBlock(DyeColor.ORANGE));
    public static final Block MAGENTA_PILLOW = registerBlock("magenta_pillow", new PillowBlock(DyeColor.MAGENTA));
    public static final Block LIGHT_BLUE_PILLOW = registerBlock("light_blue_pillow", new PillowBlock(DyeColor.LIGHT_BLUE));
    public static final Block YELLOW_PILLOW = registerBlock("yellow_pillow", new PillowBlock(DyeColor.YELLOW));
    public static final Block LIME_PILLOW = registerBlock("lime_pillow", new PillowBlock(DyeColor.LIME));
    public static final Block PINK_PILLOW = registerBlock("pink_pillow", new PillowBlock(DyeColor.PINK));
    public static final Block GRAY_PILLOW = registerBlock("gray_pillow", new PillowBlock(DyeColor.GRAY));
    public static final Block LIGHT_GRAY_PILLOW = registerBlock("light_gray_pillow", new PillowBlock(DyeColor.LIGHT_GRAY));
    public static final Block CYAN_PILLOW = registerBlock("cyan_pillow", new PillowBlock(DyeColor.CYAN));
    public static final Block PURPLE_PILLOW = registerBlock("purple_pillow", new PillowBlock(DyeColor.PURPLE));
    public static final Block BLUE_PILLOW = registerBlock("blue_pillow", new PillowBlock(DyeColor.BLUE));
    public static final Block BROWN_PILLOW = registerBlock("brown_pillow", new PillowBlock(DyeColor.BROWN));
    public static final Block GREEN_PILLOW = registerBlock("green_pillow", new PillowBlock(DyeColor.GREEN));
    public static final Block RED_PILLOW = registerBlock("red_pillow", new PillowBlock(DyeColor.RED));


    private static Block registerBlock(String id, Block block) {
        Identifier identifier = Identifier.of(CushyPillows.MOD_ID, id);

        Registry.register(Registries.ITEM, identifier, new BlockItem(block, new Item.Settings()));

        return Registry.register(Registries.BLOCK, identifier, block);
    }

    public static void initialize() {}
}
