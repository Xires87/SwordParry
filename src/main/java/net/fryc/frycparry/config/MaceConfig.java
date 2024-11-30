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


    @Comment("""
            List of status effects that can be applied to attacker in the following pattern:\s
            'StatusEffect;duration;amplifier;chance;enchantmentModifier;StatusEffect;duration;amplifier;chance;enchantmentModifier'\s
            chance: 1.0 = 100%\s
            enchantmentModifier: 0.4 = 40% longer duration per Parry enchantment level""")
    @ConfigEntry.Gui.Tooltip
    public String maceParryEffects = "minecraft:slowness;160;1;1.0;0.3;" +
            "frycparry:disarmed;35;1;1.0;0.12";

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

    @Comment("""
            Values above 0 describe number of ticks item will be disabled for\s
            When this value is below zero, cooldown is based on attack speed and the value is multiplier\s
            (For example, if this value is set to -0.5, cooldown will be: valueBasedOnAttackSpeed * 0.5)\s
            """)
    @ConfigEntry.Gui.Tooltip
    public float cooldownAfterAttack = -0.90F;

    public boolean shouldStopUsingMaceAfterBlockOrParry = true;
    public int maxUseTime = 7200;

    @Comment("""
            Parry ticks start counting AFTER block delay\s
            For example, when block delay is 5, and parry ticks is 2, 6th and 7th tick counts as parry
            """)
    @ConfigEntry.Gui.Tooltip
    public int blockDelay = 2;

    @Comment("""
            When this value is below 0, explosions can't be blocked\s
            0 means no delay (but normal block delay is still taken into account)\s
            If normal block delay is higher than this value (and this value is higher than -1), then this value does nothing
            If normal block delay is 0 and this value is 5, first 5 ticks of blocking grants 80% less protection from explosions
            """)
    @ConfigEntry.Gui.Tooltip
    public int explosionBlockDelay = 5;

    @Comment("""
            % of damage TAKEN (not reduced) after blocking explosion: the higher this value, the more damage player takes when blocking\s
            This option doesn't do anything unless explosionBlockDelay is above -1
            """)
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int explosionBlockDamageTaken = 75;
}
