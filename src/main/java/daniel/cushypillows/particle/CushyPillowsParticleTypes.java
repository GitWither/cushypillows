package daniel.cushypillows.particle;

import daniel.cushypillows.CushyPillows;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class CushyPillowsParticleTypes {
    public static final ParticleType<SimpleParticleType> FEATHERS = registerParticleType("feathers", FabricParticleTypes.simple());

    private static <T extends ParticleEffect> ParticleType<T> registerParticleType(String id, ParticleType<T> effect) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(CushyPillows.MOD_ID, id), effect);
    }

    public static void initialize() {

    }
}
