package net.fryc.frycparry.util;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;

public class ParryHelper {

    public static boolean canParry(LivingEntity user){
        return user.getMainHandStack().getItem() instanceof ToolItem && !hasShieldEquipped(user) && (user.getOffHandStack().isEmpty() || checkDualWieldingWeapons(user) || checkDualWieldingItems());
    }

    public static boolean checkDualWieldingWeapons(LivingEntity user){
        return FrycParry.config.enableBlockingWhenDualWielding > 0 && user.getOffHandStack().getItem() instanceof ToolItem;
    }

    public static boolean checkDualWieldingItems(){
        return FrycParry.config.enableBlockingWhenDualWielding > 1;
    }

    public static boolean hasShieldEquipped(LivingEntity user){
        return user.getMainHandStack().getItem() instanceof ShieldItem || user.getOffHandStack().getItem() instanceof ShieldItem;
    }

    public static boolean isItemParryDisabled(Item item){
        if(item instanceof SwordItem) return !FrycParry.config.enableBlockingWithSword;
        if(item instanceof AxeItem) return !FrycParry.config.enableBlockingWithAxe;
        if(item instanceof PickaxeItem) return !FrycParry.config.enableBlockingWithPickaxe;
        if(item instanceof ShovelItem) return !FrycParry.config.enableBlockingWithShovel;
        if(item instanceof HoeItem) return !FrycParry.config.enableBlockingWithHoe;
        return false;
    }

    public static void applyParryEffects(LivingEntity user, LivingEntity attacker){
        //parry enchantment
        int parryEnchantmentLevel = ModEnchantments.getParryEnchantment(user);

        //variables for parry effects
        double knockback = ((ParryItem) user.getActiveItem().getItem()).getKnockbackAfterParryAction();
        int slowness = ((ParryItem) user.getActiveItem().getItem()).getSlownessAfterParryAction();
        int slownessAmp = ((ParryItem) user.getActiveItem().getItem()).getSlownessAmplifierAfterParryAction();
        int weakness = ((ParryItem) user.getActiveItem().getItem()).getWeaknessAfterParryAction();
        int weaknessAmp = ((ParryItem) user.getActiveItem().getItem()).getWeaknessAmplifierAfterParryAction();
        int disarmed = 0;

        float[] modifiers = new float[3];
        if(attacker instanceof PlayerEntity) {
            modifiers[0] = 0.65F; modifiers[1] = 0.2F; modifiers[2] = 0.15F;
            knockback -= FrycParry.config.parryKnockbackStrengthForPlayersModifier;
            slowness -= FrycParry.config.slownessForPlayersAfterParryModifier;
            slownessAmp -= FrycParry.config.slownessForPlayersAmplifierModifier;
            weakness -= FrycParry.config.weaknessForPlayersAfterParryModifier;
            weaknessAmp -= FrycParry.config.weaknessForPlayersAmplifierModifier;
            disarmed = ((ParryItem) user.getActiveItem().getItem()).getDisarmedAfterParryAction();
        }
        else {
            modifiers[0] = 0.95F; modifiers[1] = 0.3F; modifiers[2] = 0.22F;
        }

        //knockback
        if(knockback > 0){
            attacker.takeKnockback((knockback + parryEnchantmentLevel * modifiers[0])/10, user.getX() - attacker.getX(), user.getZ() - attacker.getZ());
            attacker.velocityModified = true;
        }

        //slowness
        if(slowness > 0){
            slowness = (int) (slowness + slowness * (parryEnchantmentLevel * modifiers[1]));
            slownessAmp--;
            if(slownessAmp < 0) slownessAmp = 0;
            if(attacker.hasStatusEffect(StatusEffects.SLOWNESS)){
                if(attacker.getActiveStatusEffects().get(StatusEffects.SLOWNESS).getDuration() < slowness) attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, slowness, slownessAmp));
            }
            else attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, slowness, slownessAmp));
        }

        //weakness
        if(weakness > 0){
            weakness = (int) (weakness + weakness * (parryEnchantmentLevel * modifiers[2]));
            weaknessAmp--;
            if(weaknessAmp < 0) weaknessAmp = 0;
            if(attacker.hasStatusEffect(StatusEffects.WEAKNESS)){
                if(attacker.getActiveStatusEffects().get(StatusEffects.WEAKNESS).getDuration() < weakness) attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, weakness, weaknessAmp));
            }
            else attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, weakness, weaknessAmp));
        }

        //disarm
        if(disarmed > 0){
            disarmed = disarmed + parryEnchantmentLevel * 5;
            if(attacker.hasStatusEffect(ModEffects.DISARMED)){
                if(attacker.getActiveStatusEffects().get(ModEffects.DISARMED).getDuration() < disarmed) attacker.addStatusEffect(new StatusEffectInstance(ModEffects.DISARMED, disarmed, 0));
            }
            else attacker.addStatusEffect(new StatusEffectInstance(ModEffects.DISARMED, disarmed, 0));
        }
    }

    public static void applyCounterattackEffects(PlayerEntity user, LivingEntity attacker){
        int counterattackEnchantmentLevel = ModEnchantments.getCounterattackEnchantment(user);
        if(counterattackEnchantmentLevel > 0){
            double ctrattack_damage = user.getAttributes().getValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            if(user.getOffHandStack().isEmpty()) ctrattack_damage *= (counterattackEnchantmentLevel * 0.2) + 0.1;
            else ctrattack_damage *= (counterattackEnchantmentLevel * 0.1) + 0.1;
            attacker.damage(attacker.getWorld().getDamageSources().playerAttack(user),(float) ctrattack_damage);
        }
    }
}
