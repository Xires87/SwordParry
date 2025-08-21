package net.fryc.frycparry.util;

import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import oshi.util.tuples.Quartet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextHelper {

// TODO przetlumaczyc te tooltipy
    public static List<Text> getParryAttributesText(ItemStack stack, PlayerEntity player, boolean fullSize){
        ArrayList<Text> list = new ArrayList<>();

        ParryAttributes attr = ((ParryItem) stack.getItem()).getParryAttributes();
        int reflexLevel = EnchantmentHelper.getLevel(ModEnchantments.PREDICTION, stack);
        int blockDelay = attr.getBlockDelay() - reflexLevel;
        int parryTicks = blockDelay < 0 ? attr.getParryTicks() + Math.abs(blockDelay) : attr.getParryTicks();
        blockDelay = Math.max(blockDelay, 0);

        list.add(Text.literal("Stable: " + !attr.shouldStopUsingItemAfterBlockOrParry()));
        list.add(Text.literal("Parry ticks: " + parryTicks));
        if(fullSize || blockDelay > 0) list.add(Text.literal("Block delay: " + blockDelay));
        if(fullSize || attr.getExplosionBlockDelay() > 0) list.add(Text.literal("Explosion block delay: " + attr.getExplosionBlockDelay()));

        if(fullSize){
            list.add(Text.literal("Melee damage protection: " + getRoundedAsString((1.0F - attr.getMeleeDamageTakenAfterBlock())*100) + "%"));
            list.add(Text.literal("Projectile damage protection: " + getRoundedAsString((1.0F - attr.getProjectileDamageTakenAfterBlock())*100) + "%"));
            list.add(Text.literal("Explosion damage protection: " + getRoundedAsString((1.0F - attr.getExplosionDamageTakenAfterBlock())*100) + "%"));

            list.add(Text.literal("Base cooldown: " + getRoundedAsString(getCooldown(player, attr.getCooldownAfterInterruptingBlockAction())/20) + "s"));
            list.add(Text.literal("Cooldown after parry: " + getRoundedAsString(getCooldown(player, attr.getCooldownAfterParryAction())/20) + "s"));
            list.add(Text.literal("Cooldown after attack: " + getRoundedAsString(getCooldown(player, attr.getCooldownAfterAttack())/20) + "s"));
        }
        else {
            boolean alwaysShowCd = player.getMainHandStack().equals(stack);

            list.add(Text.literal("Protection: " +
                    getRoundedAsString((1.0F - attr.getMeleeDamageTakenAfterBlock())*100) + "% | " +
                    getRoundedAsString((1.0F - attr.getProjectileDamageTakenAfterBlock())*100) + "% | " +
                    (attr.getExplosionBlockDelay() < 0 ? "-" : getRoundedAsString((1.0F - attr.getExplosionDamageTakenAfterBlock())*100) + "%")));

            list.add(Text.literal("Cooldown: " +
                    (alwaysShowCd || attr.getCooldownAfterInterruptingBlockAction() >= 0 ? getRoundedAsString(getCooldown(player, attr.getCooldownAfterInterruptingBlockAction())/20) + "s | " : "- | ") +
                    (alwaysShowCd || attr.getCooldownAfterParryAction() >= 0 ? getRoundedAsString(getCooldown(player, attr.getCooldownAfterParryAction())/20) + "s | " : "- | ") +
                    (alwaysShowCd || attr.getCooldownAfterAttack() >= 0 ? getRoundedAsString(getCooldown(player, attr.getCooldownAfterAttack())/20) + "s" : "-")));
        }

        list.add(Text.literal("Parry knockback: " + attr.getKnockbackAfterParryAction()));

        Set<Map.Entry<StatusEffect, Quartet<Integer, Integer, Float, Float>>> effectSet = attr.getParryEffectsCopy().entrySet();
        if(!effectSet.isEmpty()){
            list.add(Text.literal("Parry effects:"));

            effectSet.forEach(entry -> {
                if(fullSize){
                    list.add(Text.literal(
                            "  " + entry.getKey().getName().getString() +
                                    ": Duration - " + entry.getValue().getA() +
                                    " Amplifier - " + entry.getValue().getB() +
                                    " Chance -  " + entry.getValue().getC() +
                                    " Enchantment modifier - " + entry.getValue().getD()
                    ).formatted(Formatting.AQUA));
                }
                else {
                    list.add(Text.literal(" " + entry.getKey().getName().getString() +
                            " " + entry.getValue().getB() + " (" + entry.getValue().getC()*100 + "%)").formatted(Formatting.AQUA));
                }
            });
        }

        return list;
    }

    public static float getCooldown(PlayerEntity player, float cooldown){
        return cooldown < 0 ? (player.getAttackCooldownProgressPerTick() - 1) * Math.abs(cooldown) : cooldown;
    }

    public static String getRoundedAsString(float number){
        String toRound = String.valueOf(number);
        int endIndex = toRound.contains(".") ? Math.min(toRound.length(), toRound.indexOf('.') + 3) : toRound.length();

        return toRound.substring(0, endIndex);
    }
}
