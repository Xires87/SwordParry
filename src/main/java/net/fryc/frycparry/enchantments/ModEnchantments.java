package net.fryc.frycparry.enchantments;

import net.fryc.frycparry.FrycParry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final EquipmentSlot[] HANDS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

    public static Enchantment PREDICTION = register("prediction_enchantment",
            new PredictionEnchantment(Enchantment.Rarity.COMMON, EnchantmentTarget.BREAKABLE, HANDS));


    public static Enchantment COUNTERATTACK = register("counterattack_enchantment",
            new CounterAttackEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.BREAKABLE, HANDS));

    public static Enchantment PARRY = register("parry_enchantment",
            new ParryEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.BREAKABLE, HANDS));


    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(FrycParry.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments(){
    }

    public static int getParryEnchantment(LivingEntity entity){
        return EnchantmentHelper.getEquipmentLevel(ModEnchantments.PARRY, entity);
    }

    public static int getPredictionEnchantment(LivingEntity entity){
        return EnchantmentHelper.getEquipmentLevel(ModEnchantments.PREDICTION, entity);
    }

    public static int getCounterattackEnchantment(LivingEntity entity){
        return EnchantmentHelper.getEquipmentLevel(ModEnchantments.COUNTERATTACK, entity);
    }
}
