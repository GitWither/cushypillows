package daniel.cushypillows.item;

import daniel.cushypillows.CushyPillows;
import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.PillowBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public final class CushyPillowsItems {
    public static final Item WHITE_PILLOW = registerBlockItem("white_pillow", new PillowItem(CushyPillowsBlocks.WHITE_PILLOW));
    public static final Item ORANGE_PILLOW = registerBlockItem("orange_pillow", new PillowItem(CushyPillowsBlocks.ORANGE_PILLOW));
    public static final Item MAGENTA_PILLOW = registerBlockItem("magenta_pillow", new PillowItem(CushyPillowsBlocks.MAGENTA_PILLOW));
    public static final Item LIGHT_BLUE_PILLOW = registerBlockItem("light_blue_pillow", new PillowItem(CushyPillowsBlocks.LIGHT_BLUE_PILLOW));
    public static final Item YELLOW_PILLOW = registerBlockItem("yellow_pillow", new PillowItem(CushyPillowsBlocks.YELLOW_PILLOW));
    public static final Item LIME_PILLOW = registerBlockItem("lime_pillow", new PillowItem(CushyPillowsBlocks.LIME_PILLOW));
    public static final Item PINK_PILLOW = registerBlockItem("pink_pillow", new PillowItem(CushyPillowsBlocks.PINK_PILLOW));
    public static final Item GRAY_PILLOW = registerBlockItem("gray_pillow", new PillowItem(CushyPillowsBlocks.GRAY_PILLOW));
    public static final Item LIGHT_GRAY_PILLOW = registerBlockItem("light_gray_pillow", new PillowItem(CushyPillowsBlocks.LIGHT_GRAY_PILLOW));
    public static final Item CYAN_PILLOW = registerBlockItem("cyan_pillow", new PillowItem(CushyPillowsBlocks.CYAN_PILLOW));
    public static final Item PURPLE_PILLOW = registerBlockItem("purple_pillow", new PillowItem(CushyPillowsBlocks.PURPLE_PILLOW));
    public static final Item BLUE_PILLOW = registerBlockItem("blue_pillow", new PillowItem(CushyPillowsBlocks.BLUE_PILLOW));
    public static final Item BROWN_PILLOW = registerBlockItem("brown_pillow", new PillowItem(CushyPillowsBlocks.BROWN_PILLOW));
    public static final Item GREEN_PILLOW = registerBlockItem("green_pillow", new PillowItem(CushyPillowsBlocks.GREEN_PILLOW));
    public static final Item RED_PILLOW = registerBlockItem("red_pillow", new PillowItem(CushyPillowsBlocks.RED_PILLOW));
    public static final Item BLACK_PILLOW = registerBlockItem("black_pillow", new PillowItem(CushyPillowsBlocks.BLACK_PILLOW));

    private static Item registerBlockItem(String id, Item item) {
        Identifier identifier = Identifier.of(CushyPillows.MOD_ID, id);

        return Registry.register(Registries.ITEM, identifier, item);
    }

    public static void initialize() {}
}
