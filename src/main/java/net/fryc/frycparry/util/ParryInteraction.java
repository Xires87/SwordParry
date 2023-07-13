package net.fryc.frycparry.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public interface ParryInteraction {

    //for ClientPlayerInteractionManager
    ActionResult interactItemParry(PlayerEntity player, Hand hand);

    public void stopUsingItemParry(PlayerEntity player);

}
