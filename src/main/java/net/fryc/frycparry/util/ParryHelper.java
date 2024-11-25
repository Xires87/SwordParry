package net.fryc.frycparry.util;

import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.attributes.ParryAttributes;
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
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import oshi.util.tuples.Quartet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ParryHelper {

    public static int dualWieldingSettings = FrycParry.config.server.enableBlockingWhenDualWielding;
    public static boolean enableBlockingWithSword = FrycParry.config.sword.enableBlockingWithSword;
    public static boolean enableBlockingWithAxe = FrycParry.config.axe.enableBlockingWithAxe;
    public static boolean enableBlockingWithPickaxe = FrycParry.config.pickaxe.enableBlockingWithPickaxe;
    public static boolean enableBlockingWithShovel = FrycParry.config.shovel.enableBlockingWithShovel;
    public static boolean enableBlockingWithHoe = FrycParry.config.hoe.enableBlockingWithHoe;
    public static boolean enableBlockingWithOtherTools = FrycParry.config.server.enableBlockingWithOtherTools;

    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> pickaxeParryEffects = new HashMap<>();
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> axeParryEffects = new HashMap<>();
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> swordParryEffects = new HashMap<>();
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> shovelParryEffects = new HashMap<>();
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> hoeParryEffects = new HashMap<>();
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> shieldParryEffects = new HashMap<>();
    public static HashMap<StatusEffect, Quartet<Integer, Integer, Float, Float>> parryEffects = new HashMap<>();

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
                return parryItem.getParryAttributes().getProjectileDamageTakenAfterBlock() <= 0f;
            }
            return parryItem.getParryAttributes().getMeleeDamageTakenAfterBlock() <= 0f;
        }
        return true;
    }

    /**
     *  !!! use only after attackWasBlocked() check !!!
     */
    public static boolean attackWasParried(DamageSource source, ItemStack stack, LivingEntity user){
        if(source.isIn(DamageTypeTags.IS_EXPLOSION)) return false;
        if(isItemParryEnabled(stack)){
            int maxUseTime = user.getActiveItem().getItem() instanceof ShieldItem ? user.getActiveItem().getMaxUseTime() : ((ParryItem) user.getActiveItem().getItem()).getParryAttributes().getMaxUseTimeParry();
            return maxUseTime - user.getItemUseTimeLeft() < ((ParryItem) stack.getItem()).getParryAttributes().getParryTicks() + ModEnchantments.getPredictionEnchantment(user);
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

    public static void applyParryEffects(LivingEntity user, LivingEntity attacker){
        //parry enchantment
        // TODO zmodyfikowac parry enchantment zeby mnoznik na 2 poziomie byl inny niz 2 (i moze configiem zeby mozna bylo zmienic)
        int parryEnchantmentLevel = ModEnchantments.getParryEnchantment(user);

        //variables for parry effects
        ParryAttributes parryAttributes = ((ParryItem) user.getActiveItem().getItem()).getParryAttributes();
        double knockback = parryAttributes.getKnockbackAfterParryAction();
        //float[] modifiers = new float[3];
        float knockbackModifier; // modifier for parry enchantment
        if(attacker instanceof PlayerEntity) {
            //modifiers[0] = 0.65F; modifiers[1] = 0.2F; modifiers[2] = 0.15F;
            knockbackModifier = 0.65F;
            knockback -= FrycParry.config.multiplayerModifiers.parryKnockbackStrengthForPlayersModifier;
        }
        else {
            //modifiers[0] = 0.95F; modifiers[1] = 0.3F; modifiers[2] = 0.22F;
            knockbackModifier = 0.95F;
        }

        //knockback
        if(knockback > 0){
            attacker.takeKnockback((knockback + parryEnchantmentLevel * knockbackModifier)/10, user.getX() - attacker.getX(), user.getZ() - attacker.getZ());
            attacker.velocityModified = true;
        }

        //applying status effects from parry attributes
        Iterator<Map.Entry<StatusEffect, Quartet<Integer, Integer, Float, Float>>> iterator = parryAttributes.getParryEffectsIterator();
        while(iterator.hasNext()){
            Map.Entry<StatusEffect, Quartet<Integer, Integer, Float, Float>> entry = iterator.next();
            float chance = entry.getValue().getC();
            if(chance >= 1.0F || ThreadLocalRandom.current().nextFloat() < chance){
                int duration = entry.getValue().getA() + (int)(entry.getValue().getA() * (parryEnchantmentLevel * entry.getValue().getD()));
                int amplifier = entry.getValue().getB() - (parryEnchantmentLevel == 2 ? 0 : 1); // TODO tutaj dac wartosc configowa zeby parry enchantment zwiekszal amplifier
                if(amplifier < 0) amplifier = 0;

                attacker.addStatusEffect(new StatusEffectInstance(entry.getKey(), duration, amplifier));
            }
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
            cooldown = ((ParryItem) item).getParryAttributes().getCooldownAfterParryAction();
        }
        else {
            cooldown = ((ParryItem) item).getParryAttributes().getCooldownAfterInterruptingBlockAction();
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

    // all sound methods are executed on SERVER
    public static void playBlockSound(LivingEntity entity){
        boolean shield = !ParryHelper.canParryWithoutShield(entity);
        if(shield){
            entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.SHIELD_BLOCK, SoundCategory.PLAYERS, 0.9f, 0.7f + entity.getWorld().random.nextFloat() * 0.4f);
        }
        else {
            entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.TOOL_BLOCK, SoundCategory.PLAYERS, 0.9f, 0.7f + entity.getWorld().random.nextFloat() * 0.4f);
        }
    }

    public static void playParrySound(LivingEntity entity){
        boolean shield = !ParryHelper.canParryWithoutShield(entity);
        if(shield){
            entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.SHIELD_PARRY, SoundCategory.PLAYERS, 1.3f, 0.8f + entity.getWorld().random.nextFloat() * 0.4f);
        }
        else {
            entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.TOOL_PARRY, SoundCategory.PLAYERS, 1.3f, 0.8f + entity.getWorld().random.nextFloat() * 0.4f);
        }
    }

    public static void playGuardBreakSound(LivingEntity entity){
        boolean tool = ParryHelper.canParryWithoutShield(entity);
        if(tool){
            entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.TOOL_GUARD_BREAK, SoundCategory.PLAYERS, 1.3f, 1.0f + entity.getWorld().random.nextFloat() * 0.4f);
        }
        else {
            entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.SHIELD_GUARD_BREAK, SoundCategory.PLAYERS, 1.3f, 1.0f + entity.getWorld().random.nextFloat() * 0.4f);
        }
    }
}
