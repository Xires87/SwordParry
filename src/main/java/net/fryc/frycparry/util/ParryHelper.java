package net.fryc.frycparry.util;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParryHelper {

    public static int dualWieldingSettings = FrycParry.config.server.enableBlockingWhenDualWielding;
    public static boolean enableBlockingWithSword = FrycParry.config.sword.enableBlockingWithSword;
    public static boolean enableBlockingWithAxe = FrycParry.config.axe.enableBlockingWithAxe;
    public static boolean enableBlockingWithPickaxe = FrycParry.config.pickaxe.enableBlockingWithPickaxe;
    public static boolean enableBlockingWithShovel = FrycParry.config.shovel.enableBlockingWithShovel;
    public static boolean enableBlockingWithHoe = FrycParry.config.hoe.enableBlockingWithHoe;
    public static boolean enableBlockingWithOtherTools = FrycParry.config.server.enableBlockingWithOtherTools;

    public static boolean canParryWithoutShield(LivingEntity user){
        return user.getMainHandStack().getItem() instanceof ToolItem && !hasShieldEquipped(user) && (user.getOffHandStack().isEmpty() || checkDualWieldingWeapons(user) || checkDualWieldingItems(user));
    }

    public static boolean checkDualWieldingWeapons(LivingEntity user){
        if(user.getWorld().isClient()){
            return dualWieldingSettings > 0 && user.getOffHandStack().getItem() instanceof ToolItem;
        }
        return FrycParry.config.server.enableBlockingWhenDualWielding > 0 && user.getOffHandStack().getItem() instanceof ToolItem;
    }

    public static boolean checkDualWieldingItems(LivingEntity user){
        if(user.getWorld().isClient()){
            return dualWieldingSettings > 1;
        }
        return FrycParry.config.server.enableBlockingWhenDualWielding > 1;
    }

    public static boolean hasShieldEquipped(LivingEntity user){
        return user.getMainHandStack().getUseAction() == UseAction.BLOCK || user.getOffHandStack().getUseAction() == UseAction.BLOCK;
    }

    public static boolean blockingFullyNegatesDamage(DamageSource source, ItemStack stack, LivingEntity user){
        if(source.isIn(DamageTypeTags.IS_EXPLOSION)){
            return user.getActiveItem().getUseAction() == UseAction.BLOCK && user.getActiveItem().getMaxUseTime() - user.getItemUseTimeLeft() >= 5;
        }
        if(stack.getItem() instanceof ParryItem parryItem){
            if(source.isIn(DamageTypeTags.IS_PROJECTILE)){
                return parryItem.getProjectileDamageTakenAfterBlock() <= 0f;
            }
            return parryItem.getMeleeDamageTakenAfterBlock() <= 0f;
        }
        return true;
    }

    /**
     *  !!! use only after attackWasBlocked() check !!!
     */
    public static boolean attackWasParried(DamageSource source, ItemStack stack, LivingEntity user){
        if(source.isIn(DamageTypeTags.IS_EXPLOSION)) return false;
        if(stack.getItem() instanceof ParryItem parryItem){
            int maxUseTime = user.getActiveItem().getUseAction() == UseAction.BLOCK ? user.getActiveItem().getMaxUseTime() : ((ParryItem) user.getActiveItem().getItem()).getMaxUseTimeParry();
            return maxUseTime - user.getItemUseTimeLeft() < parryItem.getParryTicks() + ModEnchantments.getPredictionEnchantment(user);
        }
        return false;
    }

    //vanilla blockedByShield method with additional parameter
    public static boolean attackWasBlocked(DamageSource source, LivingEntity user) {
        Entity entity = source.getSource();
        boolean bl = false;
        if (entity instanceof PersistentProjectileEntity persistentProjectileEntity) {
            if (persistentProjectileEntity.getPierceLevel() > 0) {
                bl = true;
            }
        }

        if (!source.isIn(DamageTypeTags.BYPASSES_ARMOR) && user.isBlocking() && !bl) {
            Vec3d vec3d = source.getPosition();
            if (vec3d != null) {
                Vec3d vec3d2 = user.getRotationVec(1.0F);
                Vec3d vec3d3 = vec3d.relativize(user.getPos()).normalize();
                vec3d3 = new Vec3d(vec3d3.x, 0.0, vec3d3.z);
                if (vec3d3.dotProduct(vec3d2) < 0.0) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isItemParryDisabled(World world, Item item){
        if(world.isClient()){
            if(item instanceof SwordItem) return !enableBlockingWithSword;
            if(item instanceof AxeItem) return !enableBlockingWithAxe;
            if(item instanceof PickaxeItem) return !enableBlockingWithPickaxe;
            if(item instanceof ShovelItem) return !enableBlockingWithShovel;
            if(item instanceof HoeItem) return !enableBlockingWithHoe;
            return !enableBlockingWithOtherTools;
        }
        return isItemParryDisabled(item);
    }

    public static boolean isItemParryDisabled(Item item){
        if(item instanceof SwordItem) return !FrycParry.config.sword.enableBlockingWithSword;
        if(item instanceof AxeItem) return !FrycParry.config.axe.enableBlockingWithAxe;
        if(item instanceof PickaxeItem) return !FrycParry.config.pickaxe.enableBlockingWithPickaxe;
        if(item instanceof ShovelItem) return !FrycParry.config.shovel.enableBlockingWithShovel;
        if(item instanceof HoeItem) return !FrycParry.config.hoe.enableBlockingWithHoe;
        return !FrycParry.config.server.enableBlockingWithOtherTools;
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
            knockback -= FrycParry.config.multiplayerModifiers.parryKnockbackStrengthForPlayersModifier;
            slowness -= FrycParry.config.multiplayerModifiers.slownessForPlayersAfterParryModifier;
            slownessAmp -= FrycParry.config.multiplayerModifiers.slownessForPlayersAmplifierModifier;
            weakness -= FrycParry.config.multiplayerModifiers.weaknessForPlayersAfterParryModifier;
            weaknessAmp -= FrycParry.config.multiplayerModifiers.weaknessForPlayersAmplifierModifier;
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
