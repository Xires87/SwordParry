package net.fryc.frycparry.util.interfaces;

import net.fryc.frycparry.attributes.ParryCooldownManager;

public interface HasParryCooldownManager {

    void initParryCooldownManager();

    ParryCooldownManager getParryCooldownManager();
}
