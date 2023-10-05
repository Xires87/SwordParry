package net.fryc.frycparry.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.network.c2s.StartParryingC2SPacket;
import net.fryc.frycparry.network.c2s.StopBlockingC2SPacket;
import net.fryc.frycparry.network.s2c.ConfigAnswerS2CPacket;
import net.minecraft.util.Identifier;

public class ModPackets {

    public static final Identifier STOP_BLOCKING_ID = new Identifier(FrycParry.MOD_ID, "stop_blocking_id");
    public static final Identifier START_PARRYING_ID = new Identifier(FrycParry.MOD_ID, "start_parrying_id");
    public static final Identifier ANSWER_CONFIG_ID = new Identifier(FrycParry.MOD_ID, "answer_config_id");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(STOP_BLOCKING_ID, StopBlockingC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(START_PARRYING_ID, StartParryingC2SPacket::receive);
    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(ANSWER_CONFIG_ID, ConfigAnswerS2CPacket::receive);
    }
}
