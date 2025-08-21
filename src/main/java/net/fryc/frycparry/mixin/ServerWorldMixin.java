package net.fryc.frycparry.mixin;

import net.fryc.frycparry.util.ConfigHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
abstract class ServerWorldMixin {

    @Inject(method = "onPlayerConnected(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("TAIL"))
    private void synchronizeConfigAndParryAttributes(ServerPlayerEntity player, CallbackInfo info) {
        ConfigHelper.sendConfigToClient(player);
        ConfigHelper.sendParryAttributesToClient(player);
        ConfigHelper.applyParryAttributesOnClient(player);
    }
}
