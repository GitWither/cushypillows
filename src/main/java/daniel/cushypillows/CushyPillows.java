package daniel.cushypillows;

import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import daniel.cushypillows.entity.CushyPillowsEntities;
import daniel.cushypillows.item.CushyPillowsItems;
import daniel.cushypillows.particle.CushyPillowsParticleTypes;
import daniel.cushypillows.recipe.CushyPillowsRecipeTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.item.ItemGroups;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CushyPillows implements ModInitializer {
    public static final String MOD_ID = "cushypillows";
    public static final Logger LOGGER = LoggerFactory.getLogger(CushyPillows.class);

    public static final DefaultParticleType FEATHERS = FabricParticleTypes.simple();


    @Override
    public void onInitialize() {
        LOGGER.debug("Cushy Pillows initialized!");

        CushyPillowsParticleTypes.initialize();
        CushyPillowsRecipeTypes.initialize();
        CushyPillowsBlocks.initialize();
        CushyPillowsItems.initialize();
        CushyPillowsBlockEntities.initialize();
        CushyPillowsEntities.initialize();

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
