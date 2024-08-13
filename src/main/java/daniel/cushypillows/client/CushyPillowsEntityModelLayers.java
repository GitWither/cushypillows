package daniel.cushypillows.client;

import daniel.cushypillows.CushyPillows;
import daniel.cushypillows.client.render.block.entity.PillowBlockEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public final class CushyPillowsEntityModelLayers {
    public static final EntityModelLayer PILLOW = registerModelLayer("pillow", "main", PillowBlockEntityRenderer::getTexturedModelData);


    private static EntityModelLayer registerModelLayer(String id, String name, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayer layer = new EntityModelLayer(
                new Identifier(CushyPillows.MOD_ID, id),
                name
        );

        EntityModelLayerRegistry.registerModelLayer(layer, provider);

        return layer;
    }

    public static void initialize() {

    }
}
