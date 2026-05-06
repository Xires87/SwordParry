package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.frycparry.attributes.ParryCooldownManager;
import net.fryc.frycparry.network.payloads.SynchronizeParryCooldownPayload;
import net.fryc.frycparry.util.mixin_interfaces.HasParryCooldownManager;
import net.minecraft.client.network.ClientPlayerEntity;

public class SynchronizeParryCooldownS2CPacket {

    public static void receive(SynchronizeParryCooldownPayload payload, ClientPlayNetworking.Context context){
        ClientPlayerEntity player = context.player();
        if(player != null){
            ParryCooldownManager manager = ((HasParryCooldownManager) player).getParryCooldownManager();
            manager.setCooldown(payload.cooldown());
            manager.onCooldownUpdate();
        }
    }
}
