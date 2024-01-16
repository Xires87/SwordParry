package net.fryc.frycparry;

import net.fabricmc.api.ClientModInitializer;
import net.fryc.frycparry.keybind.ModKeyBinds;
import net.fryc.frycparry.network.ModPackets;

public class FrycParryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModPackets.registerS2CPackets();
        ModKeyBinds.register();

    }
}
