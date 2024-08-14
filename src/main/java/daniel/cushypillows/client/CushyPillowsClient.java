package daniel.cushypillows.client;

import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import daniel.cushypillows.client.render.block.entity.PillowBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class CushyPillowsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CushyPillowsEntityModelLayers.initialize();

        BlockEntityRendererFactories.register(CushyPillowsBlockEntities.PILLOW, PillowBlockEntityRenderer::new);
    }
}
