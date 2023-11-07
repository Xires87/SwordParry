package net.fryc.frycparry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@Config(name="frycparryClient")
public class FrycparryClientConfig implements ConfigData {

    @Comment("When false, dontUseParryKey is toggleable (press it to disable blocking and parrying, and press again to enable)")
    public boolean holdDontUseParryKey = false;

    @Comment("If you play singleplayer, use server sided config (frycparry.json5) for options under this comment. " +
            "These options only disable keybind (game behaves like you have never pressed parry key if you have a disabled item in your mainhand)." +
            "You will still get cooldowns after swapping or using disabled items")
    public boolean enableBlockingWithSword = true;
    public boolean enableBlockingWithAxe = true;
    public boolean enableBlockingWithHoe = true;
    public boolean enableBlockingWithShovel = true;
    public boolean enableBlockingWithPickaxe = true;
    public boolean enableBlockingWithOtherTools = true;

}
