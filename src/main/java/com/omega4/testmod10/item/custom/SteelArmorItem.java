package com.omega4.testmod10.item.custom;

import com.omega4.testmod10.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.List;

public class SteelArmorItem extends ArmorItem {
    public SteelArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    //List<RegistryEntry<StatusEffect>> statusEffects = List.of(StatusEffects.H);
    List<StatusEffectInstance> statusEffects = List.of(new StatusEffectInstance(StatusEffects.HEALTH_BOOST,  300,1,false,true),new StatusEffectInstance(StatusEffects.ABSORPTION,  300,1,false,true));


    public boolean correctSteelArmorOn(PlayerEntity player) {
        int x = 0;
        for(ItemStack armorItem : player.getArmorItems()) {
            if(armorItem.getItem() == ModItems.STEEL_BOOTS || armorItem.getItem() == ModItems.STEEL_HELMET) {
                x++;
            }

        }
        if(x == 2) {return true;}
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if(!(entity instanceof ServerPlayerEntity serverPlayer)) {return;}
        if(serverPlayer.getStatusEffect(StatusEffects.ABSORPTION) != null) {return;}
        if(!(correctSteelArmorOn(serverPlayer))) {return;}
        for(StatusEffectInstance statusEffectInstance : statusEffects) {
            serverPlayer.addStatusEffect(new StatusEffectInstance(statusEffectInstance)); //otherwise instance is consumed??
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
