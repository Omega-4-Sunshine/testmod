package com.omega4.testmod10.datagen;

import com.omega4.testmod10.block.ModBlocks;
import com.omega4.testmod10.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.STEEL_BLOCK)
                .add(ModBlocks.REFINED_FURNACE_BLOCK)
                .add(ModBlocks.STEEL_ORE_BLOCK)
                .add(ModBlocks.STEEL_SLAB)
                .add(ModBlocks.STEEL_DOOR);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL) //always block tags for all tags
                .add(ModBlocks.REFINED_FURNACE_BLOCK);
        getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.STEEL_BLOCK);
        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_STEEL_TOOL)
                .add(ModBlocks.STEEL_BLOCK)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);


    }
}
