package net.fryc.frycparry.mixin.client;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntityRenderer.class)
abstract class PlayerEntityRendererMixin {

    /*
    //transforms third person model when player is blocking
    @Inject(method = "getArmPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;", at = @At("TAIL"), cancellable = true)
    private static void renderThirdPersonBlockAnimation(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> ret) {
        if(((CanBlock) player).getBlockingDataValue()) ret.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
    }

     */
}
