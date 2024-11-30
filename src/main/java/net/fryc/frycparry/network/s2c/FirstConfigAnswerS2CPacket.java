package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.frycparry.network.payloads.FirstConfigAnswerPayload;
import net.fryc.frycparry.util.ConfigHelper;

public class FirstConfigAnswerS2CPacket {

    public static void receive(FirstConfigAnswerPayload payload, ClientPlayNetworking.Context context){
        ConfigHelper.enableBlockingWithSword = payload.enSwordBlocking();
        ConfigHelper.enableBlockingWithAxe = payload.enAxeBlocking();
        ConfigHelper.enableBlockingWithPickaxe = payload.enPickaxeBlocking();
        ConfigHelper.enableBlockingWithShovel = payload.enShovelBlocking();
        ConfigHelper.enableBlockingWithHoe = payload.enHoeBlocking();
        ConfigHelper.enableBlockingWithOtherTools = payload.enOtherBlocking();
    }
}
