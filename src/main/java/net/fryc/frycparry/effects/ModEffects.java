package net.fryc.frycparry.effects;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {

    public static StatusEffect DISARMED;

    static StatusEffect disarmed = new DisarmedEffect(StatusEffectCategory.HARMFUL, 4738376);

    public static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(FrycParry.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        DISARMED = registerStatusEffect("disarmed", disarmed);
    }
}
