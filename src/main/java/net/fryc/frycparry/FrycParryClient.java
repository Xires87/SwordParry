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
        /*
        ModelPredicateProviderRegistry.register(new Identifier("parry"), (stack, world, entity, seed) -> {
            return entity != null && (entity.getOffHandStack().isEmpty() || ParryHelper.checkDualWieldingWeapons(entity) || ParryHelper.checkDualWieldingItems(entity)) && entity.isUsingItem() && entity.getActiveItem().getItem() instanceof SwordItem ? 1.0F : 0.0F;
        });

        ModelPredicateProviderRegistry.register(new Identifier("axeparry"), (stack, world, entity, seed) -> {
            return entity != null && (entity.getOffHandStack().isEmpty() || ParryHelper.checkDualWieldingWeapons(entity) || ParryHelper.checkDualWieldingItems(entity)) && entity.isUsingItem() && entity.getActiveItem().getItem() instanceof AxeItem ? 1.0F : 0.0F;
        });
 todo usunac niepotrzebne modele, powyzszy kod i zrobic (hehe) animacje
         */

        ModKeyBinds.register();

    }
}
