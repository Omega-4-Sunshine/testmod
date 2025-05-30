package com.omega4.testmod10.item.custom;

import com.omega4.testmod10.component.ModDataComponentTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class HealBeamItem extends Item {

    public HealBeamItem(Settings settings) {
        super(settings);
    }

    private int maxCharge = 150;
    private int maxUsableCharge = 100;
    private static boolean currentlyUsed = false;
    private double tmpx, tmpy, tmpz;

    private Vec3d startPos = new Vec3d(0,0,0);
    private Vec3d endPos = new Vec3d(0,0,0);
    private Vec3d currentStraight = new Vec3d(0,0,0);
    private Vec3d currentLook = new Vec3d(0,0,0);
    private Vec3d currentInterpolated = new Vec3d(0,0,0);
    double x = 0;
    boolean currentlyHealing;


    MinecraftClient client = MinecraftClient.getInstance();

    //helper Method for Entity capable raycast
    public HitResult raycastEntities(PlayerEntity player, double maxDistance) {
        Vec3d playerPos = player.getCameraPosVec(1.0f);
        Vec3d playerLook = player.getRotationVec(1.0f);
        Vec3d playerCastEnd = playerPos.add(playerLook.multiply(maxDistance));
        return ProjectileUtil.raycast(player, playerPos, playerCastEnd, player.getBoundingBox().stretch(playerLook.multiply(maxDistance)).expand(1.0),
                entity -> !entity.isSpectator() && entity.isAlive(), maxDistance);
    }
    public static float map(float value, float sourceMin, float sourceMax, float targetMin, float targetMax) {
        return targetMin + ((value - sourceMin) / (sourceMax - sourceMin)) * (targetMax - targetMin);
    }

    public void spawnParticleTrail(ServerWorld serverWorld, Vec3d startPos, Vec3d endPos, Vec3d lookDirection) {
        startPos = new Vec3d(startPos.x, startPos.y + 1.2 ,startPos.z);
        while (x < 1) {
            currentStraight = ((endPos.subtract(startPos)).multiply(x)).add(startPos);
            currentLook = startPos.add(lookDirection.multiply(x*((endPos.subtract(startPos)).length())));
            currentInterpolated = currentLook.add(
                    (currentStraight.subtract(currentInterpolated)).multiply(x)
            );
            tmpx = currentInterpolated.getX();
            tmpy = currentInterpolated.getY();
            tmpz = currentInterpolated.getZ();
            serverWorld.spawnParticles(ParticleTypes.BUBBLE_POP, tmpx, tmpy, tmpz, 1,0,0,0,0);
            x = x+0.005f;
        }
        x = 0;
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        currentlyUsed = true;
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        user.sendMessage(Text.of("test 5"));
        currentlyHealing = false;

        if (!world.isClient) {
            currentlyHealing = false;
            startPos = user.getPos();
            endPos = new Vec3d(50,100,50);
            PlayerEntity playerEntity = ((PlayerEntity) user);
            HitResult hitResult = raycastEntities(playerEntity, 1000);

            if (world instanceof ServerWorld serverWorld) {
                spawnParticleTrail(serverWorld, startPos, endPos, user.getRotationVec(1.0F));
            }

            if(hitResult == null) {
                return;
            }



            switch (hitResult.getType()) {
                case HitResult.Type.MISS: break;
                case HitResult.Type.ENTITY: {
                    endPos = hitResult.getPos();
                    EntityHitResult entityHit = (EntityHitResult) hitResult;
                    Entity entity = entityHit.getEntity();
                    if(entity instanceof LivingEntity livingEntity && (stack.get(ModDataComponentTypes.CHARGE) < maxUsableCharge)) {
                        livingEntity.setHealth(livingEntity.getHealth() - 0.5f);
                        world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.BLOCKS, 0.8f, (map(stack.get(ModDataComponentTypes.CHARGE), 0, maxUsableCharge,0.5f,2f))); //pitch varies between:
                        stack.set(ModDataComponentTypes.CHARGE, Math.clamp(stack.get(ModDataComponentTypes.CHARGE) + 1, 0,maxCharge));
                        currentlyHealing = true;

                    }
                    if(stack.get(ModDataComponentTypes.CHARGE) >= maxUsableCharge) {
                        world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 0.5f, 1);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClient) {
            currentlyUsed = false;
            stack.set(ModDataComponentTypes.CHARGE, Math.clamp(stack.get(ModDataComponentTypes.CHARGE) + 20, 0,maxCharge));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
         //   if(stack.get(ModDataComponentTypes.INITIALIZED) == null) {
        //        stack.set(ModDataComponentTypes.INITIALIZED, true);
         //       stack.set(ModDataComponentTypes.CHARGE, 0);
         //       ((PlayerEntity) entity).sendMessage(Text.of("Initialized: " + stack.getName()));
          //  }
            if (!currentlyUsed && selected) {
                stack.set(ModDataComponentTypes.CHARGE, Math.clamp(stack.get(ModDataComponentTypes.CHARGE) - 2, 0,maxCharge));

            }
            if (selected){
                ((PlayerEntity) entity).sendMessage(Text.of("Charge: " + stack.get(ModDataComponentTypes.CHARGE)), true);
            }

            super.inventoryTick(stack, world, entity, slot, selected);
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 100000;
    }
}
