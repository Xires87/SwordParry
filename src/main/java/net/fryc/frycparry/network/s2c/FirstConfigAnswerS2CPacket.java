package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.frycparry.network.payloads.FirstConfigAnswerPayload;
import net.fryc.frycparry.util.ParryHelper;

public class FirstConfigAnswerS2CPacket {

    public static void receive(FirstConfigAnswerPayload payload, ClientPlayNetworking.Context context){
        ParryHelper.enableBlockingWithSword = payload.enSwordBlocking();
        ParryHelper.enableBlockingWithAxe = payload.enAxeBlocking();
        ParryHelper.enableBlockingWithPickaxe = payload.enPickaxeBlocking();
        ParryHelper.enableBlockingWithShovel = payload.enShovelBlocking();
        ParryHelper.enableBlockingWithHoe = payload.enHoeBlocking();
        ParryHelper.enableBlockingWithOtherTools = payload.enOtherBlocking();
    }
}
