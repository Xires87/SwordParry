package net.fryc.frycparry.sounds;

import net.fryc.frycparry.FrycParry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent TOOL_PARRY = registerSound("tool_parry");
    public static final SoundEvent SHIELD_PARRY = registerSound("shield_parry");

    public static final SoundEvent TOOL_BLOCK = registerSound("tool_block");
    public static final SoundEvent SHIELD_BLOCK = registerSound("shield_block");

    public static final SoundEvent TOOL_GUARD_BREAK = registerSound("tool_guard_break");
    public static final SoundEvent SHIELD_GUARD_BREAK = registerSound("shield_guard_break");


    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(FrycParry.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }


    public static void registerModSounds(){
    }


    private ModSounds(){
    }
}
