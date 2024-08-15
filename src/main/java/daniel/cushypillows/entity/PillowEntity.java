package daniel.cushypillows.entity;

import daniel.cushypillows.item.CushyPillowsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
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
		World world = this.getWorld();

		// TODO: Replace with proper value and check frequency
		if (world.isClient && this.age % 15 == 0) {
			world.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getRandomBodyY(), this.getZ(), 0, -0.1f, 0);
		}

		super.tick();
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
		// Gets the projectile ItemStack
		ItemStack projectileItem = this.getItem();

		// Drops the item of the ItemStack in the world where the entity hits
		this.dropItem(projectileItem.getItem());

		// Discards after hitting something
		this.discard();

		super.onCollision(hitResult);
	}
}