package net.fryc.frycparry.mixin;

import net.fryc.frycparry.util.OnParryInteraction;
import net.fryc.frycparry.util.ServerParryInteraction;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.EntityTrackingListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayNetworkHandler.class)
abstract class ServerPlayNetworkHandlerMixin implements EntityTrackingListener, TickablePacketListener, ServerPlayPacketListener, OnParryInteraction {

    @Shadow public ServerPlayerEntity player;

    @Shadow
    public void updateSequence(int sequence) {
    }
    public void onPlayerInteractItemParry(PlayerInteractItemC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, ((ServerPlayNetworkHandler)(Object)this), this.player.getWorld());
        this.updateSequence(packet.getSequence());
        ServerWorld serverWorld = this.player.getWorld();
        Hand hand = packet.getHand();
        ItemStack itemStack = this.player.getStackInHand(hand);
        this.player.updateLastActionTime();
        if (!itemStack.isEmpty() && itemStack.isItemEnabled(serverWorld.getEnabledFeatures())) {
            ActionResult actionResult = ((ServerParryInteraction) this.player.interactionManager).interactItemParry(this.player, serverWorld, itemStack, hand);
            /*
            if (actionResult.shouldSwingHand()) {
                this.player.swingHand(hand, true);
            }

             */

        }
    }
}
