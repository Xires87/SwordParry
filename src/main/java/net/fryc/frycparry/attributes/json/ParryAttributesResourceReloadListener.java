package net.fryc.frycparry.attributes.json;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.InputStream;

public class ParryAttributesResourceReloadListener implements SimpleSynchronousResourceReloadListener {

    private static final String PARRY_ATTRIBUTES_PATH = "parry_attributes";

    @Override
    public Identifier getFabricId() {
        return Identifier.of(FrycParry.MOD_ID, PARRY_ATTRIBUTES_PATH);
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
                float cooldownAfterParryAction = JsonHelper.getFloat(jsonObject, "cooldownAfterParryAction");
                float cooldownAfterInterruptingBlockAction = JsonHelper.getFloat(jsonObject, "cooldownAfterInterruptingBlockAction");

                int maxUseTime = JsonHelper.getInt(jsonObject, "maxUseTime", 7200);
                boolean shouldStopUsingItemAfterBlockOrParry = JsonHelper.getBoolean(jsonObject, "shouldStopUsingItemAfterBlockOrParry", true);
                double knockbackAfterParryAction = JsonHelper.getDouble(jsonObject, "knockbackAfterParryAction", 4.0);
                int slownessAfterParryAction = JsonHelper.getInt(jsonObject, "slownessAfterParryAction", 60);
                int slownessAmplifierAfterParryAction = JsonHelper.getInt(jsonObject, "slownessAmplifierAfterParryAction", 1);
                int weaknessAfterParryAction = JsonHelper.getInt(jsonObject, "weaknessAfterParryAction", 0);
                int weaknessAmplifierAfterParryAction = JsonHelper.getInt(jsonObject, "weaknessAmplifierAfterParryAction", 1);
                int disarmedAfterParryAction = JsonHelper.getInt(jsonObject, "disarmedAfterParryAction", 20);

                ParryAttributes.create(fileName, parryTicks, meleeDamageTakenAfterBlock, projectileDamageTakenAfterBlock,
                        cooldownAfterParryAction, cooldownAfterInterruptingBlockAction, maxUseTime, shouldStopUsingItemAfterBlockOrParry,
                        knockbackAfterParryAction, slownessAfterParryAction, slownessAmplifierAfterParryAction, weaknessAfterParryAction,
                        weaknessAmplifierAfterParryAction, disarmedAfterParryAction);

            } catch(Exception e) {
                FrycParry.LOGGER.error("Error occurred while loading resource json" + id.toString(), e);
            }
        }
    }
}
