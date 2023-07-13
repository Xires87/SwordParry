package net.fryc.frycparry.mixin;

import net.fryc.frycparry.keybind.ModKeyBinds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
abstract class MinecraftClientMixin extends ReentrantThreadExecutor<Runnable> implements WindowEventHandler {


    public MinecraftClientMixin(String string) {
        super(string);
    }


// required for shield blocking with parry key
    @Redirect(method = "Lnet/minecraft/client/MinecraftClient;handleInputEvents()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;stopUsingItem(Lnet/minecraft/entity/player/PlayerEntity;)V"))
    private void pressed(ClientPlayerInteractionManager manager, PlayerEntity player) {
        MinecraftClient dys = ((MinecraftClient)(Object)this);
        if(!ModKeyBinds.parrykey.isPressed()){
            dys.interactionManager.stopUsingItem(dys.player);
        }
    }
}
