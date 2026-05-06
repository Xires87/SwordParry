package net.fryc.frycparry.mixin.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.frycparry.network.payloads.ResetLastAttackedTicksPayload;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
abstract class MinecraftClientMixin {

    @Inject(method = "doAttack()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;resetLastAttackedTicks()V", shift = At.Shift.AFTER))
    private void checkItemCooldownBeforeUsing(CallbackInfoReturnable<Boolean> ret) {
        ClientPlayNetworking.send(new ResetLastAttackedTicksPayload(true));
    }
}
