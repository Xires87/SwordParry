package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "axe")
public class AxeConfig implements ConfigData {

    @ConfigEntry.Category("axe")
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBlockingWithAxe = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("axe")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int axeParryTicks = 4;

    @Comment("% of damage TAKEN (not reduced) after blocking melee attack: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("axe")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int axeBlockMeleeDamageTaken = 43;

    @Comment("% of damage TAKEN (not reduced) after blocking projectile: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("axe")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int axeBlockArrowDamageTaken = 85;

    @ConfigEntry.Category("axe")
    public double axeParryKnockbackStrength = 7;

    @ConfigEntry.Category("axe")
    public int axeSlownessAfterParry = 100;

    @ConfigEntry.Category("axe")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int axeSlownessAfterParryAmplifier = 1;

    @ConfigEntry.Category("axe")
    public int axeWeaknessAfterParry = 0;

    @ConfigEntry.Category("axe")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int axeWeaknessAfterParryAmplifier = 1;

    @ConfigEntry.Category("axe")
    public int axeDisarmAfterParry = 45;

    @ConfigEntry.Category("axe")
    public int cooldownAfterInterruptingAxeBlockAction = 24;

    @ConfigEntry.Category("axe")
    public int cooldownAfterAxeParryAction = 15;
}
