package com.omega4.testmod10.enchantment;

import com.omega4.testmod10.Testmod10;
import com.omega4.testmod10.enchantment.custom.LightningStrikeEnchantmentEffect;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class ModEnchantments {

    //this is for datagen -> json file for enchantment
    public static final RegistryKey<Enchantment> LIGHTNING_STRIKE = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Testmod10.MOD_ID, "lightning_strike"));

    public static void bootstrap(Registerable<Enchantment> registerable) {
        var enchantments = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);

        register(registerable, LIGHTNING_STRIKE, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE), //what enchantment for
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE), //what mainly for
                5,
        2,
                Enchantment.leveledCost(5,7),
                Enchantment.leveledCost(25,9),
                5,
                AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET)) //in set with other damage -> not smite + scharpness for example
                .addEffect(EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER, EnchantmentEffectTarget.VICTIM, new LightningStrikeEnchantmentEffect()));
    }


    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }

}
