package com.omega4.testmod10.block;

import com.omega4.testmod10.Testmod10;
import com.omega4.testmod10.block.custom.IndustrialLamp;
import com.omega4.testmod10.block.custom.RefinedFurnaceBlock;
import com.omega4.testmod10.sound.ModSounds;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {

    public static final Block STEEL_BLOCK = registerBlock("steel_block", new Block(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(ModSounds.STEEL_BLOCK_SOUNDS))); //custom sound group
    public static final Block STEEL_ORE_BLOCK = registerBlock("steel_ore_block", new ExperienceDroppingBlock(UniformIntProvider.create(2,5),AbstractBlock.Settings.create().strength(2f).requiresTool().sounds(BlockSoundGroup.ANCIENT_DEBRIS)));

    public static final Block REFINED_FURNACE_BLOCK = registerBlock("refined_furnace_block", new RefinedFurnaceBlock(AbstractBlock.Settings.create().strength(8f).requiresTool().sounds(BlockSoundGroup.NETHERITE).luminance(state -> state.get(RefinedFurnaceBlock.CLICKED) ? 12 : 0)));

    public static final Block STEEL_SLAB = registerBlock("steel_slab", new SlabBlock(AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
    public static final Block STEEL_DOOR = registerBlock("steel_door", new DoorBlock(BlockSetType.IRON, AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.ANCIENT_DEBRIS).nonOpaque())); // for transparent need to get cutout at clientMain

    public static final Block INDUSTRIAL_LAMP = registerBlock("industrial_lamp", new IndustrialLamp(AbstractBlock.Settings.create().strength(1.5f).luminance(state -> state.get(IndustrialLamp.CLICKED) ? 15 : 0))); //input of function is state and outputs int value
    //look at block class to get more values to change here ,nocollision. requiresTool, etc.
    //there are different subclasses of block
    //PreasurePlateBlock -> blockSetType(Iron), StairsBlock -> getDefaultState, FenceBlock, FenceGateBlock -> woodType(does not change anything), WallBlock, DoorBlock -> BlockSetType -> .nonOpaque, TrapdoorBlock -> same as Door (non opaque as have transparent pixtures)
    //for doors you need specific textures, otherwise groups are fine
    //textures for _trapdoor, _door_bottom, _door_top needed + _door bei items, da door andere Texture im Inv hat
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Testmod10.MOD_ID,name), block);
    }

    private  static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Testmod10.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        Testmod10.LOGGER.info("Registering Mod blocks for: " + Testmod10.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> { //adding to existing Group
            entries.add(STEEL_BLOCK);
        });
    }
}
