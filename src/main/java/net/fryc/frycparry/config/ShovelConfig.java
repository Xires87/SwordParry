package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "shovel")
public class ShovelConfig implements ConfigData {

    
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBlockingWithShovel = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int shovelParryTicks = 2;

    @Comment("% of damage TAKEN (not reduced) after blocking melee attack: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int shovelBlockMeleeDamageTaken = 71;

    @Comment("% of damage TAKEN (not reduced) after blocking projectile: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int shovelBlockArrowDamageTaken = 82;

    
    public double shovelParryKnockbackStrength = 9;

    
    public int shovelSlownessAfterParry = 110;

    
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int shovelSlownessAfterParryAmplifier = 1;

    
    public int shovelWeaknessAfterParry = 0;

    
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int shovelWeaknessAfterParryAmplifier = 1;

    
    public int shovelDisarmAfterParry = 28;

    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -5, cooldown will be: valueBasedOnAttackSpeed * 5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterInterruptingShovelBlockAction = -2.4f;

    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -0.5, cooldown will be: valueBasedOnAttackSpeed * 0.5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterShovelParryAction = -1.8f;

    public boolean shouldStopUsingShovelAfterBlockOrParry = true;
    public int maxUseTime = 7200;
}
