package net.fryc.frycparry;

import net.fabricmc.api.ClientModInitializer;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;

public class FrycParryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(new Identifier("parry"), (stack, world, entity, seed) -> {
            return entity != null && (entity.getOffHandStack().isEmpty() || ParryHelper.checkDualWielding((PlayerEntity) entity)) && entity.isUsingItem() && entity.getActiveItem().getItem() instanceof SwordItem ? 1.0F : 0.0F;
        });

        ModelPredicateProviderRegistry.register(new Identifier("axeparry"), (stack, world, entity, seed) -> {
            return entity != null && (entity.getOffHandStack().isEmpty() || ParryHelper.checkDualWielding((PlayerEntity) entity)) && entity.isUsingItem() && entity.getActiveItem().getItem() instanceof AxeItem ? 1.0F : 0.0F;
        });

    }
}
