package com.omega4.testmod10.item;

import com.omega4.testmod10.Testmod10;
import com.omega4.testmod10.component.ModDataComponentTypes;
import com.omega4.testmod10.item.custom.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.mixin.item.ItemAccessor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItems {

    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new Item.Settings()));
    public static final Item COPPER_PLATE = registerItem("copper_plate", new Item(new Item.Settings()));

    public static final Item ROASTED_SEEDS = registerItem("roasted_seeds", new Item(new Item.Settings().food(ModFoodComponents.ROASTED_SEEDS)));

    public static final Item COAL_DUST = registerItem("coal_dust", new Item(new Item.Settings()));//all lowercase, no spaces

    public static Item CHISEL = registerItem("chisel", new ChiselItem(new Item.Settings().maxDamage(32).recipeRemainder(ModItems.STEEL_INGOT)) {// hier Klammer, da man neues ChiselItem umdeklarieren will
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type)
        {
            if(Screen.hasShiftDown()){
                tooltip.add(Text.translatable("tooltip.testmod10.steel_ingot.shiftDown"));
            } else {
                tooltip.add(Text.translatable("tooltip.testmod10.steel_ingot"));
            }
            super.appendTooltip(stack, context, tooltip, type);
        }
    });
    //all items are of super class
    public static final Item HEAL_BEAM = registerItem("heal_beam", new HealBeamItem(new Item.Settings().maxCount(1).component(ModDataComponentTypes.CHARGE, 0)));
    public static final Item HEAL_BEAM_2 = registerItem("heal_beam_2", new HealBeam2Item(new  Item.Settings().maxCount(1).component(ModDataComponentTypes.CHARGE, 0)));//default Component value //max count for non stackable

    public static final Item STEEL_SHOVEL = registerItem("steel_shovel", new ShovelItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.STEEL, - 3, 2f))));
    public static final Item HAMMER = registerItem("hammer", new HammerItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.STEEL, 7,-3.5f))));//ModToolMaterials is advanced enum

    public static final Item STEEL_HELMET = registerItem("steel_helmet", new SteelArmorItem(ModArmorMaterials.STEEL_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
    public static final Item STEEL_BOOTS = registerItem("steel_boots", new ArmorItem(ModArmorMaterials.STEEL_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));

    public static final Item STEEL_BOW = registerItem("steel_bow", new BowItem(new Item.Settings().maxDamage(2000)));



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Testmod10.MOD_ID, name), item);
    }
    public static void registerModItems() {
        Testmod10.LOGGER.info("Registering Mod Items for: "+ Testmod10.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> { //add to create tab item group
            entries.add(STEEL_INGOT);
        });
    }

}
