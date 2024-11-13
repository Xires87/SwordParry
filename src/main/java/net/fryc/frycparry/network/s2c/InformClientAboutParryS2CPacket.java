package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.frycparry.network.payloads.InformClientAboutParryPayload;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.minecraft.client.network.ClientPlayerEntity;

public class InformClientAboutParryS2CPacket {

    public static void receive(InformClientAboutParryPayload payload, ClientPlayNetworking.Context context){
        ClientPlayerEntity player = context.player();
        if(player != null){
            ((CanBlock) player).setParryTimer(player.getWorld(), payload.ticks());
        }
    }


}
