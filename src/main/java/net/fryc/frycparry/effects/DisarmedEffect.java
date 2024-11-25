package net.fryc.frycparry.effects;

import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.interfaces.TargetingMob;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.world.World;

public class DisarmedEffect extends StatusEffect {
    protected DisarmedEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    public void onApplied(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity player){
            int duration = player.getActiveStatusEffects().get(ModEffects.DISARMED).getDuration();
            ItemStack mainItem = player.getMainHandStack();
            ItemStack offItem = player.getOffHandStack();

            if(!offItem.isEmpty()){
                if(isShieldOrBowOrTool(entity.getWorld(), offItem)){
                    player.getItemCooldownManager().set(offItem.getItem(), duration);
                }
            }

            if(!mainItem.isEmpty()){
                if(isShieldOrBowOrTool(entity.getWorld(), mainItem)){
                    player.getItemCooldownManager().set(mainItem.getItem(), duration);
                }
            }
        }
        else if(entity instanceof MobEntity mob){
            mob.setAttacking(false);
            ((TargetingMob) mob).setLastTarget(mob.getTarget());
            mob.setTarget(null);
            if(mob.getTarget() != null){
                mob.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, mob.getTarget().getPos());
            }
        }
        super.onApplied(entity, amplifier);
    }

    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if(entity instanceof MobEntity mob){
            mob.setTarget(((TargetingMob) mob).getLastTarget());
            ((TargetingMob) mob).setLastTarget(null);
            mob.setAttacking(true);
        }

        super.onRemoved(entity, attributes, amplifier);
    }

    private static boolean isShieldOrBowOrTool(World world, ItemStack stack){
        return stack.getItem() instanceof ShieldItem || stack.getAttributeModifiers(EquipmentSlot.MAINHAND).keySet().contains(EntityAttributes.GENERIC_ATTACK_SPEED) ||
                !ParryHelper.isItemParryDisabledWithConfig(world, stack);
    }

}
