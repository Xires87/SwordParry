package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "frycparry")
public class FrycparryConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("parry")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 1)
    public int shieldParryTicks = 5;

    @ConfigEntry.Category("parry")
    public boolean enableBlockingWithSword = true;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("parry")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 1)
    public int swordParryTicks = 5;

    @ConfigEntry.Category("parry")
    public boolean enableBlockingWithAxe = true;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("parry")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 1)
    public int axeParryTicks = 5;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("parry_effects")
    @ConfigEntry.BoundedDiscrete(max = 20, min = 1)
    public int parryKnockbackStrength = 9;

    @ConfigEntry.Category("parry_effects")
    @ConfigEntry.Gui.Tooltip
    public int slownessAfterParry = 100;
    @ConfigEntry.Category("parry_effects")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 1)
    public int slownessAmplifier = 1;
    @ConfigEntry.Category("parry_effects")
    @ConfigEntry.Gui.Tooltip
    public int weaknessAfterParry = 0;
    @ConfigEntry.Category("parry_effects")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 1)
    public int weaknessAmplifier = 1;


    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.BoundedDiscrete(max = 20, min = 1)
    public int parryKnockbackStrengthForPlayers = 2;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int disarmForPlayersAfterParry = 45;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int weaknessForPlayersAfterParry = 0;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 1)
    public int weaknessForPlayersAmplifier = 1;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int slownessForPlayersAfterParry = 100;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100, min = 1)
    public int slownessForPlayersAmplifier = 1;


    @ConfigEntry.Category("block")
    public boolean interruptSwordBlockActionAfterParryOrBlock = true;

    @ConfigEntry.Category("block")
    public boolean interruptAxeBlockActionAfterParryOrBlock = true;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("block")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int swordBlockMeleeDamageTaken = 50;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("block")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int swordBlockArrowDamageTaken = 90;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("block")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int axeBlockMeleeDamageTaken = 40;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("block")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int axeBlockArrowDamageTaken = 85;


    @ConfigEntry.Category("cooldown")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int cooldownAfterBlockAction = 20;

    @ConfigEntry.Category("cooldown")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int cooldownForBlockAfterSwordAttack = 12;
    @ConfigEntry.Category("cooldown")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int cooldownForBlockAfterAxeAttack = 15;

}
