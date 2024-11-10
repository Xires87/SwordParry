package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fryc.frycparry.client.SyncParryItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public class SyncParryItemMaxUseTimeS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        LivingEntity entity = client.player;
        if (entity == null) return;
        ItemStack activeItem = entity.getActiveItem();
        if (activeItem.isEmpty()) return;
        ((SyncParryItem) activeItem.getItem()).setSyncMaxUseTime(buf.readVarInt());
    }
}
