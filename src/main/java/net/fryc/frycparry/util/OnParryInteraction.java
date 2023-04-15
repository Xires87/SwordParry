package net.fryc.frycparry.util;

import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;

public interface OnParryInteraction {

    //for ServerPlayNetworkHandler
    void onPlayerInteractItemParry(PlayerInteractItemC2SPacket packet);
}
