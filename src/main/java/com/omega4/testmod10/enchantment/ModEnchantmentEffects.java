package com.omega4.testmod10.enchantment;

import com.mojang.serialization.MapCodec;
import com.omega4.testmod10.Testmod10;
import com.omega4.testmod10.enchantment.custom.LightningStrikeEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {

    public static final MapCodec<? extends EnchantmentEntityEffect> LIGHTNING_STRIKE = registerEntityEffect("lightning_strike", LightningStrikeEnchantmentEffect.CODEC);

    private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name, MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(name), codec);
    }

    public static void registerEnchantmentEffects(){
        Testmod10.LOGGER.info("Registering Mod Enchantments for: " + Testmod10.MOD_ID);
    }

}
