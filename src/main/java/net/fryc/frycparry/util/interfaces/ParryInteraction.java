package net.fryc.frycparry.util.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public interface ParryInteraction {

    //for ClientPlayerInteractionManager
    ActionResult interactItemParry(PlayerEntity player, Hand hand);

    void stopUsingItemParry(PlayerEntity player);

}
