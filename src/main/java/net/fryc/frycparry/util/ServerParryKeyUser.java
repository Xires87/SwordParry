package net.fryc.frycparry.util;

public interface ServerParryKeyUser {

    //for server player entity
    boolean getPressedParryKeyValue();
    void changePressedParryKeyValueToTrue();
    void changePressedParryKeyValueToFalse();
}
