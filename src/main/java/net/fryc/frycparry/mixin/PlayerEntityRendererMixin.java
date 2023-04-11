package net.fryc.frycparry.mixin;

import net.fryc.frycparry.util.CanBlock;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
abstract class PlayerEntityRendererMixin {


    //transforms third person model when player is blocking
    @Inject(method = "getArmPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;", at = @At("TAIL"), cancellable = true)
    private static void renderThirdPersonBlockAnimation(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> ret) {
        if(((CanBlock) player).getBlockingDataValue()) ret.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
    }
}
