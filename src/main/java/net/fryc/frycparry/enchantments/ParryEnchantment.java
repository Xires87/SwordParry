package net.fryc.frycparry.enchantments;

import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

public class ParryEnchantment extends Enchantment {
    protected ParryEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
    }

    public int getMaxLevel() {
        return 2;
    }

    public int getMinPower(int level) {
        return 5 + 20 * (level - 1);
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof ShieldItem || !ParryHelper.isItemParryDisabled(stack);
    }
}
