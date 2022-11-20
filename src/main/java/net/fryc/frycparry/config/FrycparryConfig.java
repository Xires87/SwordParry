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

    @ConfigEntry.Category("block")
    public boolean interruptSwordBlockActionAfterParryOrBlock = true;

    @ConfigEntry.Category("block")
    public boolean interruptAxeBlockActionAfterParryOrBlock = true;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("block")
    @ConfigEntry.BoundedDiscrete(max = 9, min = 1)
    public int swordBlockMeleeDamageTaken = 5;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("block")
    @ConfigEntry.BoundedDiscrete(max = 9, min = 1)
    public int swordBlockArrowDamageTaken = 9;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("block")
    @ConfigEntry.BoundedDiscrete(max = 9, min = 1)
    public int axeBlockMeleeDamageTaken = 4;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("block")
    @ConfigEntry.BoundedDiscrete(max = 9, min = 1)
    public int axeBlockArrowDamageTaken = 8;

}
