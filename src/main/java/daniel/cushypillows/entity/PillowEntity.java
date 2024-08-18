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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
		BlockPos pos = this.getBlockPos();

		if (this.age % 6 == 0) {
			PillowBlock.spawnFeatherParticles(world, pos, 1);
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
		// getItem in L1 -> gets ItemStack of projectile
		// getItem in L2 -> gets method of item of ItemStack
		ItemStack projectileItem = this.getItem();

		PillowBlock.spawnFeatherParticles(this.getWorld(), this.getBlockPos(), 3);
		this.dropItem(projectileItem.getItem());
		this.discard();

		super.onCollision(hitResult);
	}
}