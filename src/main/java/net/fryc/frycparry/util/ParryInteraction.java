package net.fryc.frycparry.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public interface ParryInteraction {

    ActionResult interactItemParry(PlayerEntity player, Hand hand);

    //todo scalic te interfejsy zeby mniej klas bylo (jesli mozna)
}
