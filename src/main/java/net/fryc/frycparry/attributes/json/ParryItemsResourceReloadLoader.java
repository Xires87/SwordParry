package net.fryc.frycparry.attributes.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.util.ConfigHelper;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.InputStream;


public class ParryItemsResourceReloadLoader implements SimpleSynchronousResourceReloadListener {
    private static final String PARRY_ITEMS_PATH = "parry_items";

    @Override
    public Identifier getFabricId() {
        return new Identifier(FrycParry.MOD_ID, PARRY_ITEMS_PATH);
    }

    @Override
    public void reload(ResourceManager manager) {
        // setting default parry attributes for all items
        ConfigHelper.reloadDefaultParryEffects();
        for(Item item : Registries.ITEM){
            ((ParryItem) item).setParryAttributes(ParryAttributes.getDefaultParryAttributes(item));
        }

        // setting parry attributes from datapacks
        for(Identifier id : manager.findResources(PARRY_ITEMS_PATH, path -> path.getPath().endsWith(".json")).keySet()) {
            try(InputStream stream = manager.getResource(id).get().getInputStream()) {
                JsonObject jsonObject = JsonParser.parseString(new String(stream.readAllBytes())).getAsJsonObject();
                String parryAttributesId = JsonHelper.getString(jsonObject, "parryAttributes");
                JsonArray array = JsonHelper.getArray(jsonObject, "items");
                for (JsonElement jsonElement : array) {
                    ((ParryItem) JsonHelper.asItem(jsonElement, "itemFromJsonArray")).setParryAttributes(parryAttributesId);
                }

            } catch(Exception e) {
                FrycParry.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }
    }
}
