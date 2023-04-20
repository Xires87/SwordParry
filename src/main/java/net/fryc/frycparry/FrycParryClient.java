package net.fryc.frycparry;

import net.fabricmc.api.ClientModInitializer;
import net.fryc.frycparry.keybind.ModKeyBinds;

public class FrycParryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ModKeyBinds.register();

    }
}
