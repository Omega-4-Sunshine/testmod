package com.omega4.testmod10.enchantment.custom;

import com.mojang.serialization.MapCodec;
import com.omega4.testmod10.component.ModDataComponentTypes;
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

        context.stack().set(ModDataComponentTypes.OUTPUT, 5*level);

    }

    @Override
    public void remove(EnchantmentEffectContext context, Entity user, Vec3d pos, int level) {
        user.sendMessage(Text.of("TestRemove!"));
        context.stack().set(ModDataComponentTypes.OUTPUT, -1);
        EnchantmentEntityEffect.super.remove(context, user, pos, level);
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
