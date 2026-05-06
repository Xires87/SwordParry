package net.fryc.frycparry.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.network.c2s.ResetLastAttackedTicksC2SPacket;
import net.fryc.frycparry.network.payloads.*;
import net.fryc.frycparry.network.s2c.*;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import oshi.util.tuples.Quartet;

public class ModPackets {


    public static final Identifier ANSWER_CONFIG_ID = Identifier.of(FrycParry.MOD_ID, "answer_config_id");
    public static final Identifier RESET_LAST_ATTACKED_TICKS_ID = Identifier.of(FrycParry.MOD_ID, "reset_last_attacked_ticks_id");
    public static final Identifier INFORM_CLIENT_ABOUT_PARRY_ID = Identifier.of(FrycParry.MOD_ID, "inform_client_about_parry_id");
    public static final Identifier SYNCHRONIZE_PARRY_COOLDOWN_ID = Identifier.of(FrycParry.MOD_ID, "synchronize_parry_cooldown_id");

    public static final Identifier ANSWER_PARRY_ATTRIBUTES_ID = Identifier.of(FrycParry.MOD_ID, "answer_parry_attributes_id");
    public static final Identifier ANSWER_APPLY_PARRY_ATTRIBUTES_ID = Identifier.of(FrycParry.MOD_ID, "answer_apply_parry_attributes_id");

    public static final PacketCodec<RegistryByteBuf, Quartet<Integer, Integer, Float, Float>> QUARTET_PACKET_CODEC = new PacketCodec<>() {
        @Override
        public Quartet<Integer, Integer, Float, Float> decode(RegistryByteBuf buf) {
            int a = PacketCodecs.INTEGER.decode(buf);
            int b = PacketCodecs.INTEGER.decode(buf);
            float c = PacketCodecs.FLOAT.decode(buf);
            float d = PacketCodecs.FLOAT.decode(buf);

            return new Quartet<>(a, b, c, d);
        }

        @Override
        public void encode(RegistryByteBuf buf, Quartet<Integer, Integer, Float, Float> value) {
            PacketCodecs.INTEGER.encode(buf, value.getA());
            PacketCodecs.INTEGER.encode(buf, value.getB());
            PacketCodecs.FLOAT.encode(buf, value.getC());
            PacketCodecs.FLOAT.encode(buf, value.getD());
        }
    };

    public static void registerC2SPackets(){
        //payloads
        PayloadTypeRegistry.playS2C().register(ConfigAnswerPayload.ID, ConfigAnswerPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(InformClientAboutParryPayload.ID, InformClientAboutParryPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SynchronizeParryCooldownPayload.ID, SynchronizeParryCooldownPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ParryAttributesAnswerPayload.ID, ParryAttributesAnswerPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ParryAttributesApplyAnswerPayload.ID, ParryAttributesApplyAnswerPayload.CODEC);

        PayloadTypeRegistry.playC2S().register(ResetLastAttackedTicksPayload.ID, ResetLastAttackedTicksPayload.CODEC);

        //receivers
        ServerPlayNetworking.registerGlobalReceiver(ResetLastAttackedTicksPayload.ID, ResetLastAttackedTicksC2SPacket::receive);
    }

    public static void registerS2CPackets(){
        //receivers
        ClientPlayNetworking.registerGlobalReceiver(ConfigAnswerPayload.ID, ConfigAnswerS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(InformClientAboutParryPayload.ID, InformClientAboutParryS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SynchronizeParryCooldownPayload.ID, SynchronizeParryCooldownS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ParryAttributesAnswerPayload.ID, ParryAttributesAnswerS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ParryAttributesApplyAnswerPayload.ID, ParryAttributesApplyAnswerS2CPacket::receive);
    }
}
