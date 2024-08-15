package daniel.cushypillows.client;

import daniel.cushypillows.CushyPillows;
import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import daniel.cushypillows.client.particle.FeatherParticle;
import daniel.cushypillows.client.render.block.entity.PillowBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class CushyPillowsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CushyPillowsEntityModelLayers.initialize();

        BlockEntityRendererFactories.register(CushyPillowsBlockEntities.PILLOW, PillowBlockEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(CushyPillows.FEATHERS, new FeatherParticle.Factory());
    }
}
