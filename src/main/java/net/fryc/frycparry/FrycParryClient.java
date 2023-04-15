package net.fryc.frycparry;

import net.fabricmc.api.ClientModInitializer;
import net.fryc.frycparry.keybind.ModKeyBinds;
import net.fryc.frycparry.network.ModPackets;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;

public class FrycParryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ModKeyBinds.register();

    }
}
