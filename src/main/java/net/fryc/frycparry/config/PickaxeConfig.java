package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "pickaxe")
public class PickaxeConfig implements ConfigData {

    
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBlockingWithPickaxe = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int pickaxeParryTicks = 0;

    @Comment("% of damage TAKEN (not reduced) after blocking melee attack: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int pickaxeBlockMeleeDamageTaken = 80;

    @Comment("% of damage TAKEN (not reduced) after blocking projectile: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int pickaxeBlockArrowDamageTaken = 95;

    
    public double pickaxeParryKnockbackStrength = 5;

    
    public int pickaxeSlownessAfterParry = 100;

    
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int pickaxeSlownessAfterParryAmplifier = 1;

    
    public int pickaxeWeaknessAfterParry = 0;

    
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int pickaxeWeaknessAfterParryAmplifier = 1;

    
    public int pickaxeDisarmAfterParry = 20;

    
    public float cooldownAfterInterruptingPickaxeBlockAction = 35;

    
    public float cooldownAfterPickaxeParryAction = 28;

    public boolean shouldStopUsingPickaxeAfterBlockOrParry = true;

    public int maxUseTime = 7200;
}
