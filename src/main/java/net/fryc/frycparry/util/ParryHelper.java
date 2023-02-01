package net.fryc.frycparry.util;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;

public class ParryHelper {

    public static boolean checkDualWielding(PlayerEntity user){
        return FrycParry.config.enableBlockingWhenDualWielding && (user.getOffHandStack().getItem() instanceof SwordItem || user.getOffHandStack().getItem() instanceof AxeItem);
    }
}
