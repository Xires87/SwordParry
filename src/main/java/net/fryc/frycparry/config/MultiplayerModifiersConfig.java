package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "multiplayerModifiers")
public class MultiplayerModifiersConfig implements ConfigData {
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
}
