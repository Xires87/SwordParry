package net.fryc.frycparry.util;

public interface ServerParryKeyUser {

    //for ServerPlayerEntity
    boolean getPressedParryKeyValue();
    void changePressedParryKeyValueToTrue();
    void changePressedParryKeyValueToFalse();
}
