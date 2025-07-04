package com.omega4.testmod10.item.custom;

import com.omega4.testmod10.component.ModDataComponentTypes;
import com.omega4.testmod10.item.ModItems;
import com.omega4.testmod10.particle.ModParticles;
import com.omega4.testmod10.sound.ModSounds;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

// work with charge -> component
// different states: inactive = 0, active = 1, healing = 2
//variables : maxCharge, maxUsableCharge, chrageIncrease, chargeDecrease

public class HealBeam2Item extends Item {
    public HealBeam2Item(Settings settings) {
        super(settings);
    }

    private int chargeMax = 160;
    private static int chargeMaxUsable = 120;
    private int chargeIncrease = 1;
    private int chargeDecrease = 2;
    private int state = 0;
    private int beamDistance = 1000;
    double beamMaxAngle = -2, beamSteps = 0.02;
    private LivingEntity entitySelected;

    //------------------------------------------------------------------------------------------------------------------
    public static int getChargeMaxUsable() {
        return chargeMaxUsable;
    }
    public EntityHitResult raycastLivingEntities(PlayerEntity player, double maxDistance) {
        Vec3d playerPos = player.getCameraPosVec(1.0f);
        Vec3d playerLook = player.getRotationVec(1.0f);
        Vec3d playerCastEnd = playerPos.add(playerLook.multiply(maxDistance));
        return ((EntityHitResult) ProjectileUtil.raycast(player, playerPos, playerCastEnd, player.getBoundingBox().stretch(playerLook.multiply(maxDistance)).expand(1.0),
                entity -> !entity.isSpectator() && entity.isAlive(), maxDistance));
    }
    public static float map(float value, float sourceMin, float sourceMax, float targetMin, float targetMax) {
        return targetMin + ((value - sourceMin) / (sourceMax - sourceMin)) * (targetMax - targetMin);
    }
    public boolean checkValidHealAngle (Entity entitySelected, PlayerEntity player){
        Vec3d playerLookDirection = player.getRotationVec(1.0f);
        Vec3d selectedDirection = (entitySelected.getPos()).subtract(player.getPos());
        double norm1 = playerLookDirection.length();
        double norm2 = selectedDirection.length();
        double dotProduct = selectedDirection.dotProduct(playerLookDirection);
        double angle = dotProduct/(norm1*norm2);
        return !(angle < beamMaxAngle);
    }
    public void overheat(ItemStack stack, World world, PlayerEntity player){
        stack.set(ModDataComponentTypes.CHARGE, Math.clamp(stack.get(ModDataComponentTypes.CHARGE) + 50, 0, chargeMax));
        world.playSound(null, player.getBlockPos(), ModSounds.Heal_beam_STOP, SoundCategory.BLOCKS, 1f, 0.7f);
        world.playSound(null, player.getBlockPos(), ModSounds.HEAL_BEAM_OVERHEAT, SoundCategory.BLOCKS, 1, 1);

    }
    public void succedBeamAction(boolean killed, World world, PlayerEntity player) {
        world.playSound(null, player.getBlockPos(), ModSounds.HEAL_BEAM_SHOT, SoundCategory.BLOCKS, 2, 1f);
        world.playSound(null, player.getBlockPos(), ModSounds.Heal_beam_STOP, SoundCategory.BLOCKS, 1, 1.2f);
    }
    public void drawBeam(Entity entitySelected, PlayerEntity player, ServerWorld world) {
        double x = 0;
        Vec3d playerCenterVec = new Vec3d(player.getX(), (player.getY() + player.getHeight()/2) + 0.17,player.getZ());
        Vec3d entityCenterVec = new Vec3d(entitySelected.getX(), entitySelected.getY() + entitySelected.getHeight()/2, entitySelected.getZ());
        Vec3d currentLookVec, currentPlayerEntityVec;
        Vec3d playerEntityVec;
        Vec3d currentSpawnVec;
        Vec3d look = player.getRotationVec(1f);
        Vec3d rightLook = new Vec3d(-look.z, 0, look.x).normalize();

        Vec3d healbeamTip = playerCenterVec.add(player.getRotationVec(1f).multiply(1.1)).add(0,0,0).add(rightLook.multiply(0.38));

;
        playerEntityVec = entityCenterVec.subtract(healbeamTip);
        Vec3d playerLookVec = player.getRotationVec(1).multiply(playerEntityVec.length());



        while (x <= 1) {

            currentPlayerEntityVec = healbeamTip.add(playerEntityVec.multiply(x));
            currentLookVec = healbeamTip.add(playerLookVec.multiply(x));
            currentSpawnVec = currentPlayerEntityVec.add((currentLookVec.subtract(currentPlayerEntityVec)).multiply(1 - x));
            double random = Math.random();

            //gradient density (lame way)
            if(x <= 0.1 || x >= 0.9) {
                if(Math.random() < 0.8) {
                    x = x + beamSteps;
                    continue;
                }
            } else if(x <= 0.2 || x >= 0.8) {
                if(Math.random() < 0.6) {
                    x = x + beamSteps;
                    continue;
                }
            } else if (x <= 0.3 || x >= 0.7) {
                if(Math.random() < 0.4) {
                    x = x + beamSteps;
                    continue;
                }
            }

            if(random > 0.75) {
                world.spawnParticles(ModParticles.HEAL_BEAM_PARTICLE_1, currentSpawnVec.getX(), currentSpawnVec.getY(), currentSpawnVec.getZ(), 1, 0,0,0,0);

                world.spawnParticles(ModParticles.HEAL_BEAM_PARTICLE_1, entityCenterVec.getX(), entityCenterVec.getY(), entityCenterVec.getZ(), 1, 0.3,0.3,0.3,0);

            } else if (random > 0.45){
                world.spawnParticles(ModParticles.HEAL_BEAM_PARTICLE_2, currentSpawnVec.getX(), currentSpawnVec.getY(), currentSpawnVec.getZ(), 1, 0,0,0,0);

            } else if (random > 0.25){
                world.spawnParticles(ModParticles.HEAL_BEAM_PARTICLE_3, currentSpawnVec.getX(), currentSpawnVec.getY(), currentSpawnVec.getZ(), 1, 0,0,0,0);
            } else {
                world.spawnParticles(ParticleTypes.BUBBLE_POP, currentSpawnVec.getX(), currentSpawnVec.getY(), currentSpawnVec.getZ(), 1,0.1,0.1,0.1,0);

                world.spawnParticles(ParticleTypes.BUBBLE_POP, entityCenterVec.getX(), entityCenterVec.getY(), entityCenterVec.getZ(), 1, 0.3,0.3,0.3,0);

            }

            x = x + beamSteps;
        }

        world.spawnParticles(ParticleTypes.GLOW, entityCenterVec.getX() + (Math.random()-0.5), entityCenterVec.getY() + (Math.random()-0.5), entityCenterVec.getZ()+ (Math.random() -0.5), 1,0,0,0,0);
        world.spawnParticles(ParticleTypes.BUBBLE, entityCenterVec.getX(), entityCenterVec.getY(), entityCenterVec.getZ(), 1, 0.3,0.3,0.3,0);
        world.spawnParticles(ParticleTypes.DOLPHIN, entityCenterVec.getX(), entityCenterVec.getY(), entityCenterVec.getZ(), 1, 0.3,0.3,0.3,0);
    };
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient) {return TypedActionResult.fail(user.getStackInHand(hand));};
        state = 1; //set active
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand)); //needed for consume action and usageTick, stoppedUsing, etc.

    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {

        if(((PlayerEntity) user).getInventory().getStack(40).getItem() == ModItems.HEAL_BEAM_2 && ((PlayerEntity) user).getStackInHand(Hand.MAIN_HAND).getItem() != ModItems.HEAL_BEAM_2) {this.onStoppedUsing(stack,world,user,0);}
        if(world.isClient) {return;}
        if((state < 2) && (stack.get(ModDataComponentTypes.CHARGE) <= chargeMaxUsable)) {
            EntityHitResult entityHitResult = raycastLivingEntities(((PlayerEntity) user), beamDistance);
            if(entityHitResult == null || !(entityHitResult.getEntity() instanceof LivingEntity)) {return;}
            state = 2;
            entitySelected = ((LivingEntity) entityHitResult.getEntity());
            return;
        }
        int currentCharge = stack.get(ModDataComponentTypes.CHARGE);
        if(currentCharge == chargeMaxUsable) {overheat(stack, world, ((PlayerEntity) user));}
        if((entitySelected == null) || entitySelected.getHealth() <= 0 || !checkValidHealAngle(entitySelected, ((PlayerEntity) user)) || (stack.getItem() != ModItems.HEAL_BEAM_2) || (currentCharge >= chargeMaxUsable)) {this.onStoppedUsing(stack, world, user, 0); return;}
        stack.set(ModDataComponentTypes.CHARGE, Math.clamp(currentCharge + 1,0,chargeMaxUsable));
        //world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.BLOCKS, 1.0f, map(currentCharge, 0, chargeMax,0.5f,2f));
        world.playSound(null, user.getBlockPos(), ModSounds.HEAL_BEAM_CHARGE, SoundCategory.BLOCKS, 1.8f, map(currentCharge, 0, chargeMax,0.5f,2.5f));
        //entitySelected.setHealth(entitySelected.getHealth() - 1.0f); //main functionality
        DamageSources sources = world.getDamageSources();
        entitySelected.damage(sources.freeze(), 1f);
        drawBeam(entitySelected, ((PlayerEntity) user), ((ServerWorld) world));

        //draw line, play sound
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if(world.isClient) {return;}

        if(selected && (state < 2)){
            stack.set(ModDataComponentTypes.CHARGE, Math.clamp(stack.get(ModDataComponentTypes.CHARGE) - 2, 0, chargeMax));
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }


    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {


        
        state = 0;
        if(entitySelected == null) {return;}
        if(stack.get(ModDataComponentTypes.CHARGE) < chargeMaxUsable) {
            succedBeamAction(true, world, ((PlayerEntity) user) );
        }
        entitySelected = null;
        //world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1, 1);
        //play break sound
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }


    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 10000;
    }

    @Override // to stop anim
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE; //works changes general anim, but does not stop anim when changing stack
    }
}
