package net.fryc.frycparry.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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

@Mixin(ItemRenderer.class)
abstract class ItemRendererMixin {

    @WrapOperation(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;" +
                    "ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;" +
                    "IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/model/json/Transformation;apply(ZLnet/minecraft/client/util/math/MatrixStack;)V")
    )
    private void applyParryModelTransformation(Transformation transformation, boolean leftHanded, MatrixStack matrices, Operation<Void> original,
                      ItemStack stack, ModelTransformationMode renderMode, boolean leftHandedv2, MatrixStack matricesv2,
                      VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model) {


        if(renderMode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND && ItemRendererHelper.shouldApplyParryTransform && stack.getItem() instanceof ToolItem){
            ItemRendererHelper.applyParryTransform(matrices, transformation);
        }
        else {
            original.call(transformation, leftHanded, matrices);
        }
    }



}
