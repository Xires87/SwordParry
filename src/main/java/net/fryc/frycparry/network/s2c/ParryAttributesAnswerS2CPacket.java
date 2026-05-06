package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.network.payloads.ParryAttributesAnswerPayload;

public class ParryAttributesAnswerS2CPacket {

    public static void receive(ParryAttributesAnswerPayload payload, ClientPlayNetworking.Context context){
        payload.parryAttributesMap().forEach(ParryAttributes::create);
    }

}
