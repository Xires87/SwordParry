package net.fryc.frycparry.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.network.ModPackets;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
abstract class ServerWorldMixin {

    @Inject(method = "onPlayerConnected(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("TAIL"))
    private void sendConfigToClient(ServerPlayerEntity player, CallbackInfo info) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(FrycParry.config.enableBlockingWhenDualWielding);
        buf.writeBoolean(FrycParry.config.enableBlockingWithSword);
        buf.writeBoolean(FrycParry.config.enableBlockingWithAxe);
        buf.writeBoolean(FrycParry.config.enableBlockingWithPickaxe);
        buf.writeBoolean(FrycParry.config.enableBlockingWithShovel);
        buf.writeBoolean(FrycParry.config.enableBlockingWithHoe);
        buf.writeBoolean(FrycParry.config.enableBlockingWithOtherTools);
        ServerPlayNetworking.send(player, ModPackets.ANSWER_CONFIG_ID, buf); // <--- informs client about server's config to avoid visual bugs
    }
}
