package net.fryc.frycparry.attributes;

public class ParryCooldownManager {

    private int currentCooldown = 0;
    private int maxCooldown = 0;

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
        return 0f;
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
