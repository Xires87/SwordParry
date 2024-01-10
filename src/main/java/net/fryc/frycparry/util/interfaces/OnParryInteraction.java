package net.fryc.frycparry.util.interfaces;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

public interface OnParryInteraction {

    //for ServerPlayNetworkHandler
    void onPlayerInteractItemParry(ServerPlayerEntity player, ServerWorld world, Hand hand);
}
