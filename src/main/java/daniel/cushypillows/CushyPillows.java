package daniel.cushypillows;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CushyPillows implements ModInitializer {
    public static Logger LOGGER = LoggerFactory.getLogger(CushyPillows.class);


    @Override
    public void onInitialize() {
        LOGGER.debug("Cushy Pillows initialized!");
    }
}
