package daniel.cushypillows;

import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
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
    }
}
