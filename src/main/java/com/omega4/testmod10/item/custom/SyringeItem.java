package com.omega4.testmod10.item.custom;

import com.omega4.testmod10.Testmod10;
import com.omega4.testmod10.item.ModItems;
import com.omega4.testmod10.sound.ModSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.UUID;

public class SyringeItem extends Item {


    public SyringeItem(Settings settings) {
        super(settings);
    }

    public void healPlayer(PlayerEntity player){
        player.setHealth(player.getMaxHealth());
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION,250,2,false,false,false));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,90,2,false,false,false));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,60,2,false,false,false));
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) {return TypedActionResult.fail(user.getStackInHand(hand));};//set active
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand)); //needed for consume action and usageTick, stoppedUsing, etc.

    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 50;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {

        if(remainingUseTicks > 49) {
            world.playSound(user, user.getBlockPos(), ModSounds.SYRINGE_USE, SoundCategory.BLOCKS,1f,1f);
        }


        user.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(Identifier.of("syringe_speed"));
        user.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addPersistentModifier(
                new EntityAttributeModifier(// Eindeutige ID -> wichtig zum Entfernen
                        Identifier.of("syringe_speed"),          // Nur ein Name, frei wählbar
                        -0.05,                        // 50% langsamer
                        EntityAttributeModifier.Operation.ADD_VALUE
                )
        );
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        stack.decrement(1);
        MinecraftClient client = MinecraftClient.getInstance();
        client.options.getGamma().setValue(2000.0);
        user.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(Identifier.of("syringe_speed"));
        healPlayer(((PlayerEntity) user));
        return super.finishUsing(stack, world, user);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) { //for animation !!
        return UseAction.BRUSH;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

        user.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(Identifier.of("syringe_speed"));
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }


}
