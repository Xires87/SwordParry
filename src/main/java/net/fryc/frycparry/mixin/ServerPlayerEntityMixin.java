package net.fryc.frycparry.mixin;

import net.fryc.frycparry.util.ServerParryKeyUser;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin implements ServerParryKeyUser {

    boolean pressedParryKey = false;


    public boolean getPressedParryKeyValue() {
        return pressedParryKey;
    }


    public void changePressedParryKeyValueToTrue() {
        pressedParryKey = true;
    }
    public void changePressedParryKeyValueToFalse() {
        pressedParryKey = false;
    }
}
