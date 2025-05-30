package com.omega4.testmod10.item.custom;

import com.omega4.testmod10.component.ModDataComponentTypes;
import com.omega4.testmod10.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {


    private static final Map<Block, Block> CHISEL_MAP =
           Map.of(
                    Blocks.DIRT, Blocks.MUD_BRICKS,
                    Blocks.GRASS_BLOCK, Blocks.MOSS_BLOCK
            );

    public ChiselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
       World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if(CHISEL_MAP.containsKey(clickedBlock)) { //block itself cant give position or set itself to something else
            if(!world.isClient()) { //no permissions

                world.setBlockState(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());//Get value of key, then get default block state

                context.getStack().damage(1,((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()), item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), ModSounds.CHISEL_USE, SoundCategory.BLOCKS);

                context.getStack().set(ModDataComponentTypes.COORDINATES, context.getBlockPos()); //hier custom DataComponentType -> kann auch schon gefertigte nehmen

            }
       }
        return ActionResult.SUCCESS; // send back, that right click was successfull
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {

        if(stack.get(ModDataComponentTypes.COORDINATES) != null) {
            tooltip.add(Text.literal("Coordinate: " + stack.get(ModDataComponentTypes.COORDINATES))); //get and set of stack to change component
        }

        super.appendTooltip(stack, context, tooltip, type);
    }
}
