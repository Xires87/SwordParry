package net.fryc.frycparry.enchantments;

import net.fryc.frycparry.FrycParry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEnchantments {

    public static final RegistryKey<Enchantment> REFLEX = of("reflex");


    public static final RegistryKey<Enchantment> COUNTERATTACK = of("counterattack");

    public static final RegistryKey<Enchantment> PARRY = of("parry");


    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(FrycParry.MOD_ID, id));
    }

    public static void registerModEnchantments(){
    }

    public static int getParryEnchantment(LivingEntity entity){
        return EnchantmentHelper.getEquipmentLevel(entity.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(PARRY), entity);
    }

    public static int getReflexEnchantment(LivingEntity entity){
        return EnchantmentHelper.getEquipmentLevel(entity.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(REFLEX), entity);
    }

    public static int getCounterattackEnchantment(LivingEntity entity){
        return EnchantmentHelper.getEquipmentLevel(entity.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(COUNTERATTACK), entity);
    }

    public static int getKnockbackEnchantment(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(entity.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(Enchantments.KNOCKBACK), entity);
    }
}
