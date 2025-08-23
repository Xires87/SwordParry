package net.fryc.frycparry.attributes;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fryc.frycparry.network.payloads.SynchronizeParryCooldownPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public class ParryCooldownManager {

    private int currentCooldown = 0;
    private int maxCooldown = 0;

    public boolean isCoolingDown(){
        return this.currentCooldown > 0;
    }

    public void tick(){
        if(this.currentCooldown > 0){
            this.currentCooldown--;
            this.onCooldownTick();
        }
        else {
            this.maxCooldown = 0;
        }
    }

    public float getCooldownProgress(){
        if (this.maxCooldown > 0) {
            return 1f - (float) this.currentCooldown / this.maxCooldown;
        }
        return 1.0f;
    }

    public void onCooldownTick(){
    }

    public void onCooldownUpdate(){
        this.maxCooldown = this.currentCooldown;
    }

    /**
     *  Adds cooldown only if current cooldown is lower than given cooldown.
     *  @return  True if given cooldown is higher than current cooldown
     */
    public boolean addCooldown(int cooldown){
        if(this.getCooldown() < cooldown){
            this.setCooldown(cooldown);
            this.onCooldownUpdate();

            return true;
        }

        return false;
    }

    /**
     *  (SERVER SIDED) If given cooldown is higher than current cooldown, sets current cooldown to given cooldown and sends packet to client.
     *  @return  True if given cooldown is higher than current cooldown
     */
    public boolean addCooldown(ServerPlayerEntity player, int cooldown){
        boolean wasAdded = this.addCooldown(cooldown);
        if(wasAdded){
            ServerPlayNetworking.send(player, new SynchronizeParryCooldownPayload(cooldown));
        }

        return wasAdded;
    }

    public void removeCooldown(){
        this.setCooldown(0);
        this.onCooldownUpdate();
    }

    public int getCooldown() {
        return currentCooldown;
    }

    public void setCooldown(int cooldown) {
        this.currentCooldown = cooldown;
    }
}
