package daniel.cushypillows.client.render.block.entity;

import daniel.cushypillows.block.PillowBlock;
import daniel.cushypillows.block.entity.PillowBlockEntity;
import daniel.cushypillows.client.CushyPillowsEntityModelLayers;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.DecoratedPotBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.RotationPropertyHelper;

public class PillowBlockEntityRenderer implements BlockEntityRenderer<PillowBlockEntity> {
    private final ModelPart base;

    public PillowBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.base = ctx.getLayerModelPart(CushyPillowsEntityModelLayers.PILLOW);
    }

    public static TexturedModelData getTexturedModelData() {
        return DecoratedPotBlockEntityRenderer.getTopBottomNeckTexturedModelData();
    }

    @Override
    public void render(PillowBlockEntity pillow, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        BlockState cachedPillowState = pillow.getCachedState();
        float degrees = -RotationPropertyHelper.toDegrees(cachedPillowState.get(PillowBlock.ROTATION));

        matrices.translate(0.5, 0.0, 0.5);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(degrees));
        matrices.translate(-0.5, 0.0, -0.5);

        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getCutout());
        this.base.render(matrices, consumer, light, overlay);
        matrices.pop();
    }
}
