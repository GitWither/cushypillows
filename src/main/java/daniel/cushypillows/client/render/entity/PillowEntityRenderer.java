package daniel.cushypillows.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.math.RotationAxis;

public class PillowEntityRenderer extends FlyingItemEntityRenderer<ThrownItemEntity> {
	private final ItemRenderer itemRenderer;

	public PillowEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, 1.0f, true);

		this.itemRenderer = ctx.getItemRenderer();
	}

	@Override
	public void render(ThrownItemEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		float rotationDegrees = (entity.age + tickDelta) * 20;

		matrices.push();
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotationDegrees));
		matrices.scale(4, 4, 4);

		itemRenderer.renderItem(entity.getStack(), ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
		matrices.pop();
	}
}