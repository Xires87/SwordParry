package net.fryc.frycparry.network.c2s;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fryc.frycparry.util.interfaces.OnParryInteraction;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public class StartParryingC2SPacket {


    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        ((OnParryInteraction)handler).onPlayerInteractItemParry(player, player.getServerWorld(), Hand.MAIN_HAND); // through ServerPlayNetworkHandler first
    }
}
