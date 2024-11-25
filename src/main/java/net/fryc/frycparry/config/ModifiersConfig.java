package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "modifiers")
public class ModifiersConfig implements ConfigData {
    @Comment("Knockback strength is multiplied by this value for PvP")
    @ConfigEntry.Gui.Tooltip
    public float parryKnockbackStrengthForPlayersMultiplier = 0.25F;

    @Comment("Disarm duration is multiplied by this value for mobs")
    @ConfigEntry.Gui.Tooltip
    public float parryDisarmDurationForMobsMultiplier = 0.35F;

}
