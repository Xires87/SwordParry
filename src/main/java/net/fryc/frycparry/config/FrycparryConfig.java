package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "frycparry")
public class FrycparryConfig implements ConfigData {

    //shield
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("shield")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int shieldParryTicks = 4;
    @ConfigEntry.Category("shield")
    public double shieldParryKnockbackStrength = 9;
    @ConfigEntry.Category("shield")
    public int shieldSlownessAfterParry = 100;
    @ConfigEntry.Category("shield")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int shieldSlownessAfterParryAmplifier = 1;
    @ConfigEntry.Category("shield")
    public int shieldWeaknessAfterParry = 0;
    @ConfigEntry.Category("shield")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int shieldWeaknessAfterParryAmplifier = 1;
    @ConfigEntry.Category("shield")
    public int shieldDisarmAfterParry = 45;

    @ConfigEntry.Category("shield")
    public int cooldownAfterInterruptingShieldBlockAction = 26;
    @ConfigEntry.Category("shield")
    public int cooldownAfterShieldParryAction = 16;


    //sword
    @Comment("Sword")
    @ConfigEntry.Category("sword")
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBlockingWithSword = true;


    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("sword")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int swordParryTicks = 4;

    @Comment("% of damage TAKEN (not reduced) after blocking melee attack: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("sword")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int swordBlockMeleeDamageTaken = 50;

    @Comment("% of damage TAKEN (not reduced) after blocking projectile: the higher this value, the more damage player takes when blocking")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("sword")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int swordBlockArrowDamageTaken = 90;

    @ConfigEntry.Category("sword")
    public double swordParryKnockbackStrength = 6;

    @ConfigEntry.Category("sword")
    public int swordSlownessAfterParry = 100;

    @ConfigEntry.Category("sword")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int swordSlownessAfterParryAmplifier = 1;

    @ConfigEntry.Category("sword")
    public int swordWeaknessAfterParry = 0;

    @ConfigEntry.Category("sword")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int swordWeaknessAfterParryAmplifier = 1;

    @ConfigEntry.Category("sword")
    public int swordDisarmAfterParry = 45;

    @ConfigEntry.Category("sword")
    public int cooldownAfterInterruptingSwordBlockAction = 20;
    @ConfigEntry.Category("sword")
    public int cooldownAfterSwordParryAction = 12;

    //axe
    @Comment("Axe")
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


    //shovel
    @Comment("Shovel")
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


    //hoe
    @Comment("Hoe")
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


    //pickaxe
    @Comment("Pickaxe")
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


    //multiplayer modifiers
    @Comment("Example: when set to 3, and knockback strength of active item is 9, knockback strength for parried player will be 6 (9 - 3 = 6)")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("multiplayer")
    public int parryKnockbackStrengthForPlayersModifier = 7;
    @Comment("Example: when set to 30, and weakness duration of active item is 100, weakness duration for parried player will be 70 (100 - 30 = 70)")
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int weaknessForPlayersAfterParryModifier = 0;
    @Comment("Example: when set to 1, and weakness amplifier of active item is 2, weakness amplifier for parried player will be 1 (2 - 1 = 1)")
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int weaknessForPlayersAmplifierModifier = 0;
    @Comment("Example: when set to -30, and slowness duration of active item is 100, slowness duration for parried player will be 130 (100 - (-30) = 130)")
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int slownessForPlayersAfterParryModifier = 0;
    @Comment("Example: when set to -1, and slowness amplifier of active item is 1, slowness amplifier for parried player will be 2 (1 - (-1) = 2)")
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int slownessForPlayersAmplifierModifier = 0;



    //global settings

    @ConfigEntry.Category("global")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableBlockingWithOtherTools = false;
    @Comment("When true, axe attack will set cooldown whether it was parry or not")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("global")
    public boolean disableBlockAfterParryingAxeAttack = false;

    @Comment("This option doesn't do anything when blocking with axes and swords is disabled")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("global")
    public boolean enchantmentsForShieldsCanAppearOnWeaponsInChests = false;

    @Comment("Dual wielding settings: 0 - player can block and parry with empty offhand;  1 - player can also block and parry when dual wields weapons; 2 - player can block and parry with any item in offhand;")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("global")
    @ConfigEntry.BoundedDiscrete(max = 2, min = 0)
    @ConfigEntry.Gui.RequiresRestart
    public int enableBlockingWhenDualWielding = 0;

}
