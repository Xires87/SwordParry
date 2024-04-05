package net.fryc.frycparry.tag;

import net.fryc.frycparry.FrycParry;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class ModItemTags {

    public static final TagKey<Item> PARRYING_EXCLUDED_ITEMS = ModItemTags.register("parrying_excluded_items");
    public static final TagKey<Item> ITEMS_CAN_PARRY = ModItemTags.register("items_can_parry");


    private ModItemTags() {
    }

    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(FrycParry.MOD_ID, id));
    }
}
