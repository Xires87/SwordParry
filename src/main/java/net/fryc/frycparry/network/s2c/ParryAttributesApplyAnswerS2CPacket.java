package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.frycparry.network.payloads.ParryAttributesApplyAnswerPayload;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.registry.Registries;

public class ParryAttributesApplyAnswerS2CPacket {

    public static void receive(ParryAttributesApplyAnswerPayload payload, ClientPlayNetworking.Context context){
        payload.parryAttributesForItems().forEach((itemId, attrId) -> {
            ((ParryItem) Registries.ITEM.get(itemId)).setParryAttributes(attrId);
        });
    }

}
