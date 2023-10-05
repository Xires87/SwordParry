package net.fryc.frycparry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fryc.frycparry.config.FrycparryClientConfig;
import net.fryc.frycparry.keybind.ModKeyBinds;
import net.fryc.frycparry.network.ModPackets;

public class FrycParryClient implements ClientModInitializer {

    public static FrycparryClientConfig config;
    @Override
    public void onInitializeClient() {
        AutoConfig.register(FrycparryClientConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(FrycparryClientConfig.class).getConfig();

        ModPackets.registerS2CPackets();
        ModKeyBinds.register();

    }
}
