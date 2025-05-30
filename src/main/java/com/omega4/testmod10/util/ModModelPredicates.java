package com.omega4.testmod10.util;

import com.omega4.testmod10.Testmod10;
import com.omega4.testmod10.component.ModDataComponentTypes;
import com.omega4.testmod10.item.ModItems;
import com.omega4.testmod10.item.custom.HealBeam2Item;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    public static void registerModelPredicates() { //to change textures -> make custom json file
        ModelPredicateProviderRegistry.register(ModItems.CHISEL, Identifier.of(Testmod10.MOD_ID,"used"), (stack, world, entity, seed) -> stack.get(ModDataComponentTypes.COORDINATES) == null ? 0f : 1f);
        ModelPredicateProviderRegistry.register(ModItems.HEAL_BEAM_2, Identifier.of(Testmod10.MOD_ID, "overcharged"), (stack, world, entity, seed) -> {
            if(stack.get(ModDataComponentTypes.CHARGE) >= HealBeam2Item.getChargeMaxUsable()) {return 1f;} return 0f;
        });
    }
}
