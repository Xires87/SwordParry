package net.fryc.frycparry.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.frycparry.keybind.ModKeyBinds;
import net.fryc.frycparry.network.payloads.ResetLastAttackedTicksPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
abstract class MinecraftClientMixin extends ReentrantThreadExecutor<Runnable> implements WindowEventHandler {


    public MinecraftClientMixin(String string) {
        super(string);
    }


// required for shield blocking with parry key
    @WrapOperation(method = "Lnet/minecraft/client/MinecraftClient;handleInputEvents()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;stopUsingItem(Lnet/minecraft/entity/player/PlayerEntity;)V"))
    private void pressed(ClientPlayerInteractionManager manager, PlayerEntity player, Operation<Void> original) {
        MinecraftClient dys = ((MinecraftClient)(Object)this);
        if(!ModKeyBinds.parrykey.isPressed()){
            original.call(manager, player);
        }
    }


    @Inject(method = "doAttack()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;resetLastAttackedTicks()V", shift = At.Shift.AFTER))
    private void checkItemCooldownBeforeUsing(CallbackInfoReturnable<Boolean> ret) {
        ClientPlayNetworking.send(new ResetLastAttackedTicksPayload(true));
    }
}
