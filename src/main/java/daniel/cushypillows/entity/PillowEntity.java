package daniel.cushypillows.entity;

import daniel.cushypillows.block.PillowBlock;
import daniel.cushypillows.item.CushyPillowsItems;
import daniel.cushypillows.particle.CushyPillowsParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PillowEntity extends ThrownItemEntity {
	public PillowEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	public PillowEntity(LivingEntity owner, World world) {
		super(CushyPillowsEntities.PILLOW_ENTITY, owner, world);
	}

	@Override
	protected Item getDefaultItem() {
		return CushyPillowsItems.WHITE_PILLOW;
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.getWorld().isClient() && this.age % 4 == 0) {
			ServerWorld serverWorld = (ServerWorld) this.getWorld();
			serverWorld.spawnParticles(
					(ParticleEffect) CushyPillowsParticleTypes.FEATHERS,
					getPos().getX(), getPos().getY(), getPos().getZ(),
					1, 0.0, 0, 0, 0);
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity hitEntity = entityHitResult.getEntity();

		if (hitEntity instanceof LivingEntity livingHitEntity) {
			livingHitEntity.damage(this.getDamageSources().thrown(this, this.getOwner()), 0);
		}

		super.onEntityHit(entityHitResult);
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		ItemStack projectileItem = this.getItem();

		PillowBlock.spawnFeatherParticles(this.getWorld(), this.getBlockPos(), 3);
		this.dropItem(projectileItem.getItem());
		this.discard();

		super.onCollision(hitResult);
	}
}