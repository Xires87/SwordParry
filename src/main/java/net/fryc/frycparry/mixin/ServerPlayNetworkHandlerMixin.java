package net.fryc.frycparry.mixin;

import net.fryc.frycparry.util.interfaces.OnParryInteraction;
import net.fryc.frycparry.util.interfaces.ServerParryInteraction;
import net.minecraft.item.ItemStack;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayNetworkHandler.class)
abstract class ServerPlayNetworkHandlerMixin extends ServerCommonNetworkHandler implements ServerPlayPacketListener, PlayerAssociatedNetworkHandler, TickablePacketListener, OnParryInteraction {


    public ServerPlayNetworkHandlerMixin(MinecraftServer server, ClientConnection connection, ConnectedClientData clientData) {
        super(server, connection, clientData);
    }


    public void onPlayerInteractItemParry(ServerPlayerEntity player, ServerWorld world, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        player.updateLastActionTime();
        if (!itemStack.isEmpty() && itemStack.isItemEnabled(world.getEnabledFeatures())) {
            ((ServerParryInteraction) player.interactionManager).interactItemParry(player, world, itemStack, hand);
        }
    }


}
