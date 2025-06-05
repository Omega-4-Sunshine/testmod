package com.omega4.testmod10.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public record InversionEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<InversionEnchantmentEffect> CODEC = MapCodec.unit(InversionEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        EntityType.LIGHTNING_BOLT.spawn(world, user.getBlockPos(), SpawnReason.TRIGGERED);
        if(user instanceof PlayerEntity){
            user.sendMessage(Text.of("TestUSER = Player"));
        }

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
