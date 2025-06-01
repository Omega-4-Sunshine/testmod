package com.omega4.testmod10.particle;

import com.omega4.testmod10.Testmod10;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {

    public static final SimpleParticleType HEAL_BEAM_PARTICLE = registerParticle("heal_beam_particle", FabricParticleTypes.simple());

    private static SimpleParticleType registerParticle(String name, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Testmod10.MOD_ID, name), particleType);
    }

    public static void registerParticles() {
        Testmod10.LOGGER.info("Registering Particels for: " + Testmod10.MOD_ID);



    }
}
