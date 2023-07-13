package net.fryc.frycparry.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.network.c2s.NotHoldingParryKeyC2SPacket;
import net.fryc.frycparry.network.c2s.PressedParryKeyC2SPacket;
import net.fryc.frycparry.network.c2s.StopBlockingC2SPacket;
import net.minecraft.util.Identifier;

public class ModPackets {

    public static final Identifier PARRY_ID = new Identifier(FrycParry.MOD_ID, "parry_id");
    public static final Identifier NOT_HOLDING_PARRY_KEY_ID = new Identifier(FrycParry.MOD_ID, "not_holding_parry_key_id");
    public static final Identifier STOP_BLOCKING_ID = new Identifier(FrycParry.MOD_ID, "stop_blocking_id");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(PARRY_ID, PressedParryKeyC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(NOT_HOLDING_PARRY_KEY_ID, NotHoldingParryKeyC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(STOP_BLOCKING_ID, StopBlockingC2SPacket::receive);
    }
}
