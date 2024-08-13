package daniel.cushypillows.client;

import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import daniel.cushypillows.client.render.block.entity.PillowBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class CushyPillowsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CushyPillowsEntityModelLayers.initialize();

        BlockRenderLayerMap.INSTANCE.putBlock(CushyPillowsBlocks.YELLOW_PILLOW, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(CushyPillowsBlockEntities.PILLOW, PillowBlockEntityRenderer::new);
    }
}
