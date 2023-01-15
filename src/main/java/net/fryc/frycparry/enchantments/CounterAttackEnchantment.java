package net.fryc.frycparry.enchantments;

import net.fryc.frycparry.FrycParry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

public class CounterAttackEnchantment extends Enchantment {
    protected CounterAttackEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
    }

    public int getMaxLevel() {
        return 2;
    }

    public int getMinPower(int level) {
        return 15 + (level - 1) * 9;
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof ShieldItem || (item instanceof SwordItem && FrycParry.config.enableBlockingWithSword) || (item instanceof AxeItem && FrycParry.config.enableBlockingWithAxe);
    }
}
