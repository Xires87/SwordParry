package net.fryc.frycparry.effects;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static RegistryEntry<StatusEffect> DISARMED;

    static StatusEffect disarmed = new DisarmedEffect(StatusEffectCategory.HARMFUL, 4738376);

    public static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(FrycParry.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        DISARMED = registerStatusEffect("disarmed", disarmed);
    }
}
