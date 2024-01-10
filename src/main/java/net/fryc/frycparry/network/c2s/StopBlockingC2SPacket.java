package net.fryc.frycparry.network.c2s;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class StopBlockingC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        player.updateLastActionTime();
        ((CanBlock) player).stopUsingItemParry();
    }
}
