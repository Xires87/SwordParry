package net.fryc.frycparry.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static Identifier PARRYING_SOUND_ID = new Identifier("frycparry", "parrying");
    public static SoundEvent PARRYING_SOUND_EVENT = SoundEvent.of(PARRYING_SOUND_ID);

    public static void registerSounds() {
        Registry.register(Registries.SOUND_EVENT, PARRYING_SOUND_ID, PARRYING_SOUND_EVENT);
    }
}
