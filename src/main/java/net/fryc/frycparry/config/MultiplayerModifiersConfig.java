package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "multiplayerModifiers")
public class MultiplayerModifiersConfig implements ConfigData {
    @Comment("Example: when set to 3, and knockback strength of active item is 9, knockback strength for parried player will be 6 (9 - 3 = 6)")
    @ConfigEntry.Gui.Tooltip
    public int parryKnockbackStrengthForPlayersModifier = 7;

}
