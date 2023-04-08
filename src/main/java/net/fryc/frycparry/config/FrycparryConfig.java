package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "frycparry")
public class FrycparryConfig implements ConfigData {

    //shield
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("shield")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 1)
    public int shieldParryTicks = 4;

    @ConfigEntry.Category("shield")
    public int cooldownAfterShieldParryAction = 18;

    // todo reszta opcji configu (i opcja do automatycznego wylaczania blokowania tarcza kiedy sie sparuje)

    //sword
    @ConfigEntry.Category("sword")
    public boolean enableBlockingWithSword = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("sword")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 1)
    public int swordParryTicks = 4;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("sword")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int swordBlockMeleeDamageTaken = 50;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("sword")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int swordBlockArrowDamageTaken = 90;

    @ConfigEntry.Category("sword")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int cooldownAfterSwordParryAction = 12;

    //axe
    @ConfigEntry.Category("axe")
    public boolean enableBlockingWithAxe = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("axe")
    @ConfigEntry.BoundedDiscrete(max = 30, min = 1)
    public int axeParryTicks = 4;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("axe")
    public boolean disableBlockAfterParryingAxeAttack = false;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("axe")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int axeBlockMeleeDamageTaken = 43;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("axe")
    @ConfigEntry.BoundedDiscrete(max = 99, min = 1)
    public int axeBlockArrowDamageTaken = 85;

    @ConfigEntry.Category("axe")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int cooldownAfterAxeParryAction = 15;


    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("parry")
    @ConfigEntry.BoundedDiscrete(max = 2, min = 0)
    public int enableBlockingWhenDualWielding = 0;

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
    @ConfigEntry.BoundedDiscrete(max = 20, min = -20)
    public int parryKnockbackStrengthForPlayers = 2;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int disarmForPlayersAfterParry = 45;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int weaknessForPlayersAfterParry = 0;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 20, min = -20)
    public int weaknessForPlayersAmplifier = 1;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    public int slownessForPlayersAfterParry = 100;
    @ConfigEntry.Category("multiplayer")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 20, min = -20)
    public int slownessForPlayersAmplifier = 1;







    @ConfigEntry.Category("cooldown")
    @ConfigEntry.BoundedDiscrete(max = 100, min = 0)
    public int cooldownAfterInterruptingBlockAction = 24;




}
