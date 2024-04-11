package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "hoe")
public class HoeConfig implements ConfigData {

    
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBlockingWithHoe = true;

    @ConfigEntry.Gui.Tooltip
    
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int hoeParryTicks = 1;

    @Comment("% of damage TAKEN (not reduced) after blocking melee attack: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int hoeBlockMeleeDamageTaken = 85;

    @Comment("% of damage TAKEN (not reduced) after blocking projectile: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int hoeBlockArrowDamageTaken = 97;

    
    public double hoeParryKnockbackStrength = 3;

    
    public int hoeSlownessAfterParry = 40;

    
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int hoeSlownessAfterParryAmplifier = 1;

    
    public int hoeWeaknessAfterParry = 0;

    
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int hoeWeaknessAfterParryAmplifier = 1;

    
    public int hoeDisarmAfterParry = 25;

    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -5, cooldown will be: valueBasedOnAttackSpeed * 5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterInterruptingHoeBlockAction = -2;

    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -0.5, cooldown will be: valueBasedOnAttackSpeed * 0.5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterHoeParryAction = -1;

    public boolean shouldStopUsingHoeAfterBlockOrParry = true;
    public int maxUseTime = 7200;
}
