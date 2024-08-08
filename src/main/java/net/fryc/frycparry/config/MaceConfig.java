package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "mace")
public class MaceConfig implements ConfigData {

    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBlockingWithMace = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int maceParryTicks = 2;

    @Comment("% of damage TAKEN (not reduced) after blocking melee attack: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int maceBlockMeleeDamageTaken = 30;

    @Comment("% of damage TAKEN (not reduced) after blocking projectile: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int maceBlockArrowDamageTaken = 65;


    public double maceParryKnockbackStrength = 11;


    public int maceSlownessAfterParry = 160;


    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int maceSlownessAfterParryAmplifier = 1;


    public int maceWeaknessAfterParry = 0;


    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int maceWeaknessAfterParryAmplifier = 1;


    public int maceDisarmAfterParry = 35;

    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -5, cooldown will be: valueBasedOnAttackSpeed * 5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterInterruptingMaceBlockAction = -2.1f;

    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -0.5, cooldown will be: valueBasedOnAttackSpeed * 0.5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterMaceParryAction = -1.4f;

    public boolean shouldStopUsingMaceAfterBlockOrParry = true;
    public int maxUseTime = 7200;
}
