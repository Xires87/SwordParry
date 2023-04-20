package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

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
    @ConfigEntry.Category("sword")
    public boolean enableBlockingWithSword = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("sword")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int swordParryTicks = 4;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("sword")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int swordBlockMeleeDamageTaken = 50;

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
    @ConfigEntry.Category("axe")
    public boolean enableBlockingWithAxe = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("axe")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int axeParryTicks = 4;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("axe")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int axeBlockMeleeDamageTaken = 43;

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
    @ConfigEntry.Category("shovel")
    public boolean enableBlockingWithShovel = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("shovel")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int shovelParryTicks = 2;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("shovel")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int shovelBlockMeleeDamageTaken = 71;

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
    @ConfigEntry.Category("hoe")
    public boolean enableBlockingWithHoe = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("hoe")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int hoeParryTicks = 1;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("hoe")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int hoeBlockMeleeDamageTaken = 85;

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
    @ConfigEntry.Category("pickaxe")
    public boolean enableBlockingWithPickaxe = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("pickaxe")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 0)
    public int pickaxeParryTicks = 0;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("pickaxe")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int pickaxeBlockMeleeDamageTaken = 80;

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
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("multiplayer")
    public int parryKnockbackStrengthForPlayersModifier = 7;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int weaknessForPlayersAfterParryModifier = 0;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int weaknessForPlayersAmplifierModifier = 0;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int slownessForPlayersAfterParryModifier = 0;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int slownessForPlayersAmplifierModifier = 0;



    //global settings
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("global")
    public boolean disableBlockAfterParryingAxeAttack = false;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("global")
    public boolean enchantmentsForShieldsCanAppearOnWeaponsInChests = false;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("global")
    @ConfigEntry.BoundedDiscrete(max = 2, min = 0)
    public int enableBlockingWhenDualWielding = 0;

}
