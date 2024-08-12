package daniel.cushypillows;

import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.item.ItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class CushyPillows implements ModInitializer {
    public static final String MOD_ID = "cushypillows";
    public static final Logger LOGGER = LoggerFactory.getLogger(CushyPillows.class);


    @Override
    public void onInitialize() {
        LOGGER.debug("Cushy Pillows initialized!");

        CushyPillowsBlocks.initialize();
        CushyPillowsBlockEntities.initialize();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> {
            entries.add(CushyPillowsBlocks.WHITE_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.ORANGE_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.MAGENTA_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.LIGHT_BLUE_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.YELLOW_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.LIME_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.PINK_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.GRAY_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.LIGHT_GRAY_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.CYAN_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.PURPLE_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.BLUE_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.BROWN_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.GREEN_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.RED_PILLOW.asItem());
            entries.add(CushyPillowsBlocks.BLACK_PILLOW.asItem());
        });
    }
}
