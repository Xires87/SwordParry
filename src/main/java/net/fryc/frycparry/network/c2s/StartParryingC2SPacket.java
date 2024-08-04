package net.fryc.frycparry.network.c2s;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.util.interfaces.OnParryInteraction;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public class StartParryingC2SPacket {


    public static void receive(CustomPayload payload, ServerPlayNetworking.Context context){
        ServerPlayerEntity player = context.player();
        ((OnParryInteraction)player.networkHandler).onPlayerInteractItemParry(player, player.getServerWorld(), Hand.MAIN_HAND); // through ServerPlayNetworkHandler first
    }
}
