package net.fryc.frycparry.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;

public class DisarmedEffect extends StatusEffect {
    protected DisarmedEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if(entity instanceof PlayerEntity player){
            int duration = player.getActiveStatusEffects().get(ModEffects.DISARMED).getDuration();
            ItemStack mainItem = player.getMainHandStack();
            ItemStack offItem = player.getOffHandStack();

            if(!offItem.isEmpty()){
                if(isShieldOrBowOrWeapon(offItem.getItem())){
                    player.getItemCooldownManager().set(offItem.getItem(), duration);
                }
            }
            if(!mainItem.isEmpty()){
                if(isShieldOrBowOrWeapon(offItem.getItem())){
                    player.getItemCooldownManager().set(mainItem.getItem(), duration);
                }
            }
        }
        super.onApplied(entity, attributes, amplifier);
    }

    private boolean isShieldOrBowOrWeapon(Item item){
        return item instanceof ShieldItem  || item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem || item instanceof SwordItem || item instanceof AxeItem;
    }

}
