package net.fryc.frycparry.network.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fryc.frycparry.util.EnchantmentsConfigHelper;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class ConfigAnswerS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        ParryHelper.dualWieldingSettings = buf.readInt();
        ParryHelper.enableBlockingWithSword = buf.readBoolean();
        ParryHelper.enableBlockingWithAxe = buf.readBoolean();
        ParryHelper.enableBlockingWithPickaxe = buf.readBoolean();
        ParryHelper.enableBlockingWithShovel = buf.readBoolean();
        ParryHelper.enableBlockingWithHoe = buf.readBoolean();
        ParryHelper.enableBlockingWithOtherTools = buf.readBoolean();

        //enchantments
        EnchantmentsConfigHelper.enableReflexEnchantment = buf.readBoolean();
        EnchantmentsConfigHelper.enableParryEnchantment = buf.readBoolean();
        EnchantmentsConfigHelper.enableCounterattackEnchantment = buf.readBoolean();
        EnchantmentsConfigHelper.shieldEnchantability = buf.readInt();
    }
}
