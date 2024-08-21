package daniel.cushypillows.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.SimpleParticleType;

public class FeatherParticle extends SpriteBillboardParticle {
    private static final ItemStack FEATHER_STACK = Items.FEATHER.getDefaultStack();

    protected FeatherParticle(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        BakedModel featherModel = itemRenderer.getModel(FEATHER_STACK, world, null, 0);
        this.setSprite(featherModel.getParticleSprite());

        this.setMaxAge(this.random.nextBetween(20, 40));

        setVelocity(
                this.random.nextFloat() * 0.1f - 0.05f,
                this.random.nextFloat() * 0.1f + 0.1f,
                this.random.nextFloat() * 0.1f - 0.05f
        );

        this.gravityStrength = 0.02f;
        this.angle = this.random.nextBetween(-180, 180);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.prevAngle = this.angle;

        if (this.maxAge-- <= 0) {
            this.markDead();
        }

        if (this.dead) {
            return;
        }

        this.velocityY -= this.gravityStrength;

        this.move(this.velocityX, this.velocityY, this.velocityZ);

        if (!this.onGround) {
            this.angle += 0.25f;
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.TERRAIN_SHEET;
    }

    @Environment(value= EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new FeatherParticle(clientWorld, d, e, f);
        }
    }
}
