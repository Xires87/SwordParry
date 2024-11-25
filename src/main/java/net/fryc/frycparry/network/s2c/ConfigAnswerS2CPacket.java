package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fryc.frycparry.util.ConfigHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class ConfigAnswerS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        ConfigHelper.dualWieldingSettings = buf.readInt();
        ConfigHelper.enableBlockingWithSword = buf.readBoolean();
        ConfigHelper.enableBlockingWithAxe = buf.readBoolean();
        ConfigHelper.enableBlockingWithPickaxe = buf.readBoolean();
        ConfigHelper.enableBlockingWithShovel = buf.readBoolean();
        ConfigHelper.enableBlockingWithHoe = buf.readBoolean();
        ConfigHelper.enableBlockingWithOtherTools = buf.readBoolean();

        //enchantments
        ConfigHelper.enableReflexEnchantment = buf.readBoolean();
        ConfigHelper.enableParryEnchantment = buf.readBoolean();
        ConfigHelper.enableCounterattackEnchantment = buf.readBoolean();
        ConfigHelper.shieldEnchantability = buf.readInt();
    }
}
