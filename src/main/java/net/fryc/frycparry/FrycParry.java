package net.fryc.frycparry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fryc.frycparry.attributes.json.ParryAttributesResourceReloadListener;
import net.fryc.frycparry.attributes.json.ParryItemsResourceReloadLoader;
import net.fryc.frycparry.config.FrycParryConfig;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.network.ModPackets;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrycParry implements ModInitializer {

    public static final String MOD_ID = "frycparry";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static FrycParryConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(FrycParryConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        config = AutoConfig.getConfigHolder(FrycParryConfig.class).getConfig();

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ParryAttributesResourceReloadListener());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ParryItemsResourceReloadLoader());

        ModPackets.registerC2SPackets();
        ModEffects.registerEffects();
        ModEnchantments.registerModEnchantments();// todo translate do itemtagow i do macea

    }
}
