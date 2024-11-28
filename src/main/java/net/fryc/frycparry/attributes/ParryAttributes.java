package net.fryc.frycparry.attributes;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.util.ConfigHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.*;
import oshi.util.tuples.Quartet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParryAttributes {

    private final int parryTicks;
    private final float meleeDamageTakenAfterBlock;
    private final float projectileDamageTakenAfterBlock;
    private final float explosionDamageTakenAfterBlock;
    private final float cooldownAfterParryAction;
    private final float cooldownAfterInterruptingBlockAction;
    private final float cooldownAfterAttack;
    private final int maxUseTime;
    private final int blockDelay;
    private final int explosionBlockDelay;
    private final boolean shouldStopUsingItemAfterBlockOrParry;
    private final double knockbackAfterParryAction;

    private final Map<StatusEffect, Quartet<Integer, Integer, Float, Float>> parryEffects;


    private static final HashMap<String, ParryAttributes> REGISTERED_ATTRIBUTES = new HashMap<>();
    public static final ParryAttributes DEFAULT = ParryAttributes.create(
            "default", 0, 0.80F, 0.95F, 1.0F,
            18, 28, -0.90F, 7200,
            0, -1, true, 4.0, new HashMap<>()
            );


    public static ParryAttributes get(String id){
        if(REGISTERED_ATTRIBUTES.containsKey(id)){
            return REGISTERED_ATTRIBUTES.get(id);
        }

        FrycParry.LOGGER.error("Unable to get Parry Attributes with following id: " + id);
        return REGISTERED_ATTRIBUTES.get("default");
    }

    protected ParryAttributes(int parryTicks, float meleeDamageTakenAfterBlock, float projectileDamageTakenAfterBlock,
                              float explosionDamageTakenAfterBlock,
                              float cooldownAfterParryAction, float cooldownAfterInterruptingBlockAction, float cooldownAfterAttack,
                              int maxUseTime, int blockDelay, int explosionBlockDelay, boolean shouldStopUsingItemAfterBlockOrParry,
                              double knockbackAfterParryAction, Map<StatusEffect, Quartet<Integer, Integer, Float, Float>> parryEffects){

        this.parryTicks = parryTicks;
        this.meleeDamageTakenAfterBlock = meleeDamageTakenAfterBlock;
        this.projectileDamageTakenAfterBlock = projectileDamageTakenAfterBlock;
        this.explosionDamageTakenAfterBlock = explosionDamageTakenAfterBlock;
        this.cooldownAfterParryAction = cooldownAfterParryAction;
        this.cooldownAfterInterruptingBlockAction = cooldownAfterInterruptingBlockAction;
        this.cooldownAfterAttack = cooldownAfterAttack;
        this.maxUseTime = maxUseTime;
        this.blockDelay = blockDelay;
        this.explosionBlockDelay = explosionBlockDelay;
        this.shouldStopUsingItemAfterBlockOrParry = shouldStopUsingItemAfterBlockOrParry;
        this.knockbackAfterParryAction = knockbackAfterParryAction;
        this.parryEffects = parryEffects;
    }

    protected void addToMap(String id){
        REGISTERED_ATTRIBUTES.put(id, this);
    }

    public static ParryAttributes create(String id, int parryTicks, float meleeDamageTakenAfterBlock, float projectileDamageTakenAfterBlock, float explosionDamageTakenAfterBlock,
                                         float cooldownAfterParryAction, float cooldownAfterInterruptingBlockAction, float cooldownAfterAttack,
                                         int maxUseTime, int blockDelay, int explosionBlockDelay, boolean shouldStopUsingItemAfterBlockOrParry,
                                         double knockbackAfterParryAction, Map<StatusEffect, Quartet<Integer, Integer, Float, Float>> parryEffects){

        return create(id, new ParryAttributes(parryTicks, meleeDamageTakenAfterBlock, projectileDamageTakenAfterBlock, explosionDamageTakenAfterBlock,
                cooldownAfterParryAction, cooldownAfterInterruptingBlockAction, cooldownAfterAttack, maxUseTime, blockDelay,
                explosionBlockDelay, shouldStopUsingItemAfterBlockOrParry, knockbackAfterParryAction, parryEffects));
    }

    private static ParryAttributes create(String id, ParryAttributes parryAttributes){
        parryAttributes.addToMap(id);
        return parryAttributes;
    }


    public static ParryAttributes getDefaultParryAttributes(Item item){
        if(item instanceof PickaxeItem) return new ParryAttributes(
                FrycParry.config.pickaxe.pickaxeParryTicks, (float)FrycParry.config.pickaxe.pickaxeBlockMeleeDamageTaken/100,
                (float)FrycParry.config.pickaxe.pickaxeBlockArrowDamageTaken/100, (float)FrycParry.config.pickaxe.explosionBlockDamageTaken/100,
                FrycParry.config.pickaxe.cooldownAfterPickaxeParryAction, FrycParry.config.pickaxe.cooldownAfterInterruptingPickaxeBlockAction,
                FrycParry.config.pickaxe.cooldownAfterAttack, FrycParry.config.pickaxe.maxUseTime, FrycParry.config.pickaxe.blockDelay,
                FrycParry.config.pickaxe.explosionBlockDelay, FrycParry.config.pickaxe.shouldStopUsingPickaxeAfterBlockOrParry,
                FrycParry.config.pickaxe.pickaxeParryKnockbackStrength, new HashMap<>(ConfigHelper.pickaxeParryEffects)
        );
        if(item instanceof AxeItem) return new ParryAttributes(
                FrycParry.config.axe.axeParryTicks, (float)FrycParry.config.axe.axeBlockMeleeDamageTaken/100,
                (float)FrycParry.config.axe.axeBlockArrowDamageTaken/100, (float)FrycParry.config.axe.explosionBlockDamageTaken/100,
                FrycParry.config.axe.cooldownAfterAxeParryAction, FrycParry.config.axe.cooldownAfterInterruptingAxeBlockAction,
                FrycParry.config.axe.cooldownAfterAttack, FrycParry.config.axe.maxUseTime, FrycParry.config.axe.blockDelay,
                FrycParry.config.axe.explosionBlockDelay, FrycParry.config.axe.shouldStopUsingAxeAfterBlockOrParry,
                FrycParry.config.axe.axeParryKnockbackStrength, new HashMap<>(ConfigHelper.axeParryEffects)
        );
        if(item instanceof SwordItem) return new ParryAttributes(
                FrycParry.config.sword.swordParryTicks, (float)FrycParry.config.sword.swordBlockMeleeDamageTaken/100,
                (float)FrycParry.config.sword.swordBlockArrowDamageTaken/100, (float)FrycParry.config.sword.explosionBlockDamageTaken/100,
                FrycParry.config.sword.cooldownAfterSwordParryAction, FrycParry.config.sword.cooldownAfterInterruptingSwordBlockAction,
                FrycParry.config.sword.cooldownAfterAttack, FrycParry.config.sword.maxUseTime, FrycParry.config.sword.blockDelay,
                FrycParry.config.sword.explosionBlockDelay, FrycParry.config.sword.shouldStopUsingSwordAfterBlockOrParry,
                FrycParry.config.sword.swordParryKnockbackStrength, new HashMap<>(ConfigHelper.swordParryEffects)
        );
        if(item instanceof ShovelItem) return new ParryAttributes(
                FrycParry.config.shovel.shovelParryTicks, (float)FrycParry.config.shovel.shovelBlockMeleeDamageTaken/100,
                (float)FrycParry.config.shovel.shovelBlockArrowDamageTaken/100, (float)FrycParry.config.shovel.explosionBlockDamageTaken/100,
                FrycParry.config.shovel.cooldownAfterShovelParryAction, FrycParry.config.shovel.cooldownAfterInterruptingShovelBlockAction,
                FrycParry.config.shovel.cooldownAfterAttack, FrycParry.config.shovel.maxUseTime, FrycParry.config.shovel.blockDelay,
                FrycParry.config.shovel.explosionBlockDelay, FrycParry.config.shovel.shouldStopUsingShovelAfterBlockOrParry,
                FrycParry.config.shovel.shovelParryKnockbackStrength, new HashMap<>(ConfigHelper.shovelParryEffects)
        );
        if(item instanceof HoeItem) return new ParryAttributes(
                FrycParry.config.hoe.hoeParryTicks, (float)FrycParry.config.hoe.hoeBlockMeleeDamageTaken/100,
                (float)FrycParry.config.hoe.hoeBlockArrowDamageTaken/100, (float)FrycParry.config.hoe.explosionBlockDamageTaken/100,
                FrycParry.config.hoe.cooldownAfterHoeParryAction, FrycParry.config.hoe.cooldownAfterInterruptingHoeBlockAction,
                FrycParry.config.hoe.cooldownAfterAttack, FrycParry.config.hoe.maxUseTime, FrycParry.config.hoe.blockDelay,
                FrycParry.config.hoe.explosionBlockDelay, FrycParry.config.hoe.shouldStopUsingHoeAfterBlockOrParry,
                FrycParry.config.hoe.hoeParryKnockbackStrength, new HashMap<>(ConfigHelper.hoeParryEffects)
        );
        if(item instanceof ShieldItem) return new ParryAttributes(
                FrycParry.config.shield.shieldParryTicks, (float)FrycParry.config.shield.shieldBlockMeleeDamageTaken/100,
                (float)FrycParry.config.shield.shieldBlockArrowDamageTaken/100, (float)FrycParry.config.shield.explosionBlockDamageTaken/100,
                FrycParry.config.shield.cooldownAfterShieldParryAction, FrycParry.config.shield.cooldownAfterInterruptingShieldBlockAction,
                FrycParry.config.shield.cooldownAfterAttack, FrycParry.config.shield.maxUseTime, FrycParry.config.shield.blockDelay,
                FrycParry.config.shield.explosionBlockDelay, FrycParry.config.shield.shouldStopUsingShieldAfterBlockOrParry,
                FrycParry.config.shield.shieldParryKnockbackStrength, new HashMap<>(ConfigHelper.shieldParryEffects)
        );
        if(item.getAttributeModifiers(EquipmentSlot.MAINHAND).keySet().contains(EntityAttributes.GENERIC_ATTACK_SPEED)) return new ParryAttributes(
                FrycParry.config.server.parryTicks, (float)FrycParry.config.server.blockMeleeDamageTaken/100,
                (float)FrycParry.config.server.blockArrowDamageTaken/100, (float)FrycParry.config.server.explosionBlockDamageTaken/100,
                FrycParry.config.server.cooldownAfterParryAction, FrycParry.config.server.cooldownAfterInterruptingBlockAction,
                FrycParry.config.server.cooldownAfterAttack, FrycParry.config.server.maxUseTime, FrycParry.config.server.blockDelay,
                FrycParry.config.server.explosionBlockDelay, FrycParry.config.server.shouldStopUsingAfterBlockOrParry,
                FrycParry.config.server.parryKnockbackStrength, new HashMap<>(ConfigHelper.parryEffects)
        );
        return ParryAttributes.DEFAULT;
    }


    //------------------------------------------------------------------------------------------------------------------

    public int getMaxUseTimeParry(){
        return this.maxUseTime;
    }
    public int getBlockDelay(){
        return this.blockDelay;
    }
    public int getExplosionBlockDelay(){
        return this.explosionBlockDelay;
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
    public float getExplosionDamageTakenAfterBlock(){
        return this.explosionDamageTakenAfterBlock;
    }

    public float getCooldownAfterParryAction(){
        return this.cooldownAfterParryAction;
    }

    public float getCooldownAfterInterruptingBlockAction(){
        return this.cooldownAfterInterruptingBlockAction;
    }
    public float getCooldownAfterAttack(){
        return this.cooldownAfterAttack;
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
