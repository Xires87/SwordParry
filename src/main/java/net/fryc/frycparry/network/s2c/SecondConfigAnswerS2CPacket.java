package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.frycparry.network.payloads.SecondConfigAnswerPayload;
import net.fryc.frycparry.util.ConfigHelper;

public class SecondConfigAnswerS2CPacket {

    public static void receive(SecondConfigAnswerPayload payload, ClientPlayNetworking.Context context){
        ConfigHelper.enableBlockingWithMace = payload.enMaceBlocking();
        ConfigHelper.shieldEnchantability = payload.shieldEnchantability();
        ConfigHelper.dualWieldingSettings = payload.dualWieldingSettings();
    }
}
