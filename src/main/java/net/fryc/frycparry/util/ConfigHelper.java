package net.fryc.frycparry.util;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.effect.StatusEffect;
import oshi.util.tuples.Quartet;

import java.util.HashMap;

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

    public static final HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> pickaxeParryEffects = new HashMap<>();
    public static final HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> axeParryEffects = new HashMap<>();
    public static final HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> swordParryEffects = new HashMap<>();
    public static final HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> shovelParryEffects = new HashMap<>();
    public static final HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> hoeParryEffects = new HashMap<>();
    public static final HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> shieldParryEffects = new HashMap<>();
    public static final HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> parryEffects = new HashMap<>();

    public static void reloadDefaultParryEffects(){

    }
}
