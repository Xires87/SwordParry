package net.fryc.frycparry.attributes;

import net.fryc.frycparry.FrycParry;

import java.util.HashMap;

public class ParryAttributes {

    private final int parryTicks;
    private final float meleeDamageTakenAfterBlock;
    private final float projectileDamageTakenAfterBlock;
    private final int cooldownAfterParryAction;
    private final int cooldownAfterInterruptingBlockAction;
    private final int maxUseTime;
    private final boolean shouldStopUsingItemAfterBlockOrParry;
    private final double knockbackAfterParryAction;
    private final int slownessAfterParryAction;
    private final int slownessAmplifierAfterParryAction;
    private final int weaknessAfterParryAction;
    private final int weaknessAmplifierAfterParryAction;
    private final int disarmedAfterParryAction;

    private static final HashMap<String, ParryAttributes> REGISTERED_ATTRIBUTES = new HashMap<>();
    public static final ParryAttributes DEFAULT = ParryAttributes.create(
            "default", 0, 0.80F, 0.95F,
            18, 28, 7200, true,
            4.0, 60, 1, 0,
            1,0
            );


    public static ParryAttributes get(String id){
        if(REGISTERED_ATTRIBUTES.containsKey(id)){
            return REGISTERED_ATTRIBUTES.get(id);
        }

        FrycParry.LOGGER.error("Unable to get Parry Attributes with following id: " + id);
        return REGISTERED_ATTRIBUTES.get("default");
    }

    public ParryAttributes(int parryTicks, float meleeDamageTakenAfterBlock, float projectileDamageTakenAfterBlock,
                           int cooldownAfterParryAction, int cooldownAfterInterruptingBlockAction,
                           int maxUseTime, boolean shouldStopUsingItemAfterBlockOrParry,
                           double knockbackAfterParryAction, int slownessAfterParryAction,
                           int slownessAmplifierAfterParryAction, int weaknessAfterParryAction,
                           int weaknessAmplifierAfterParryAction, int disarmedAfterParryAction){

        this.parryTicks = parryTicks;
        this.meleeDamageTakenAfterBlock = meleeDamageTakenAfterBlock;
        this.projectileDamageTakenAfterBlock = projectileDamageTakenAfterBlock;
        this.cooldownAfterParryAction = cooldownAfterParryAction;
        this.cooldownAfterInterruptingBlockAction = cooldownAfterInterruptingBlockAction;
        this.maxUseTime = maxUseTime;
        this.shouldStopUsingItemAfterBlockOrParry = shouldStopUsingItemAfterBlockOrParry;
        this.knockbackAfterParryAction = knockbackAfterParryAction;
        this.slownessAfterParryAction = slownessAfterParryAction;
        this.slownessAmplifierAfterParryAction = slownessAmplifierAfterParryAction;
        this.weaknessAfterParryAction = weaknessAfterParryAction;
        this.weaknessAmplifierAfterParryAction = weaknessAmplifierAfterParryAction;
        this.disarmedAfterParryAction = disarmedAfterParryAction;
    }

    protected void addToMap(String id){
        REGISTERED_ATTRIBUTES.put(id, this);
    }

    public static ParryAttributes create(String id, int parryTicks, float meleeDamageTakenAfterBlock, float projectileDamageTakenAfterBlock,
                                         int cooldownAfterParryAction, int cooldownAfterInterruptingBlockAction,
                                         int maxUseTime, boolean shouldStopUsingItemAfterBlockOrParry,
                                         double knockbackAfterParryAction, int slownessAfterParryAction,
                                         int slownessAmplifierAfterParryAction, int weaknessAfterParryAction,
                                         int weaknessAmplifierAfterParryAction, int disarmedAfterParryAction){

        return create(id, new ParryAttributes(parryTicks, meleeDamageTakenAfterBlock, projectileDamageTakenAfterBlock,
                cooldownAfterParryAction, cooldownAfterInterruptingBlockAction, maxUseTime, shouldStopUsingItemAfterBlockOrParry,
                knockbackAfterParryAction, slownessAfterParryAction, slownessAmplifierAfterParryAction, weaknessAfterParryAction,
                weaknessAmplifierAfterParryAction, disarmedAfterParryAction));
    }

    public static ParryAttributes create(String id, ParryAttributes parryAttributes){
        parryAttributes.addToMap(id);
        return parryAttributes;
    }


    //------------------------------------------------------------------------------------------------------------------

    public int getMaxUseTimeParry(){
        return this.maxUseTime;
    }

    public int getParryTicks(){
        return this.parryTicks;
    }

    public float getMeleeDamageTakenAfterBlock(){
        return this.meleeDamageTakenAfterBlock;
    }

    public float getProjectileDamageTakenAfterBlock(){
        return this.projectileDamageTakenAfterBlock;
    }

    public int getCooldownAfterParryAction(){
        return this.cooldownAfterParryAction;
    }

    public int getCooldownAfterInterruptingBlockAction(){
        return this.cooldownAfterInterruptingBlockAction;
    }

    public double getKnockbackAfterParryAction(){
        return this.knockbackAfterParryAction;
    }
    public int getSlownessAfterParryAction(){
        return this.slownessAfterParryAction;
    }
    public int getSlownessAmplifierAfterParryAction(){
        return this.slownessAmplifierAfterParryAction;
    }
    public int getWeaknessAfterParryAction(){
        return this.weaknessAfterParryAction;
    }
    public int getWeaknessAmplifierAfterParryAction(){
        return this.weaknessAmplifierAfterParryAction;
    }
    public int getDisarmedAfterParryAction(){
        return this.disarmedAfterParryAction;
    }

    public boolean shouldStopUsingItemAfterBlockOrParry(){
        return this.shouldStopUsingItemAfterBlockOrParry;
    }
}
