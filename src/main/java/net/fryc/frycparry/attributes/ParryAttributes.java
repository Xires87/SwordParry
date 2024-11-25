package net.fryc.frycparry.attributes;

import net.fryc.frycparry.FrycParry;
import net.minecraft.entity.effect.StatusEffect;
import oshi.util.tuples.Quartet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParryAttributes {

    private final int parryTicks;
    private final float meleeDamageTakenAfterBlock;
    private final float projectileDamageTakenAfterBlock;
    private final float cooldownAfterParryAction;
    private final float cooldownAfterInterruptingBlockAction;
    private final int maxUseTime;
    private final boolean shouldStopUsingItemAfterBlockOrParry;
    private final double knockbackAfterParryAction;

    private final Map<StatusEffect, Quartet<Integer, Integer, Float, Float>> parryEffects;
    /*
    private final int slownessAfterParryAction;
    private final int slownessAmplifierAfterParryAction;
    private final int weaknessAfterParryAction;
    private final int weaknessAmplifierAfterParryAction;
    private final int disarmedAfterParryAction;
     */

    private static final HashMap<String, ParryAttributes> REGISTERED_ATTRIBUTES = new HashMap<>();
    public static final ParryAttributes DEFAULT = ParryAttributes.create(
            "default", 0, 0.80F, 0.95F,
            18, 28, 7200, true,
            4.0, new HashMap<>()
            );


    public static ParryAttributes get(String id){
        if(REGISTERED_ATTRIBUTES.containsKey(id)){
            return REGISTERED_ATTRIBUTES.get(id);
        }

        FrycParry.LOGGER.error("Unable to get Parry Attributes with following id: " + id);
        return REGISTERED_ATTRIBUTES.get("default");
    }

    public ParryAttributes(int parryTicks, float meleeDamageTakenAfterBlock, float projectileDamageTakenAfterBlock,
                           float cooldownAfterParryAction, float cooldownAfterInterruptingBlockAction,
                           int maxUseTime, boolean shouldStopUsingItemAfterBlockOrParry,
                           double knockbackAfterParryAction, Map<StatusEffect, Quartet<Integer, Integer, Float, Float>> parryEffects){

        this.parryTicks = parryTicks;
        this.meleeDamageTakenAfterBlock = meleeDamageTakenAfterBlock;
        this.projectileDamageTakenAfterBlock = projectileDamageTakenAfterBlock;
        this.cooldownAfterParryAction = cooldownAfterParryAction;
        this.cooldownAfterInterruptingBlockAction = cooldownAfterInterruptingBlockAction;
        this.maxUseTime = maxUseTime;
        this.shouldStopUsingItemAfterBlockOrParry = shouldStopUsingItemAfterBlockOrParry;
        this.knockbackAfterParryAction = knockbackAfterParryAction;
        /*
        this.slownessAfterParryAction = slownessAfterParryAction;
        this.slownessAmplifierAfterParryAction = slownessAmplifierAfterParryAction;
        this.weaknessAfterParryAction = weaknessAfterParryAction;
        this.weaknessAmplifierAfterParryAction = weaknessAmplifierAfterParryAction;
        this.disarmedAfterParryAction = disarmedAfterParryAction;

         */
        this.parryEffects = parryEffects;
    }

    protected void addToMap(String id){
        REGISTERED_ATTRIBUTES.put(id, this);
    }

    public static ParryAttributes create(String id, int parryTicks, float meleeDamageTakenAfterBlock, float projectileDamageTakenAfterBlock,
                                         float cooldownAfterParryAction, float cooldownAfterInterruptingBlockAction,
                                         int maxUseTime, boolean shouldStopUsingItemAfterBlockOrParry,
                                         double knockbackAfterParryAction, Map<StatusEffect, Quartet<Integer, Integer, Float, Float>> parryEffects){

        return create(id, new ParryAttributes(parryTicks, meleeDamageTakenAfterBlock, projectileDamageTakenAfterBlock,
                cooldownAfterParryAction, cooldownAfterInterruptingBlockAction, maxUseTime, shouldStopUsingItemAfterBlockOrParry,
                knockbackAfterParryAction, parryEffects));
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

    public float getCooldownAfterParryAction(){
        return this.cooldownAfterParryAction;
    }

    public float getCooldownAfterInterruptingBlockAction(){
        return this.cooldownAfterInterruptingBlockAction;
    }

    public double getKnockbackAfterParryAction(){
        return this.knockbackAfterParryAction;
    }

    public boolean shouldStopUsingItemAfterBlockOrParry(){
        return this.shouldStopUsingItemAfterBlockOrParry;
    }

    public Iterator<Map.Entry<StatusEffect, Quartet<Integer, Integer, Float, Float>>> getParryEffectsIterator(){
        return this.parryEffects.entrySet().iterator();
    }
}
