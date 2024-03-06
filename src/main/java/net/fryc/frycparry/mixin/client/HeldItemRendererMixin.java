package net.fryc.frycparry.mixin.client;

import net.fryc.frycparry.util.interfaces.CanBlock;
import net.fryc.frycparry.util.client.ItemRendererHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
abstract class HeldItemRendererMixin {

    // disables left hand rendering and sets shouldApplyParryTransform to true when player blocks with tools
    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void allowParryTransformationWhenNeeded(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if(((CanBlock) entity).getBlockingDataValue()){
            if(renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND){
                ItemRendererHelper.shouldApplyParryTransform = true;
            }
            else if(renderMode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND){
                info.cancel();
            }
        }
        else{
            ItemRendererHelper.shouldApplyParryTransform = false;
        }
    }





}
