package net.fryc.frycparry.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.network.c2s.PressedParryKeyC2SPacket;
import net.minecraft.util.Identifier;

public class ModPackets {

    public static final Identifier PARRY_ID = new Identifier(FrycParry.MOD_ID, "parry_id");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(PARRY_ID, PressedParryKeyC2SPacket::receive);
    }
}
