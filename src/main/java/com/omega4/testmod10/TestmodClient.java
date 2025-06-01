package com.omega4.testmod10;

import com.omega4.testmod10.block.ModBlocks;
import com.omega4.testmod10.particle.HealBeamParticle1;
import com.omega4.testmod10.particle.ModParticles;
import com.omega4.testmod10.util.ModModelPredicates;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.render.RenderLayer;

public class TestmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STEEL_DOOR, RenderLayer.getCutout());

        ModModelPredicates.registerModelPredicates();//needed to show transparency

        ParticleFactoryRegistry.getInstance().register(ModParticles.HEAL_BEAM_PARTICLE_1, HealBeamParticle1.Factory :: new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.HEAL_BEAM_PARTICLE_2, HealBeamParticle1.Factory :: new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.HEAL_BEAM_PARTICLE_3, HealBeamParticle1.Factory :: new);

    }
}
