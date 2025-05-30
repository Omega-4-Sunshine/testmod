package com.omega4.testmod10.block.custom;

import com.omega4.testmod10.item.ModItems;
import com.omega4.testmod10.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MinecartItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RefinedFurnaceBlock extends Block {

    public static final BooleanProperty CLICKED = BooleanProperty.of("clicked");//need to add -> append properties

    public RefinedFurnaceBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(CLICKED, false));
    }



    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if(!world.isClient){

            ItemStack mainHandItemStack =player.getStackInHand(Hand.MAIN_HAND);
            Item mainHandItem = mainHandItemStack.getItem();

            if(mainHandItem == Items.COAL){
                player.sendMessage(Text.of("Coal was used"));
            }

            world.playSound(player, pos, SoundEvents.BLOCK_AZALEA_BREAK, SoundCategory.BLOCKS);
            Boolean blockClickedState = state.get(RefinedFurnaceBlock.CLICKED);
            world.setBlockState(pos, state.cycle(CLICKED));
            player.sendMessage(Text.of(blockClickedState.toString()));
        }


        return ActionResult.SUCCESS;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {

        if(entity instanceof ItemEntity itemEntity) {
            if(isValidItem(itemEntity.getStack())) {
                itemEntity.setStack(new ItemStack(ModItems.CHISEL, itemEntity.getStack().getCount()));
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    private boolean isValidItem(ItemStack stack) {
        return stack.isIn(ModTags.Items.TRANSFORMABLE_ITEMS);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CLICKED);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.testmod10.refined_furnace.tooltip"));
        super.appendTooltip(stack, context, tooltip, options);
    }
}
