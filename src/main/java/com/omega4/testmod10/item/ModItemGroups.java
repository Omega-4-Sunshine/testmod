package com.omega4.testmod10.item;

import com.ibm.icu.impl.Row;
import com.omega4.testmod10.Testmod10;
import com.omega4.testmod10.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class ModItemGroups {

    public static final ItemGroup ADVANCED_CONSTRUCTION_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(Testmod10.MOD_ID, "advanced_construction_items"), FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.STEEL_INGOT))
            .displayName(Text.translatable("itemgroup.tutorialmod.advanced_construction_items"))
            .entries((displayContext, entries) -> {
                entries.add(ModItems.STEEL_INGOT);
                entries.add(ModBlocks.REFINED_FURNACE_BLOCK);
                entries.add(ModBlocks.STEEL_BLOCK);
                entries.add(ModBlocks.STEEL_ORE_BLOCK);
                entries.add(ModItems.CHISEL);
                entries.add(ModItems.ROASTED_SEEDS);
                entries.add(ModItems.COAL_DUST);
                entries.add(ModBlocks.STEEL_SLAB);
                entries.add(ModBlocks.STEEL_DOOR);
                entries.add(ModBlocks.INDUSTRIAL_LAMP);
                entries.add(ModItems.HEAL_BEAM);
                entries.add(ModItems.HEAL_BEAM_2);
                entries.add(ModItems.STEEL_SHOVEL);
                entries.add(ModItems.HAMMER);
                entries.add(ModItems.STEEL_HELMET);
                entries.add(ModItems.STEEL_BOOTS);
                entries.add(ModItems.STEEL_BOW);
            })
            .build());


    public static void registerItemGroups() {
        Testmod10.LOGGER.info("Registering Item Groups for: "+ Testmod10.MOD_ID);

    }
}

