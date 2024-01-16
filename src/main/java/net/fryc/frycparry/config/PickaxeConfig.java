package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "pickaxe")
public class PickaxeConfig implements ConfigData {

    @ConfigEntry.Category("pickaxe")
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBlockingWithPickaxe = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("pickaxe")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int pickaxeParryTicks = 0;

    @Comment("% of damage TAKEN (not reduced) after blocking melee attack: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("pickaxe")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int pickaxeBlockMeleeDamageTaken = 80;

    @Comment("% of damage TAKEN (not reduced) after blocking projectile: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("pickaxe")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int pickaxeBlockArrowDamageTaken = 95;

    @ConfigEntry.Category("pickaxe")
    public double pickaxeParryKnockbackStrength = 5;

    @ConfigEntry.Category("pickaxe")
    public int pickaxeSlownessAfterParry = 100;

    @ConfigEntry.Category("pickaxe")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int pickaxeSlownessAfterParryAmplifier = 1;

    @ConfigEntry.Category("pickaxe")
    public int pickaxeWeaknessAfterParry = 0;

    @ConfigEntry.Category("pickaxe")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int pickaxeWeaknessAfterParryAmplifier = 1;

    @ConfigEntry.Category("pickaxe")
    public int pickaxeDisarmAfterParry = 20;

    @ConfigEntry.Category("pickaxe")
    public int cooldownAfterInterruptingPickaxeBlockAction = 35;

    @ConfigEntry.Category("pickaxe")
    public int cooldownAfterPickaxeParryAction = 28;
}
