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

    public String pickaxeParryEffects = "minecraft:slowness;100;1;1.0;0.3;" +
            "frycparry:disarmed;20;1;1.0;0.12";


    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -5, cooldown will be: valueBasedOnAttackSpeed * 5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterInterruptingPickaxeBlockAction = -2.8f;

    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -0.5, cooldown will be: valueBasedOnAttackSpeed * 0.5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterPickaxeParryAction = -2.5f;

    public boolean shouldStopUsingPickaxeAfterBlockOrParry = true;

    public int maxUseTime = 7200;
}
