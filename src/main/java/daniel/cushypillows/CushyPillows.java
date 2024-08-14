package daniel.cushypillows;

import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import daniel.cushypillows.item.CushyPillowsItems;
import daniel.cushypillows.recipe.CushyPillowsRecipeTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CushyPillows implements ModInitializer {
    public static final String MOD_ID = "cushypillows";
    public static final Logger LOGGER = LoggerFactory.getLogger(CushyPillows.class);


    @Override
    public void onInitialize() {
        LOGGER.debug("Cushy Pillows initialized!");

        CushyPillowsRecipeTypes.initialize();
        CushyPillowsBlocks.initialize();
        CushyPillowsItems.initialize();
        CushyPillowsBlockEntities.initialize();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> {
            entries.add(CushyPillowsItems.WHITE_PILLOW);
            entries.add(CushyPillowsItems.ORANGE_PILLOW);
            entries.add(CushyPillowsItems.MAGENTA_PILLOW);
            entries.add(CushyPillowsItems.LIGHT_BLUE_PILLOW);
            entries.add(CushyPillowsItems.YELLOW_PILLOW);
            entries.add(CushyPillowsItems.LIME_PILLOW);
            entries.add(CushyPillowsItems.PINK_PILLOW);
            entries.add(CushyPillowsItems.GRAY_PILLOW);
            entries.add(CushyPillowsItems.LIGHT_GRAY_PILLOW);
            entries.add(CushyPillowsItems.CYAN_PILLOW);
            entries.add(CushyPillowsItems.PURPLE_PILLOW);
            entries.add(CushyPillowsItems.BLUE_PILLOW);
            entries.add(CushyPillowsItems.BROWN_PILLOW);
            entries.add(CushyPillowsItems.GREEN_PILLOW);
            entries.add(CushyPillowsItems.RED_PILLOW);
            entries.add(CushyPillowsItems.BLACK_PILLOW);
        });
    }
}
