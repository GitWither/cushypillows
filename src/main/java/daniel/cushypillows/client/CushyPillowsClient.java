package daniel.cushypillows.client;

import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import daniel.cushypillows.client.particle.FeatherParticle;
import daniel.cushypillows.client.render.block.entity.PillowBlockEntityRenderer;
import daniel.cushypillows.client.render.entity.PillowEntityRenderer;
import daniel.cushypillows.entity.CushyPillowsEntities;
import daniel.cushypillows.particle.CushyPillowsParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class CushyPillowsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CushyPillowsEntityModelLayers.initialize();

        BlockEntityRendererFactories.register(CushyPillowsBlockEntities.PILLOW, PillowBlockEntityRenderer::new);
        EntityRendererRegistry.register(CushyPillowsEntities.PILLOW_ENTITY, PillowEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(CushyPillowsParticleTypes.FEATHERS, new FeatherParticle.Factory());
    }
}