package net.fryc.frycparry.effects;

import net.fryc.frycparry.util.mixin_interfaces.TargetingMob;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

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
                player.getItemCooldownManager().set(offItem.getItem(), duration);
            }

            if(!mainItem.isEmpty()){
                player.getItemCooldownManager().set(mainItem.getItem(), duration);
            }
        }
        else if(entity instanceof MobEntity mob){
            mob.setAttacking(false);
            ((TargetingMob) mob).setLastTarget(mob.getTarget());
            mob.setTarget(null);
            if(((TargetingMob) mob).getLastTarget() != null){
                mob.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, ((TargetingMob) mob).getLastTarget().getPos());
            }
        }
        super.onApplied(entity, amplifier);
    }

}
