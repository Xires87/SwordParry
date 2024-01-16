package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "other")
public class OtherConfig implements ConfigData {

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
