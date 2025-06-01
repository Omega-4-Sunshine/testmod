package com.omega4.testmod10.util;

import com.omega4.testmod10.Testmod10;
import com.omega4.testmod10.component.ModDataComponentTypes;
import com.omega4.testmod10.item.ModItems;
import com.omega4.testmod10.item.custom.HealBeam2Item;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    public static void registerModelPredicates() { //to change textures -> make custom json file
        ModelPredicateProviderRegistry.register(ModItems.CHISEL, Identifier.of(Testmod10.MOD_ID,"used"), (stack, world, entity, seed) -> stack.get(ModDataComponentTypes.COORDINATES) == null ? 0f : 1f);

        registerCustomBow(ModItems.STEEL_BOW);
    }




    private static void registerCustomBow(Item item) {
        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getActiveItem() != stack ? 0.0F : (float)(stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 20.0F;
            }
        });
        ModelPredicateProviderRegistry.register(
                item,
                Identifier.ofVanilla("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
        );
    }
}
