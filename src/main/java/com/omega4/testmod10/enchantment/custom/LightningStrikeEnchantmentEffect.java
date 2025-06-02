package com.omega4.testmod10.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public record LightningStrikeEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<LightningStrikeEnchantmentEffect> CODEC = MapCodec.unit(LightningStrikeEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) { //gets called when effect is applied, da es sich um postAttack handelt ist user das "victim" -> muss user aus context nehmen


        // Blitz spawnen
        for (int i = 0; i < level; i++) {
            EntityType.LIGHTNING_BOLT.spawn(world, user.getBlockPos(), SpawnReason.TRIGGERED);
            //context.owner().setHealth(0.5f);
        }


        }
    


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
