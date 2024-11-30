package net.fryc.frycparry.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class FrycJsonHelper {

    public static RegistryEntry<StatusEffect> asStatusEffect(JsonElement element, String name){
        if (element.isJsonPrimitive()) {
            String string = element.getAsString();
            return Registries.STATUS_EFFECT.getEntry(Identifier.of(string)).orElseThrow(() -> new JsonSyntaxException("Expected " + name + " to be a Status Effect, was unknown string '" + string + "'"));
        }
        throw new JsonSyntaxException("Expected " + name + " to be a Status Effect, was " + JsonHelper.getType(element));
    }

    public static RegistryEntry<StatusEffect> getStatusEffect(JsonObject jsonObject, String key){
        if (jsonObject.has(key)) {
            return FrycJsonHelper.asStatusEffect(jsonObject.get(key), key);
        }
        throw new JsonSyntaxException("Missing " + key + ", expected to find a Status Effect");
    }
}
