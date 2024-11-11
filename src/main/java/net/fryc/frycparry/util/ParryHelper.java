package net.fryc.frycparry.util;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.sounds.ModSounds;
import net.fryc.frycparry.tag.ModItemTags;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
        return isItemParryEnabled(user.getMainHandStack()) && !hasShieldEquipped(user) && isNonShieldParryingEnabled(user);
    }

    public static boolean isItemParryEnabled(ItemStack stack){
        if(stack.isEmpty()) return false;
        if(stack.isIn(ModItemTags.PARRYING_EXCLUDED_ITEMS)) return false;
        return stack.getItem().getAttributeModifiers(EquipmentSlot.MAINHAND).keySet().contains(EntityAttributes.GENERIC_ATTACK_SPEED) ||
                stack.isIn(ModItemTags.ITEMS_CAN_PARRY) || stack.getItem() instanceof ShieldItem;
    }

    public static boolean isNonShieldParryingEnabled(LivingEntity user){
        return user.getOffHandStack().isEmpty() || checkDualWieldingWeapons(user) || checkDualWieldingItems(user);
    }

    public static boolean checkDualWieldingWeapons(LivingEntity user){
        if(user.getWorld().isClient()){
            return dualWieldingSettings > 0 && isItemParryEnabled(user.getOffHandStack());
        }
        return FrycParry.config.server.enableBlockingWhenDualWielding > 0 && isItemParryEnabled(user.getOffHandStack());
    }

    public static boolean checkDualWieldingItems(LivingEntity user){
        if(user.getWorld().isClient()){
            return dualWieldingSettings > 1;
        }
        return FrycParry.config.server.enableBlockingWhenDualWielding > 1;
    }

    public static boolean hasShieldEquipped(LivingEntity user){
        return user.getMainHandStack().getItem() instanceof ShieldItem || user.getOffHandStack().getItem() instanceof ShieldItem;
    }

    public static boolean blockingFullyNegatesDamage(DamageSource source, ItemStack stack, LivingEntity user){
        if(source.isIn(DamageTypeTags.IS_EXPLOSION)){
            return user.getActiveItem().getItem() instanceof ShieldItem && user.getActiveItem().getMaxUseTime() - user.getItemUseTimeLeft() >= 5;
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
        if(isItemParryEnabled(stack)){
            int maxUseTime = user.getActiveItem().getItem() instanceof ShieldItem ? user.getActiveItem().getMaxUseTime() : ((ParryItem) user.getActiveItem().getItem()).getMaxUseTimeParry();
            return maxUseTime - user.getItemUseTimeLeft() < ((ParryItem) stack.getItem()).getParryTicks() + ModEnchantments.getPredictionEnchantment(user);
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

    public static boolean isItemParryDisabledWithConfig(World world, ItemStack stack){
        Item item = stack.getItem();
        if(world.isClient()){
            if(item instanceof SwordItem) return !enableBlockingWithSword && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
            if(item instanceof AxeItem) return !enableBlockingWithAxe && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
            if(item instanceof PickaxeItem) return !enableBlockingWithPickaxe && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
            if(item instanceof ShovelItem) return !enableBlockingWithShovel && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
            if(item instanceof HoeItem) return !enableBlockingWithHoe && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
            return !enableBlockingWithOtherTools && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
        }
        return isItemParryDisabledWithConfig(stack);
    }

    /**
     *  Should be used only on server side
     */
    public static boolean isItemParryDisabledWithConfig(ItemStack stack){
        Item item = stack.getItem();
        if(item instanceof SwordItem) return !FrycParry.config.sword.enableBlockingWithSword && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
        if(item instanceof AxeItem) return !FrycParry.config.axe.enableBlockingWithAxe && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
        if(item instanceof PickaxeItem) return !FrycParry.config.pickaxe.enableBlockingWithPickaxe && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
        if(item instanceof ShovelItem) return !FrycParry.config.shovel.enableBlockingWithShovel && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
        if(item instanceof HoeItem) return !FrycParry.config.hoe.enableBlockingWithHoe && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
        return !FrycParry.config.server.enableBlockingWithOtherTools && !stack.isIn(ModItemTags.ITEMS_CAN_PARRY);
    }

    public static ParryAttributes getDefaultParryAttributes(Item item){
        if(item instanceof PickaxeItem) return new ParryAttributes(
                FrycParry.config.pickaxe.pickaxeParryTicks, (float)FrycParry.config.pickaxe.pickaxeBlockMeleeDamageTaken/100,
                (float)FrycParry.config.pickaxe.pickaxeBlockArrowDamageTaken/100, FrycParry.config.pickaxe.cooldownAfterPickaxeParryAction,
                FrycParry.config.pickaxe.cooldownAfterInterruptingPickaxeBlockAction, FrycParry.config.pickaxe.maxUseTime,
                FrycParry.config.pickaxe.shouldStopUsingPickaxeAfterBlockOrParry, FrycParry.config.pickaxe.pickaxeParryKnockbackStrength,
                FrycParry.config.pickaxe.pickaxeSlownessAfterParry, FrycParry.config.pickaxe.pickaxeSlownessAfterParryAmplifier,
                FrycParry.config.pickaxe.pickaxeWeaknessAfterParry, FrycParry.config.pickaxe.pickaxeWeaknessAfterParryAmplifier,
                FrycParry.config.pickaxe.pickaxeDisarmAfterParry
        );
        if(item instanceof AxeItem) return new ParryAttributes(
                FrycParry.config.axe.axeParryTicks, (float)FrycParry.config.axe.axeBlockMeleeDamageTaken/100,
                (float)FrycParry.config.axe.axeBlockArrowDamageTaken/100, FrycParry.config.axe.cooldownAfterAxeParryAction,
                FrycParry.config.axe.cooldownAfterInterruptingAxeBlockAction, FrycParry.config.axe.maxUseTime,
                FrycParry.config.axe.shouldStopUsingAxeAfterBlockOrParry, FrycParry.config.axe.axeParryKnockbackStrength,
                FrycParry.config.axe.axeSlownessAfterParry, FrycParry.config.axe.axeSlownessAfterParryAmplifier,
                FrycParry.config.axe.axeWeaknessAfterParry, FrycParry.config.axe.axeWeaknessAfterParryAmplifier,
                FrycParry.config.axe.axeDisarmAfterParry
        );
        if(item instanceof SwordItem) return new ParryAttributes(
                FrycParry.config.sword.swordParryTicks, (float)FrycParry.config.sword.swordBlockMeleeDamageTaken/100,
                (float)FrycParry.config.sword.swordBlockArrowDamageTaken/100, FrycParry.config.sword.cooldownAfterSwordParryAction,
                FrycParry.config.sword.cooldownAfterInterruptingSwordBlockAction, FrycParry.config.sword.maxUseTime,
                FrycParry.config.sword.shouldStopUsingSwordAfterBlockOrParry, FrycParry.config.sword.swordParryKnockbackStrength,
                FrycParry.config.sword.swordSlownessAfterParry, FrycParry.config.sword.swordSlownessAfterParryAmplifier,
                FrycParry.config.sword.swordWeaknessAfterParry, FrycParry.config.sword.swordWeaknessAfterParryAmplifier,
                FrycParry.config.sword.swordDisarmAfterParry
        );
        if(item instanceof ShovelItem) return new ParryAttributes(
                FrycParry.config.shovel.shovelParryTicks, (float)FrycParry.config.shovel.shovelBlockMeleeDamageTaken/100,
                (float)FrycParry.config.shovel.shovelBlockArrowDamageTaken/100, FrycParry.config.shovel.cooldownAfterShovelParryAction,
                FrycParry.config.shovel.cooldownAfterInterruptingShovelBlockAction, FrycParry.config.shovel.maxUseTime,
                FrycParry.config.shovel.shouldStopUsingShovelAfterBlockOrParry, FrycParry.config.shovel.shovelParryKnockbackStrength,
                FrycParry.config.shovel.shovelSlownessAfterParry, FrycParry.config.shovel.shovelSlownessAfterParryAmplifier,
                FrycParry.config.shovel.shovelWeaknessAfterParry, FrycParry.config.shovel.shovelWeaknessAfterParryAmplifier,
                FrycParry.config.shovel.shovelDisarmAfterParry
        );
        if(item instanceof HoeItem) return new ParryAttributes(
                FrycParry.config.hoe.hoeParryTicks, (float)FrycParry.config.hoe.hoeBlockMeleeDamageTaken/100,
                (float)FrycParry.config.hoe.hoeBlockArrowDamageTaken/100, FrycParry.config.hoe.cooldownAfterHoeParryAction,
                FrycParry.config.hoe.cooldownAfterInterruptingHoeBlockAction, FrycParry.config.hoe.maxUseTime,
                FrycParry.config.hoe.shouldStopUsingHoeAfterBlockOrParry, FrycParry.config.hoe.hoeParryKnockbackStrength,
                FrycParry.config.hoe.hoeSlownessAfterParry, FrycParry.config.hoe.hoeSlownessAfterParryAmplifier,
                FrycParry.config.hoe.hoeWeaknessAfterParry, FrycParry.config.hoe.hoeWeaknessAfterParryAmplifier,
                FrycParry.config.hoe.hoeDisarmAfterParry
        );
        if(item instanceof ShieldItem) return new ParryAttributes(
                FrycParry.config.shield.shieldParryTicks, (float)FrycParry.config.shield.shieldBlockMeleeDamageTaken/100,
                (float)FrycParry.config.shield.shieldBlockArrowDamageTaken/100, FrycParry.config.shield.cooldownAfterShieldParryAction,
                FrycParry.config.shield.cooldownAfterInterruptingShieldBlockAction, 7200,
                FrycParry.config.shield.shouldStopUsingShieldAfterBlockOrParry, FrycParry.config.shield.shieldParryKnockbackStrength,
                FrycParry.config.shield.shieldSlownessAfterParry, FrycParry.config.shield.shieldSlownessAfterParryAmplifier,
                FrycParry.config.shield.shieldWeaknessAfterParry, FrycParry.config.shield.shieldWeaknessAfterParryAmplifier,
                FrycParry.config.shield.shieldDisarmAfterParry
        );
        if(item.getAttributeModifiers(EquipmentSlot.MAINHAND).keySet().contains(EntityAttributes.GENERIC_ATTACK_SPEED)) return new ParryAttributes(
                FrycParry.config.server.parryTicks, (float)FrycParry.config.server.blockMeleeDamageTaken/100,
                (float)FrycParry.config.server.blockArrowDamageTaken/100, FrycParry.config.server.cooldownAfterParryAction,
                FrycParry.config.server.cooldownAfterInterruptingBlockAction, FrycParry.config.server.maxUseTime,
                FrycParry.config.server.shouldStopUsingAfterBlockOrParry, FrycParry.config.server.parryKnockbackStrength,
                FrycParry.config.server.slownessAfterParry, FrycParry.config.server.slownessAfterParryAmplifier,
                FrycParry.config.server.weaknessAfterParry, FrycParry.config.server.weaknessAfterParryAmplifier,
                FrycParry.config.server.disarmAfterParry
        );
        return ParryAttributes.DEFAULT;
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

    public static int getParryCooldown(PlayerEntity player, Item item) {
        float cooldown;
        if(((CanBlock) player).hasParriedRecently()){
            cooldown = ((ParryItem) item).getCooldownAfterParryAction();
        }
        else {
            cooldown = ((ParryItem) item).getCooldownAfterInterruptingBlockAction();
        }

        if(cooldown < 0){
            cooldown = ((int) player.getAttackCooldownProgressPerTick() - 1) * (cooldown * -1);
        }

        return (int) cooldown;
    }

    /**
     *  Setting cooldown after axe attack
     */
    public static void disableParryItem(PlayerEntity player, Item item){
        int cooldown = getParryCooldown(player, item);
        player.getItemCooldownManager().set(item, cooldown + 100);
        ((CanBlock) player).stopUsingItemParry();
    }
/* TODO przetestowac na multii  jeszcze config dodac zeby modyfikowac volume i pitch */
    public static void playBlockSound(LivingEntity entity){
        boolean shield = !ParryHelper.canParryWithoutShield(entity);
        if(shield){
            entity.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 0.9f, 0.8f + entity.getWorld().random.nextFloat() * 0.4f);
        }
        else {
            entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.TOOL_BLOCK, SoundCategory.PLAYERS, 0.9f, 0.8f + entity.getWorld().random.nextFloat() * 0.4f);
        }
    }

    public static void playParrySound(LivingEntity entity){
        boolean shield = !ParryHelper.canParryWithoutShield(entity);
        if(shield){
            entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.SHIELD_PARRY, SoundCategory.PLAYERS, 1.1f, 0.8f + entity.getWorld().random.nextFloat() * 0.4f);
        }
        else {
            entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.TOOL_PARRY, SoundCategory.PLAYERS, 1.1f, 0.8f + entity.getWorld().random.nextFloat() * 0.4f);
        }
    }

    public static void playGuardBreakSound(LivingEntity entity){
        boolean tool = ParryHelper.canParryWithoutShield(entity);
        if(tool){
            entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.TOOL_GUARD_BREAK, SoundCategory.PLAYERS, 0.8f, 0.8f + entity.getWorld().random.nextFloat() * 0.4f);
        }
        else {
            entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.SHIELD_GUARD_BREAK, SoundCategory.PLAYERS, 0.8f, 0.8f + entity.getWorld().random.nextFloat() * 0.4f);
        }
    }
}
