package net.fryc.frycparry.tag;

import net.fryc.frycparry.FrycParry;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class ModItemTags {

    public static final TagKey<Item> PARRYING_EXCLUDED_ITEMS = ModItemTags.register("parrying_excluded_items");
    public static final TagKey<Item> ITEMS_CAN_PARRY = ModItemTags.register("items_can_parry");

    public static final TagKey<Item> ENCHANTMENT_PARRY_TOOLS = ModItemTags.register("enchantable/parry_tools");
    public static final TagKey<Item> ENCHANTMENT_SHIELDS = ModItemTags.register("enchantable/shield");


    private ModItemTags() {
    }

    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(FrycParry.MOD_ID, id));
    }
}
