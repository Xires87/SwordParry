package net.fryc.frycparry.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.network.c2s.ResetLastAttackedTicksC2SPacket;
import net.fryc.frycparry.network.c2s.StartParryingC2SPacket;
import net.fryc.frycparry.network.c2s.StopBlockingC2SPacket;
import net.fryc.frycparry.network.payloads.*;
import net.fryc.frycparry.network.s2c.FirstConfigAnswerS2CPacket;
import net.fryc.frycparry.network.s2c.SecondConfigAnswerS2CPacket;
import net.minecraft.util.Identifier;

public class ModPackets {

    public static final Identifier STOP_BLOCKING_ID = Identifier.of(FrycParry.MOD_ID, "stop_blocking_id");
    public static final Identifier START_PARRYING_ID = Identifier.of(FrycParry.MOD_ID, "start_parrying_id");
    public static final Identifier FIRST_ANSWER_CONFIG_ID = Identifier.of(FrycParry.MOD_ID, "first_answer_config_id");
    public static final Identifier SECOND_ANSWER_CONFIG_ID = Identifier.of(FrycParry.MOD_ID, "second_answer_config_id");
    public static final Identifier RESET_LAST_ATTACKED_TICKS_ID = Identifier.of(FrycParry.MOD_ID, "reset_last_attacked_ticks_id");

    public static void registerC2SPackets(){
        //payloads
        PayloadTypeRegistry.playS2C().register(FirstConfigAnswerPayload.ID, FirstConfigAnswerPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SecondConfigAnswerPayload.ID, SecondConfigAnswerPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(StopBlockingPayload.ID, StopBlockingPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(StartParryingPayload.ID, StartParryingPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ResetLastAttackedTicksPayload.ID, ResetLastAttackedTicksPayload.CODEC);

        //receivers
        ServerPlayNetworking.registerGlobalReceiver(StopBlockingPayload.ID, StopBlockingC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(StartParryingPayload.ID, StartParryingC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ResetLastAttackedTicksPayload.ID, ResetLastAttackedTicksC2SPacket::receive);
    }

    public static void registerS2CPackets(){
        //receivers
        ClientPlayNetworking.registerGlobalReceiver(FirstConfigAnswerPayload.ID, FirstConfigAnswerS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SecondConfigAnswerPayload.ID, SecondConfigAnswerS2CPacket::receive);
    }
}
