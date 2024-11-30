package net.fryc.frycparry.tag;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModEntityTypeTags {

    public static final TagKey<EntityType<?>> DISARM_RESISTANT_MOBS = ModEntityTypeTags.of("disarm_resistant_mobs");

    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(FrycParry.MOD_ID, id));
    }

    private ModEntityTypeTags() {
    }

}
