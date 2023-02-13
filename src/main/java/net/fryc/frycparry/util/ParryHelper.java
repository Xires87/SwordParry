package net.fryc.frycparry.util;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

public class ParryHelper {

    public static boolean checkDualWieldingWeapons(LivingEntity user){
        return FrycParry.config.enableBlockingWhenDualWielding > 0 && (user.getOffHandStack().getItem() instanceof SwordItem || user.getOffHandStack().getItem() instanceof AxeItem);
    }

    public static boolean checkDualWieldingItems(LivingEntity user){
        return FrycParry.config.enableBlockingWhenDualWielding > 1 && !(user.getOffHandStack().getItem() instanceof ShieldItem) && !(user.getOffHandStack().getItem() instanceof BowItem) && !(user.getOffHandStack().getItem() instanceof CrossbowItem);
    }
}
