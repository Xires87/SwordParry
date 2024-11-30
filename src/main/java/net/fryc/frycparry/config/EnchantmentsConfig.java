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

    @Comment("""
    Increases amplifier of parry effects by this value when item has Parry II\s
    If you want to strengthen only some of the effects, decrease amplifier of the effect you don't want to be strengthened:\s
    For example, if this option is set to 2, and parrying applies instant damage I, setting instant damage's amplifier to -1 will \s
    prevent parry enchantment from increasing the amplifier (still instant damage I will be applied)
    """)
    @ConfigEntry.Gui.Tooltip
    public int parryEnchantmentAmplifierIncreaseOnMaxLevel = 0;
}
