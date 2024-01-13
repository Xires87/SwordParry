package net.fryc.frycparry.mixin.client;

import net.fryc.frycparry.util.client.ItemRendererHelper;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Transformation.class)
abstract class TransformationMixin {

    @Inject(method = "apply(ZLnet/minecraft/client/util/math/MatrixStack;)V", at = @At("HEAD"), cancellable = true)
    private void stopTransformation(boolean leftHanded, MatrixStack matrices, CallbackInfo info){
        if(ItemRendererHelper.shouldApplyParryTransform){ // todo zrobic zeby bloki sie nie bugowaly jak sie blokuje (chyba redirectem musze)
            info.cancel();
        }
    }
}
