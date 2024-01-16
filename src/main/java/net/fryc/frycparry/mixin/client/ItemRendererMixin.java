package net.fryc.frycparry.mixin.client;

import net.fryc.frycparry.util.client.ItemRendererHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
abstract class ItemRendererMixin {

/*
    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.AFTER))
    private void applyParryTransformationWhenNeeded(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices,
                                                    VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo info){
        if(renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND && ItemRendererHelper.shouldApplyParryTransform && stack.getItem() instanceof ToolItem){
            ItemRendererHelper.applyParryTransform(matrices);
        }
    }

 */

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;" +
            "ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;" +
            "IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/model/json/Transformation;apply(ZLnet/minecraft/client/util/math/MatrixStack;)V"))
    private void injected(Transformation transformation, boolean leftHanded, MatrixStack matrices,
                          ItemStack stack, ModelTransformationMode renderMode, boolean leftHandedv2, MatrixStack matricesv2,
                          VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model) {

        if(renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND && ItemRendererHelper.shouldApplyParryTransform && stack.getItem() instanceof ToolItem){
            ItemRendererHelper.applyParryTransform(matrices);
        }
        else {
            transformation.apply(leftHanded, matrices);
        }

    }



}
