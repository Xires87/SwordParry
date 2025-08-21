package net.fryc.frycparry.util;

import com.google.gson.JsonSyntaxException;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.network.ModPackets;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import oshi.util.tuples.Quartet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConfigHelper {
    public static int dualWieldingSettings = FrycParry.config.server.enableBlockingWhenDualWielding;
    public static boolean enableBlockingWithSword = FrycParry.config.sword.enableBlockingWithSword;
    public static boolean enableBlockingWithAxe = FrycParry.config.axe.enableBlockingWithAxe;
    public static boolean enableBlockingWithPickaxe = FrycParry.config.pickaxe.enableBlockingWithPickaxe;
    public static boolean enableBlockingWithShovel = FrycParry.config.shovel.enableBlockingWithShovel;
    public static boolean enableBlockingWithHoe = FrycParry.config.hoe.enableBlockingWithHoe;
    public static boolean enableBlockingWithOtherTools = FrycParry.config.server.enableBlockingWithOtherTools;

    public static boolean enableReflexEnchantment = FrycParry.config.enchantments.enableReflexEnchantment;
    public static boolean enableParryEnchantment = FrycParry.config.enchantments.enableParryEnchantment;
    public static boolean enableCounterattackEnchantment = FrycParry.config.enchantments.enableCounterattackEnchantment;
    public static int shieldEnchantability = FrycParry.config.enchantments.shieldEnchantability;

    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> pickaxeParryEffects;
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> axeParryEffects;
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> swordParryEffects;
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> shovelParryEffects;
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> hoeParryEffects;
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> shieldParryEffects;
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> parryEffects;

    public static void reloadDefaultParryEffects(){
        pickaxeParryEffects = transformStringToMap(FrycParry.config.pickaxe.pickaxeParryEffects);
        axeParryEffects = transformStringToMap(FrycParry.config.axe.axeParryEffects);
        swordParryEffects = transformStringToMap(FrycParry.config.sword.swordParryEffects);
        shovelParryEffects = transformStringToMap(FrycParry.config.shovel.shovelParryEffects);
        hoeParryEffects = transformStringToMap(FrycParry.config.hoe.hoeParryEffects);
        shieldParryEffects = transformStringToMap(FrycParry.config.shield.shieldParryEffects);
        parryEffects = transformStringToMap(FrycParry.config.server.parryEffects);
    }

    /**
     *
     * @param stringsAndNumbers string containing strings and numbers separated with semicolon in the following pattern: String;int;int;float;float;String;int;int;float;float
     * @return map containing StatusEffect as a key and quartet of the next 2 ints and 2 floats as a value
     */
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> transformStringToMap(String stringsAndNumbers){
        HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> map = new HashMap<>();
        if(stringsAndNumbers.isEmpty()) return map;

        Iterator<String> iterator = Arrays.stream(stringsAndNumbers.split(";", 0)).iterator();
        int i = 0;
        StatusEffect key = StatusEffects.SLOWNESS;
        int a = 0;
        int b = 0;
        float c = 0.0f;
        float d = 0.0f;
        while(iterator.hasNext()){
            String string = iterator.next();
            switch (i){
                case 0 -> {
                    key = (StatusEffect) Registries.STATUS_EFFECT.getOrEmpty(new Identifier(string)).orElseThrow(
                            () -> new JsonSyntaxException("Error occurred while loading default parry effects from config. The following Status Effect: " + string + " could not be found")
                    );
                }
                case 1 -> {
                    try {
                        a = Integer.parseInt(string);
                    }
                    catch (Exception e){
                        FrycParry.LOGGER.error("Given String could not be parsed to int", e);
                    }
                }
                case 2 ->  {
                    try {
                        b = Integer.parseInt(string);
                    }
                    catch (Exception e){
                        FrycParry.LOGGER.error("Given String could not be parsed to int", e);
                    }
                }
                case 3 ->  {
                    try {
                        c = Float.parseFloat(string);
                    }
                    catch (Exception e){
                        FrycParry.LOGGER.error("Given String could not be parsed to float", e);
                    }
                }
                case 4 ->  {
                    try {
                        d = Float.parseFloat(string);
                    }
                    catch (Exception e){
                        FrycParry.LOGGER.error("Given String could not be parsed to float", e);
                    }
                }
                default -> {
                    map.put(key, new Quartet<>(a, b, c, d));
                    key = (StatusEffect) Registries.STATUS_EFFECT.getOrEmpty(new Identifier(string)).orElseThrow(
                            () -> new JsonSyntaxException("Error occurred while loading default parry effects from config. The following Status Effect: " + string + " could not be found")
                    );
                    i = 0;
                }
            }
            i++;
        }
        if(i == 5){
            map.put(key, new Quartet<>(a, b, c, d));
        }

        return map;
    }

    public static void sendConfigToClient(ServerPlayerEntity client){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(FrycParry.config.server.enableBlockingWhenDualWielding);
        buf.writeBoolean(FrycParry.config.sword.enableBlockingWithSword);
        buf.writeBoolean(FrycParry.config.axe.enableBlockingWithAxe);
        buf.writeBoolean(FrycParry.config.pickaxe.enableBlockingWithPickaxe);
        buf.writeBoolean(FrycParry.config.shovel.enableBlockingWithShovel);
        buf.writeBoolean(FrycParry.config.hoe.enableBlockingWithHoe);
        buf.writeBoolean(FrycParry.config.server.enableBlockingWithOtherTools);

        buf.writeBoolean(FrycParry.config.enchantments.enableReflexEnchantment);
        buf.writeBoolean(FrycParry.config.enchantments.enableParryEnchantment);
        buf.writeBoolean(FrycParry.config.enchantments.enableCounterattackEnchantment);
        buf.writeInt(FrycParry.config.enchantments.shieldEnchantability);

        ServerPlayNetworking.send(client, ModPackets.ANSWER_CONFIG_ID, buf); // <--- informs client about server's config to avoid visual bugs
    }

    public static void sendParryAttributesToClient(ServerPlayerEntity client){
        Map<String, ParryAttributes> map = ParryAttributes.getRegisteredAttributesCopy();
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeMap(map, PacketByteBuf::writeString, ParryAttributes.PACKET_WRITER);

        ServerPlayNetworking.send(client, ModPackets.ANSWER_PARRY_ATTRIBUTES_ID, buf);
    }

    public static void applyParryAttributesOnClient(ServerPlayerEntity client){
        HashMap<Identifier, String> parryAttributesForItems = new HashMap<>();
        Registries.ITEM.forEach(item -> {
            parryAttributesForItems.put(Registries.ITEM.getId(item), ((ParryItem) item).getParryAttributes().getId());
        });

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeMap(parryAttributesForItems, PacketByteBuf::writeIdentifier, PacketByteBuf::writeString);

        ServerPlayNetworking.send(client, ModPackets.ANSWER_APPLY_PARRY_ATTRIBUTES_ID, buf);
    }
}
