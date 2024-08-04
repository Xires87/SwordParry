package net.fryc.frycparry.mixin.client;

import net.fryc.frycparry.tag.ModItemTags;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.client.ItemRendererHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
abstract class ItemRendererMixin {

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;" +
            "ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;" +
            "IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
    private void applyParryModelTransformation(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices,
                          VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo info) {

        if(renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND && ItemRendererHelper.shouldApplyParryTransform &&
                (ParryHelper.hasAttackSpeedAttribute(stack) || stack.isIn(ModItemTags.ITEMS_CAN_PARRY))){

            matrices.pop();
            matrices.push();
            ItemRendererHelper.applyParryTransform(matrices, model.getTransformation().getTransformation(renderMode));
        }
    }

}
