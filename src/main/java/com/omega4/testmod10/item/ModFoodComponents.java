package com.omega4.testmod10.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponents {
    public static final FoodComponent ROASTED_SEEDS = new FoodComponent.Builder().nutrition(2).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 100), 0.3f).build(); //registry
}
