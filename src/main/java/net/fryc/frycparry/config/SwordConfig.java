package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "sword")
public class SwordConfig implements ConfigData {

    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBlockingWithSword = true;


    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int swordParryTicks = 4;

    @Comment("% of damage TAKEN (not reduced) after blocking melee attack: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip

    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int swordBlockMeleeDamageTaken = 50;

    @Comment("% of damage TAKEN (not reduced) after blocking projectile: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip

    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int swordBlockArrowDamageTaken = 90;


    public double swordParryKnockbackStrength = 6;


    public int swordSlownessAfterParry = 100;


    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int swordSlownessAfterParryAmplifier = 1;


    public int swordWeaknessAfterParry = 0;


    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int swordWeaknessAfterParryAmplifier = 1;


    public int swordDisarmAfterParry = 45;

    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -5, cooldown will be: valueBasedOnAttackSpeed * 5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterInterruptingSwordBlockAction = -2;
    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -0.5, cooldown will be: valueBasedOnAttackSpeed * 0.5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterSwordParryAction = -1;

    public boolean shouldStopUsingSwordAfterBlockOrParry = true;

    public int maxUseTime = 7200;

}
