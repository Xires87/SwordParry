package net.fryc.frycparry.effects;

import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
        super.onApplied(entity, amplifier);
    }

    private static boolean isShieldOrBowOrTool(World world, ItemStack stack){
        Item item = stack.getItem();
        return item instanceof ShieldItem || ParryHelper.hasAttackSpeedAttribute(stack) || !ParryHelper.isItemParryDisabledWithConfig(world, stack);
    }

}
