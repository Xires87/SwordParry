package net.fryc.frycparry.network.c2s;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

public class ResetLastAttackedTicksC2SPacket {

    public static void receive(CustomPayload payload, ServerPlayNetworking.Context context){
        context.player().resetLastAttackedTicks();
    }
}
