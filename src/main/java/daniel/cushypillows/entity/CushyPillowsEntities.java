package daniel.cushypillows.entity;

import daniel.cushypillows.CushyPillows;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CushyPillowsEntities {
	public static EntityType<PillowEntity> PILLOW_ENTITY = Registry.register(
		Registries.ENTITY_TYPE,
		Identifier.of(CushyPillows.MOD_ID, "pillow"),
		EntityType.Builder.<PillowEntity>create(PillowEntity::new, SpawnGroup.MISC)
			.dimensions(0.25f, 0.25f)
			.maxTrackingRange(25)
			.trackingTickInterval(20)
			.build()
	);

	public static void initialize() {}
}
