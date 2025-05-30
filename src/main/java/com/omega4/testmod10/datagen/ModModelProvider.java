package com.omega4.testmod10.datagen;

import com.omega4.testmod10.block.ModBlocks;
import com.omega4.testmod10.block.custom.IndustrialLamp;
import com.omega4.testmod10.block.custom.RefinedFurnaceBlock;
import com.omega4.testmod10.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        BlockStateModelGenerator.BlockTexturePool steelPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.STEEL_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STEEL_ORE_BLOCK);
        //blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.REFINED_FURNACE_BLOCK);

        steelPool.slab(ModBlocks.STEEL_SLAB);
        blockStateModelGenerator.registerDoor(ModBlocks.STEEL_DOOR);

        Identifier lampOffIdentifier = TexturedModel.CUBE_ALL.upload(ModBlocks.INDUSTRIAL_LAMP, blockStateModelGenerator.modelCollector); //just compley way to set up json file, that hast two references based on block state
        Identifier lampOnIdentifier = blockStateModelGenerator.createSubModel(ModBlocks.INDUSTRIAL_LAMP, "_on", Models.CUBE_ALL, TextureMap::all);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.INDUSTRIAL_LAMP)
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(IndustrialLamp.CLICKED, lampOnIdentifier, lampOffIdentifier)));

        Identifier RefinedFurnaceOffIdentifier = TexturedModel.CUBE_ALL.upload(ModBlocks.REFINED_FURNACE_BLOCK, blockStateModelGenerator.modelCollector); //always do data gen
        Identifier RefinedFurnaceOnIdentifier = blockStateModelGenerator.createSubModel(ModBlocks.REFINED_FURNACE_BLOCK, "_active", Models.CUBE_ALL, TextureMap::all);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.REFINED_FURNACE_BLOCK)
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(RefinedFurnaceBlock.CLICKED, RefinedFurnaceOnIdentifier, RefinedFurnaceOffIdentifier)));


    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.STEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_PLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHISEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.COAL_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.ROASTED_SEEDS, Models.GENERATED);
        itemModelGenerator.register(ModItems.HEAL_BEAM, Models.GENERATED);
        itemModelGenerator.register(ModItems.HEAL_BEAM_2, Models.GENERATED);

        itemModelGenerator.register(ModItems.STEEL_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.HAMMER, Models.HANDHELD);//for 3d -> tools, etc.
        
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.STEEL_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.STEEL_BOOTS));
    }
}
