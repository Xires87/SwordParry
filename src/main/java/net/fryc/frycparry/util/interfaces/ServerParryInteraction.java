package net.fryc.frycparry.util.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface ServerParryInteraction {

    //for ServerPlayerInteractionManager
    ActionResult interactItemParry(ServerPlayerEntity player, World world, ItemStack stack, Hand hand);
}
