package net.fryc.frycparry.mixin;

import net.fryc.frycparry.util.CanBlock;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HeldItemRenderer.class)
abstract class HeldItemRendererMixin {


    //transforms first person model when player is blocking
    @ModifyVariable(method = "renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), ordinal = 0)
    private MatrixStack renderFirstPersonAnimationWhenBlocking(MatrixStack matrices, AbstractClientPlayerEntity player) {
        if(((CanBlock) player).getBlockingDataValue()){
            matrices.translate(-0.3F, 0.05F, -0.2F);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-25.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-1.0F));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(15.0F));
        }
        return matrices;
    }

}
