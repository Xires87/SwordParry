package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "shovel")
public class ShovelConfig implements ConfigData {

    @ConfigEntry.Category("shovel")
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBlockingWithShovel = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("shovel")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int shovelParryTicks = 2;

    @Comment("% of damage TAKEN (not reduced) after blocking melee attack: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("shovel")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int shovelBlockMeleeDamageTaken = 71;

    @Comment("% of damage TAKEN (not reduced) after blocking projectile: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("shovel")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int shovelBlockArrowDamageTaken = 82;

    @ConfigEntry.Category("shovel")
    public double shovelParryKnockbackStrength = 9;

    @ConfigEntry.Category("shovel")
    public int shovelSlownessAfterParry = 110;

    @ConfigEntry.Category("shovel")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int shovelSlownessAfterParryAmplifier = 1;

    @ConfigEntry.Category("shovel")
    public int shovelWeaknessAfterParry = 0;

    @ConfigEntry.Category("shovel")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int shovelWeaknessAfterParryAmplifier = 1;

    @ConfigEntry.Category("shovel")
    public int shovelDisarmAfterParry = 28;

    @ConfigEntry.Category("shovel")
    public int cooldownAfterInterruptingShovelBlockAction = 30;

    @ConfigEntry.Category("shovel")
    public int cooldownAfterShovelParryAction = 20;
}
