package net.fryc.frycparry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fryc.frycparry.config.FrycparryConfig;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.network.ModPackets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrycParry implements ModInitializer {

    public static final String MOD_ID = "frycparry";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static FrycparryConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(FrycparryConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(FrycparryConfig.class).getConfig();

        ModPackets.registerC2SPackets();
        ModEffects.registerEffects();
        ModEnchantments.registerModEnchantments();

        /*
        todo zrobic networking zeby dzialalo na serwerze
         */
    }
}
