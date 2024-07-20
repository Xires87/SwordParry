package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "enchantments")
public class EnchantmentsConfig implements ConfigData {

    @Comment("Set it to 0 to make shield unenchantable")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.RequiresRestart
    public int shieldEnchantability = 12;

    @Comment("Disabled enchantments are unobtainable in survival mode")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableReflexEnchantment = true;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableParryEnchantment = true;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.RequiresRestart
    public boolean enableCounterattackEnchantment = true;

    @Comment("This option doesn't do anything when blocking with axes and swords is disabled (or enchantments are disabled)")
    @ConfigEntry.Gui.Tooltip
    public boolean enchantmentsForShieldsCanAppearOnWeaponsInChests = false;
}
