package com.omega4.testmod10.datagen;

import com.omega4.testmod10.block.ModBlocks;
import com.omega4.testmod10.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {

        List<ItemConvertible> ROASTED_SEEDS_SMELATBLES = List.of(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS);

        offerSmelting(recipeExporter, ROASTED_SEEDS_SMELATBLES, RecipeCategory.FOOD, ModItems.ROASTED_SEEDS,2f, 20, "roated_seeds");

        offerReversibleCompactingRecipes(recipeExporter, RecipeCategory.MISC,ModItems.STEEL_INGOT,RecipeCategory.MISC, ModBlocks.STEEL_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC,ModItems.CHISEL)
                .pattern("WSS")
                .pattern("   ")
                .pattern("   ")
                .input('W', Items.STICK)
                .input('S', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT)) //necessary
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SYRINGE_ITEM)
                .pattern("S  ")
                .pattern("EGA")
                .pattern("  S")
                .input('S',ModItems.STEEL_INGOT).input('G', Items.POTION).input('E',Items.AMETHYST_SHARD).input('A', Items.GOLDEN_APPLE)
                .criterion(hasItem(Items.GOLDEN_APPLE), conditionsFromItem(Items.GOLDEN_APPLE))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.STEEL_SHOVEL)
                .pattern("S  ")
                .pattern("S  ")
                .pattern("W  ")
                .input('W', Items.STICK)
                .input('S', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HEAL_BEAM_2)
                .pattern(" SG")
                .pattern("SNS")
                .pattern("WS ")
                        .input('N', Items.NAUTILUS_SHELL)
                                .input('S',ModItems.STEEL_INGOT)
                                        .input('G', Items.GOAT_HORN).input('W', Items.STICK).criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT)) // NEEDED!!!
                .offerTo(recipeExporter);

        createDoorRecipe(ModBlocks.STEEL_DOOR, Ingredient.ofItems(ModItems.STEEL_INGOT))
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
                .offerTo(recipeExporter); //offer braucht nicht .criterion und .offer to -> create schon
    }
    //maybe you eed to add Identifier in .offerTo when you have two recipes resulting in same output
}
