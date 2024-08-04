package net.fryc.frycparry.network.c2s;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.minecraft.network.packet.CustomPayload;

public class StopBlockingC2SPacket {

    public static void receive(CustomPayload payload, ServerPlayNetworking.Context context){
        context.player().updateLastActionTime();
        ((CanBlock) context.player()).stopUsingItemParry();
    }
}
