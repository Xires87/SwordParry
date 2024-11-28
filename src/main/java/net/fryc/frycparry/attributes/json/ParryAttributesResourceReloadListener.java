package net.fryc.frycparry.attributes.json;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.util.FrycJsonHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import oshi.util.tuples.Quartet;

import java.io.InputStream;
import java.util.HashMap;

public class ParryAttributesResourceReloadListener implements SimpleSynchronousResourceReloadListener {

    private static final String PARRY_ATTRIBUTES_PATH = "parry_attributes";

    @Override
    public Identifier getFabricId() {
        return new Identifier(FrycParry.MOD_ID, PARRY_ATTRIBUTES_PATH);
    }

    @Override
    public void reload(ResourceManager manager) {
        for(Identifier id : manager.findResources(PARRY_ATTRIBUTES_PATH, path -> path.getPath().endsWith(".json")).keySet()) {
            try(InputStream stream = manager.getResource(id).get().getInputStream()) {
                JsonObject jsonObject = JsonParser.parseString(new String(stream.readAllBytes())).getAsJsonObject();

                String fileName = id.getPath().substring(17, id.getPath().length() - 5);

                int parryTicks = JsonHelper.getInt(jsonObject, "parryTicks");
                float meleeDamageTakenAfterBlock = JsonHelper.getFloat(jsonObject, "meleeDamageTakenAfterBlock");
                float projectileDamageTakenAfterBlock = JsonHelper.getFloat(jsonObject, "projectileDamageTakenAfterBlock");
                float explosionDamageTakenAfterBlock = JsonHelper.getFloat(jsonObject, "explosionDamageTakenAfterBlock");
                float cooldownAfterParryAction = JsonHelper.getFloat(jsonObject, "cooldownAfterParryAction");
                float cooldownAfterAttack = JsonHelper.getFloat(jsonObject, "cooldownAfterAttack");
                float cooldownAfterInterruptingBlockAction = JsonHelper.getFloat(jsonObject, "cooldownAfterInterruptingBlockAction");

                int maxUseTime = JsonHelper.getInt(jsonObject, "maxUseTime", 7200);
                int blockDelay = JsonHelper.getInt(jsonObject, "blockDelay", 0);
                int explosionBlockDelay = JsonHelper.getInt(jsonObject, "explosionBlockDelay", -1);
                boolean shouldStopUsingItemAfterBlockOrParry = JsonHelper.getBoolean(jsonObject, "shouldStopUsingItemAfterBlockOrParry", true);
                double knockbackAfterParryAction = JsonHelper.getDouble(jsonObject, "knockbackAfterParryAction", 4.0);

                HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> effectMap = new HashMap<>();
                JsonArray array = JsonHelper.getArray(jsonObject, "parryEffects");
                for(JsonElement element : array){
                    try{
                        JsonObject effectObject = element.getAsJsonObject();
                        StatusEffect effect = FrycJsonHelper.getStatusEffect(effectObject, "statusEffect");
                        int duration = JsonHelper.getInt(effectObject, "duration", 100);
                        int amplifier = JsonHelper.getInt(effectObject, "amplifier", 1);
                        float chance = JsonHelper.getFloat(effectObject, "chance", 1.0f);
                        float enchantmentMultiplier = JsonHelper.getFloat(effectObject, "enchantmentMultiplier", 0.3f);
                        effectMap.put(effect, new Quartet<>(duration, amplifier, chance, enchantmentMultiplier));
                    }
                    catch (Exception e){
                        FrycParry.LOGGER.error("Error occurred while loading parry effects from the following file: " + fileName, e);
                    }
                }

                ParryAttributes.create(fileName, parryTicks, meleeDamageTakenAfterBlock, projectileDamageTakenAfterBlock,
                        explosionDamageTakenAfterBlock,
                        cooldownAfterParryAction, cooldownAfterInterruptingBlockAction, cooldownAfterAttack, maxUseTime,
                        blockDelay, explosionBlockDelay, shouldStopUsingItemAfterBlockOrParry, knockbackAfterParryAction, effectMap);


            } catch(Exception e) {
                FrycParry.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }
    }
}
