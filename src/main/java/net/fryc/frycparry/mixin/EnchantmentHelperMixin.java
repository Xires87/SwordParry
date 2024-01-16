package net.fryc.frycparry.mixin;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.*;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;
import java.util.Objects;

@Mixin(EnchantmentHelper.class)
abstract class EnchantmentHelperMixin {

    //makes enchantments for shield appear only on shield or book when using enchanting table
    @ModifyVariable(method = "generateEnchantments(Lnet/minecraft/util/math/random/Random;Lnet/minecraft/item/ItemStack;" +
            "IZ)Ljava/util/List;", at = @At("STORE"), ordinal = 1)
    private static List<EnchantmentLevelEntry> removeShieldEnchantments(List<EnchantmentLevelEntry> list2, Random random, ItemStack stack, int level, boolean treasureAllowed) {
        if(stack.getItem() instanceof ShieldItem || stack.isOf(Items.BOOK) || (treasureAllowed && isWeaponThatCanBlock(stack.getItem()))) return list2;
        boolean bl = true;
        int lastIndex = 0;
        while(bl){
            bl = false;
            if(lastIndex > 0) lastIndex--;
            for(int i = lastIndex;i < list2.size(); i++){
                if(isEnchantmentForShield(list2.get(i).enchantment.getTranslationKey())){
                    list2.remove(i);
                    lastIndex = i;
                    bl = true;
                    break;
                }
            }
        }

        return list2;
    }

    private static boolean isEnchantmentForShield(String translationKey){
        if(Objects.equals(translationKey, "enchantment.frycparry.prediction_enchantment")) return true;
        else if(Objects.equals(translationKey, "enchantment.frycparry.counterattack_enchantment")) return true;
        else if(Objects.equals(translationKey, "enchantment.frycparry.parry_enchantment")) return true;

        return false;
    }

    private static boolean isWeaponThatCanBlock(Item item){
        if(!FrycParry.config.server.enchantmentsForShieldsCanAppearOnWeaponsInChests) return false;
        return (item instanceof SwordItem || item instanceof AxeItem) && !ParryHelper.isItemParryDisabled(item);
    }
}
