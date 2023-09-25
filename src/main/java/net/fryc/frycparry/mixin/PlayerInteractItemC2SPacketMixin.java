package net.fryc.frycparry.mixin;


import net.fryc.frycparry.util.OnParryInteraction;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerInteractItemC2SPacket.class)
abstract class PlayerInteractItemC2SPacketMixin implements Packet<ServerPlayPacketListener> {

    @Redirect(method = "Lnet/minecraft/network/packet/c2s/play/PlayerInteractItemC2SPacket;apply(Lnet/minecraft/network/listener/ServerPlayPacketListener;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/listener/ServerPlayPacketListener;onPlayerInteractItem(Lnet/minecraft/network/packet/c2s/play/PlayerInteractItemC2SPacket;)V"))
    private void injected(ServerPlayPacketListener listener, PlayerInteractItemC2SPacket packet) {
        ((OnParryInteraction) listener).onPlayerInteractItemParry(packet); // todo sprobowac zrobic wlasny pakiet zeby nie ingerowac w pakiety minecrafta
    }

}
