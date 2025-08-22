package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.fryc.frycparry.util.ConfigHelper;

@Config(name="client")
public class FrycparryClientConfig implements ConfigData {

    @Comment("Negative values move icon left, and positive - right")
    @ConfigEntry.Gui.Tooltip
    public int blockCooldownIconX = 0;

    @Comment("Negative values move icon up, and positive - down")
    @ConfigEntry.Gui.Tooltip
    public int blockCooldownIconY = 23;

    @Comment("Possible values: CENTER, DOWN, RIGHT_DOWN, LEFT_DOWN")
    @ConfigEntry.Gui.NoTooltip
    public ConfigHelper.HudIconPosition blockCooldownIconStartingPosition = ConfigHelper.HudIconPosition.CENTER;

    @Comment("When true, shows colored shield when blocking: \n" +
            "Red - block delay (you will take full hit); \n" +
            "Green - attack will be parried; \n" +
            "Yellow - attack will be blocked;")
    @ConfigEntry.Gui.Tooltip
    public boolean showParryIndicator = false;

    public boolean showParryAttributesInTooltips = true;

    @Comment("When false, dontUseParryKey is toggleable (press it to disable blocking and parrying, and press again to enable)")
    @ConfigEntry.Gui.NoTooltip
    public boolean holdDontUseParryKey = false;

    @Comment("Rotation, translation and scale of items when blocking")
    @ConfigEntry.Gui.NoTooltip
    public boolean useOriginalRotation = false;

    @ConfigEntry.Gui.Tooltip
    public float itemRotationX = 121f;
    @ConfigEntry.Gui.Tooltip
    public float itemRotationY = -83f;
    @ConfigEntry.Gui.Tooltip
    public float itemRotationZ = 185f;

    public boolean useOriginalTranslation = false;
    @ConfigEntry.Gui.Tooltip
    public float itemTranslationX = -1.5f;
    @ConfigEntry.Gui.Tooltip
    public float itemTranslationY = 2.2f;
    @ConfigEntry.Gui.Tooltip
    public float itemTranslationZ = -1f;

    public boolean useOriginalScale = true;
    @ConfigEntry.Gui.Tooltip
    public float itemScaleX = 0.68f;
    @ConfigEntry.Gui.Tooltip
    public float itemScaleY = 0.68f;
    @ConfigEntry.Gui.Tooltip
    public float itemScaleZ = 0.68f;

    @Comment("If you play singleplayer, use server sided config (axe.json5, sword.json5 etc.) for options under this comment. " +
            "These options only disable keybind (game behaves like you have never pressed parry key if you have a disabled item in your mainhand)." +
            "You will still get cooldowns after swapping disabled items")
    @ConfigEntry.Gui.NoTooltip
    public boolean enableBlockingWithSword = true;
    public boolean enableBlockingWithAxe = true;
    public boolean enableBlockingWithHoe = true;
    public boolean enableBlockingWithShovel = true;
    public boolean enableBlockingWithPickaxe = true;
    public boolean enableBlockingWithOtherTools = true;

}
