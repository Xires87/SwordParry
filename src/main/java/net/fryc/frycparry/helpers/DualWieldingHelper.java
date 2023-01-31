package net.fryc.frycparry.helpers;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;

public class DualWieldingHelper {

    public static boolean checkDualWielding(PlayerEntity user){
        return FrycParry.config.enableBlockingWhenDualWielding && (user.getOffHandStack().getItem() instanceof SwordItem || user.getOffHandStack().getItem() instanceof AxeItem);
    }
}
