package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name="client")
public class FrycparryClientConfig implements ConfigData {

    @Comment("When false, dontUseParryKey is toggleable (press it to disable blocking and parrying, and press again to enable)")
    public boolean holdDontUseParryKey = false;

    @Comment("Rotation, translation and scale of items when blocking")
    @ConfigEntry.Gui.Tooltip
    public float itemRotationX = 121f;
    @ConfigEntry.Gui.Tooltip
    public float itemRotationY = -83f;
    @ConfigEntry.Gui.Tooltip
    public float itemRotationZ = 185f;
    @ConfigEntry.Gui.Tooltip
    public float itemTranslationX = 1.0f;
    @ConfigEntry.Gui.Tooltip
    public float itemTranslationY = -0.2f;
    @ConfigEntry.Gui.Tooltip
    public float itemTranslationZ = -5f;
    @ConfigEntry.Gui.Tooltip
    public float itemScaleX = 1.0f;
    @ConfigEntry.Gui.Tooltip
    public float itemScaleY = 1.0f;
    @ConfigEntry.Gui.Tooltip
    public float itemScaleZ = 1.0f;

    @Comment("If you play singleplayer, use server sided config (axe.json5, sword.json5 etc.) for options under this comment. " +
            "These options only disable keybind (game behaves like you have never pressed parry key if you have a disabled item in your mainhand)." +
            "You will still get cooldowns after swapping or using disabled items")
    public boolean enableBlockingWithSword = true;
    public boolean enableBlockingWithAxe = true;
    public boolean enableBlockingWithHoe = true;
    public boolean enableBlockingWithShovel = true;
    public boolean enableBlockingWithPickaxe = true;
    public boolean enableBlockingWithOtherTools = true;

}
