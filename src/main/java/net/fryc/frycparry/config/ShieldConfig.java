package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "shield")
public class ShieldConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int shieldParryTicks = 4;

    @Comment("% of damage TAKEN (not reduced) after blocking melee attack: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int shieldBlockMeleeDamageTaken = 0;

    @Comment("% of damage TAKEN (not reduced) after blocking projectile: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int shieldBlockArrowDamageTaken = 0;

    public double shieldParryKnockbackStrength = 9;

    public int shieldSlownessAfterParry = 100;

    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int shieldSlownessAfterParryAmplifier = 1;

    public int shieldWeaknessAfterParry = 0;

    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int shieldWeaknessAfterParryAmplifier = 1;

    public int shieldDisarmAfterParry = 45;
    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -5, cooldown will be: valueBasedOnAttackSpeed * 5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterInterruptingShieldBlockAction = 26;
    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -0.5, cooldown will be: valueBasedOnAttackSpeed * 0.5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterShieldParryAction = 16;

    public boolean shouldStopUsingShieldAfterBlockOrParry = false;

}
