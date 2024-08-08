package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.frycparry.network.payloads.SecondConfigAnswerPayload;
import net.fryc.frycparry.util.EnchantmentsConfigHelper;
import net.fryc.frycparry.util.ParryHelper;

public class SecondConfigAnswerS2CPacket {

    public static void receive(SecondConfigAnswerPayload payload, ClientPlayNetworking.Context context){
        EnchantmentsConfigHelper.shieldEnchantability = payload.shieldEnchantability();
        ParryHelper.dualWieldingSettings = payload.dualWieldingSettings();
    }
}
