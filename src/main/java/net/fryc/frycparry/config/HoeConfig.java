package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "hoe")
public class HoeConfig implements ConfigData {

    @ConfigEntry.Category("hoe")
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBlockingWithHoe = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("hoe")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int hoeParryTicks = 1;

    @Comment("% of damage TAKEN (not reduced) after blocking melee attack: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("hoe")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int hoeBlockMeleeDamageTaken = 85;

    @Comment("% of damage TAKEN (not reduced) after blocking projectile: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("hoe")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int hoeBlockArrowDamageTaken = 97;

    @ConfigEntry.Category("hoe")
    public double hoeParryKnockbackStrength = 3;

    @ConfigEntry.Category("hoe")
    public int hoeSlownessAfterParry = 40;

    @ConfigEntry.Category("hoe")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int hoeSlownessAfterParryAmplifier = 1;

    @ConfigEntry.Category("hoe")
    public int hoeWeaknessAfterParry = 0;

    @ConfigEntry.Category("hoe")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int hoeWeaknessAfterParryAmplifier = 1;

    @ConfigEntry.Category("hoe")
    public int hoeDisarmAfterParry = 25;

    @ConfigEntry.Category("hoe")
    public int cooldownAfterInterruptingHoeBlockAction = 8;

    @ConfigEntry.Category("hoe")
    public int cooldownAfterHoeParryAction = 2;
}
