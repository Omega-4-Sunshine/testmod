package com.omega4.testmod10.datagen;

import com.omega4.testmod10.item.ModItems;
import com.omega4.testmod10.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {


    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Items.TRANSFORMABLE_ITEMS) //custom tag that is used in refined furnace
                .add(ModItems.CHISEL)
                .add(Items.COAL);
        getOrCreateTagBuilder(ItemTags.WEAPON_ENCHANTABLE)
                .add(ModItems.CHISEL);
        //getOrCreateTagBuilder(ModTags.Items.INVERSIBLE).add(ModItems.HEAL_BEAM_2);

        //tag swords, pickaxe, etc -> for correct enchantment

        getOrCreateTagBuilder(ItemTags.SHOVELS).add(ModItems.STEEL_SHOVEL); //for enchantment
        getOrCreateTagBuilder(ItemTags.PICKAXES).add(ModItems.HAMMER);

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR).add(ModItems.STEEL_HELMET);
        getOrCreateTagBuilder(ItemTags.ARMOR_ENCHANTABLE)
                .add(ModItems.STEEL_HELMET)
                .add(ModItems.STEEL_BOOTS);
        getOrCreateTagBuilder(ItemTags.BOW_ENCHANTABLE).add(ModItems.STEEL_BOW);
        getOrCreateTagBuilder(ItemTags.EQUIPPABLE_ENCHANTABLE).add(ModItems.HEAL_BEAM_2); //idealy should include inversion but does not
    }
}

//pass fail, success