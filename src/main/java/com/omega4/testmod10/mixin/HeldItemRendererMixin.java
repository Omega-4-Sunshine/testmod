package com.omega4.testmod10.mixin;

import com.omega4.testmod10.item.ModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    //@Shadow protected abstract void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    //@Shadow protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);

    @Shadow public abstract void renderItem(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light);

    @Shadow private ItemStack mainHand;

    @Shadow private float prevEquipProgressMainHand;


    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
    private void renderFirstPersonItem(
            AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci
    ) {
        if(item.getItem() == ModItems.HEAL_BEAM_2) {

            //vanilla matrix transforms for basic item -----------------------------------------------------------------
            matrices.push();
            float n = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
            float mxx = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) (Math.PI * 2));
            float fxxx = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
            int o = true ? 1 : -1;
            matrices.translate(o * n, mxx, fxxx);
            matrices.translate(1 * 0.56F, -0.52F + (equipProgress/5) * -0.6F, -0.72F); //the single 0 stands for the equip progress
            int i = 1;
            float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * (45.0F + f * -20.0F)));
            float g = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(i * g * -20.0F));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * -80.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i * -45.0F));
            //----------------------------------------------------------------------------------------------------------

            ((HeldItemRenderer)(Object)this).renderItem(
                    player,
                    item,
                    true ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
                    !true,
                    matrices,
                    vertexConsumers,
                    light
            );
            ci.cancel();
        }
    }

}
