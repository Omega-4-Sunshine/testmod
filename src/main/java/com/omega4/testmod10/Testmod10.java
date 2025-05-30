package com.omega4.testmod10;

import com.omega4.testmod10.block.ModBlocks;
import com.omega4.testmod10.component.ModDataComponentTypes;
import com.omega4.testmod10.item.ModItemGroups;
import com.omega4.testmod10.item.ModItems;
import com.omega4.testmod10.util.HammerUsageEvent;
import com.omega4.testmod10.util.ModModelPredicates;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Testmod10 implements ModInitializer {

	public static final String MOD_ID = "testmod10";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModDataComponentTypes.registerDataComponentTypes();
		ModModelPredicates.registerModelPredicates();

		FuelRegistry.INSTANCE.add(ModItems.COAL_DUST, 2000); //für fuels muss man ins Register Fuel neuen Eintrag hinzufügen

		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent()); //register Event when player block break
	}
}
