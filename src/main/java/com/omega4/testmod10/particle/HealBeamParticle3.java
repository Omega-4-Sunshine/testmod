package com.omega4.testmod10.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class HealBeamParticle3 extends SpriteBillboardParticle {
    public HealBeamParticle3(ClientWorld clientWorld, double x, double y, double z, SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
        super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed);

        this.setSpriteForAge(spriteProvider);
        this.maxAge = 1;
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
        this.scale = 0.07f;
        this.alpha = 1f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> { //needs to be registerd in client class
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new HealBeamParticle3(world, x,y,z,this.spriteProvider,velocityX,velocityY,velocityZ);
        }
    }
}
