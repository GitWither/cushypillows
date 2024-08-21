package daniel.cushypillows.client.render.block.entity;

import daniel.cushypillows.CushyPillows;
import daniel.cushypillows.block.PillowBlock;
import daniel.cushypillows.block.entity.PillowBlockEntity;
import daniel.cushypillows.client.CushyPillowsEntityModelLayers;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.RotationPropertyHelper;

import java.util.List;

public class PillowBlockEntityRenderer implements BlockEntityRenderer<PillowBlockEntity> {
    public static final SpriteIdentifier PILLOW_BODY = new SpriteIdentifier(
            SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
            Identifier.of(CushyPillows.MOD_ID,"entity/pillow/pillow")
    );

    private final ModelPart root;
    private final ModelPart main;
    private final ModelPart trim;
    private final ModelPart pattern;

    public PillowBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.root = ctx.getLayerModelPart(CushyPillowsEntityModelLayers.PILLOW);
        this.main = root.getChild("main");
        this.trim = main.getChild("trim");
        this.pattern = root.getChild("pattern");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 14).cuboid(2.0F, -30.0F, 4.0F, 12.0F, 5.0F, 8.0F, new Dilation(0.3F)), ModelTransform.of(0.0F, -25.0F, 16.0F, 3.1416F, 0.0F, 0.0F));
        ModelPartData trim = main.addChild("trim", ModelPartBuilder.create().uv(0, 0).cuboid(-21.0F, -2.5F, 1.0F, 18.0F, 0.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(20.0F, -23.25F, 0.0F));
        ModelPartData pattern = modelPartData.addChild("pattern", ModelPartBuilder.create().uv(1, 8).mirrored().cuboid(2.0F, 4.0F, 15.0F, 12.0F, 8.0F, 0.0F, new Dilation(0.301F)).mirrored(false)
                .uv(-4, 3).mirrored().cuboid(2.0F, 4.0F, 15.0F, 12.0F, 0.0F, 5.0F, new Dilation(0.301F)).mirrored(false), ModelTransform.of(0.0F, 20.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
        ModelPartData cube_r1 = pattern.addChild("cube_r1", ModelPartBuilder.create().uv(-4, 16).mirrored().cuboid(-6.0F, 0.0F, -2.5F, 12.0F, 0.0F, 5.0F, new Dilation(0.301F)).mirrored(false), ModelTransform.of(8.0F, 12.0F, 17.5F, 3.1416F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(PillowBlockEntity pillow, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        BlockState cachedPillowState = pillow.getCachedState();
        float degrees = -RotationPropertyHelper.toDegrees(cachedPillowState.get(PillowBlock.ROTATION));

        matrices.translate(0.5, 0.0, 0.5);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(degrees));
        matrices.translate(-0.5, 0.0, -0.5);

        this.trim.visible = !cachedPillowState.get(PillowBlock.TRIMMED);

        if (cachedPillowState.get(PillowBlock.ATTACHED)) {
            matrices.scale(0.85f, 0.45f, 0.85f);
            matrices.translate(0.1, -1f, 0);
        }

        if (pillow.getWorld() != null) {
            float time = ((float)(pillow.getWorld().getTime() - pillow.getLastSquishTime()) + tickDelta) / 5;
            if (Math.abs(time) <= 1.0f) {
                matrices.scale(1, MathHelper.cos(time * MathHelper.TAU) * 0.4f + 0.6f, 1);
            }
        }

        BannerPatternsComponent patterns = pillow.getPatterns();

        VertexConsumer mainBodyConsumer = PILLOW_BODY.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        this.main.render(matrices, mainBodyConsumer, light, overlay, pillow.getBaseColor().getEntityColor());

        renderPatterns(matrices, vertexConsumers, light, overlay, this.pattern, patterns);

        matrices.pop();
    }

    private static void renderPatterns(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, BannerPatternsComponent patterns) {
        List<BannerPatternsComponent.Layer> layers = patterns.layers();

        for (int i = 0; i < 17 && i < layers.size(); i++) {
            BannerPatternsComponent.Layer entry = layers.get(i);

            RegistryEntry<BannerPattern> bannerPatternEntry = entry.pattern();
            if (bannerPatternEntry.getKey().isEmpty()) continue;

            SpriteIdentifier patternSprite = TexturedRenderLayers.getShieldPatternTextureId(bannerPatternEntry);
            VertexConsumer consumer = patternSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline);

            canvas.render(matrices, consumer, light, overlay, entry.color().getEntityColor());
        }
    }
}
