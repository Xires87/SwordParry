package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;

public class ParryAttributesApplyAnswerS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        buf.readMap(PacketByteBuf::readIdentifier, PacketByteBuf::readString).forEach((itemId, attrId) -> {
            ((ParryItem) Registries.ITEM.get(itemId)).setParryAttributes(attrId);
        });
    }

}
