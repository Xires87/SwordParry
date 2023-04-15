package net.fryc.frycparry.util;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

public class ParryHelper {

    public static boolean canParry(LivingEntity user){
        return user.getMainHandStack().getItem() instanceof ToolItem && !hasShieldEquipped(user) && (user.getOffHandStack().isEmpty() || checkDualWieldingWeapons(user) || checkDualWieldingItems());
    }

    public static boolean checkDualWieldingWeapons(LivingEntity user){
        return FrycParry.config.enableBlockingWhenDualWielding > 0 && user.getOffHandStack().getItem() instanceof ToolItem;
    }

    public static boolean checkDualWieldingItems(){
        return FrycParry.config.enableBlockingWhenDualWielding > 1;
    }

    public static boolean hasShieldEquipped(LivingEntity user){
        return user.getMainHandStack().getItem() instanceof ShieldItem || user.getOffHandStack().getItem() instanceof ShieldItem;
    }

    public static boolean isItemParryDisabled(Item item){
        if(item instanceof SwordItem) return !FrycParry.config.enableBlockingWithSword;
        if(item instanceof AxeItem) return !FrycParry.config.enableBlockingWithAxe;
        if(item instanceof PickaxeItem) return !FrycParry.config.enableBlockingWithPickaxe;
        if(item instanceof ShovelItem) return !FrycParry.config.enableBlockingWithShovel;
        if(item instanceof HoeItem) return !FrycParry.config.enableBlockingWithHoe;
        return false;
    }
}
