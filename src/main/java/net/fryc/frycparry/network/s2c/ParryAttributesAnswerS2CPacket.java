package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.network.payloads.ConfigAnswerPayload;
import net.fryc.frycparry.network.payloads.ParryAttributesAnswerPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class ParryAttributesAnswerS2CPacket {

    public static void receive(ParryAttributesAnswerPayload payload, ClientPlayNetworking.Context context){
        payload.parryAttributesMap().forEach(ParryAttributes::create);
    }

}
