package daniel.cushypillows.client.render.entity;

import daniel.cushypillows.entity.PillowEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.math.RotationAxis;

public class PillowEntityRenderer extends FlyingItemEntityRenderer<ThrownItemEntity> {
	public PillowEntityRenderer(EntityRendererFactory.Context ctx, float scale, boolean lit) {
		super(ctx, scale, lit);
	}

	@Override
	public void render(ThrownItemEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		Entity projectileOwner = entity.getOwner();
		int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getBlockPos().up());

		if (projectileOwner != null) {
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(((projectileOwner.getWorld().getTime() + tickDelta) * 20)));
		}

		renderPillow(entity, lightAbove, matrices, vertexConsumers);
	}

	public static void renderPillow(ThrownItemEntity entity, int lightAbove, MatrixStack matrices, VertexConsumerProvider vertexConsumers){
		MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(), ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
	}
}