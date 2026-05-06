package net.fryc.frycparry.util;

import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.util.mixin_interfaces.ParryItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import oshi.util.tuples.Quartet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextHelper {

    public static final Text PARRY_ATTRIBUTES = Text.translatable("text.frycparry.parry_attributes");

    public static final Text STABLE = Text.translatable("text.frycparry.parry_attributes.stable");
    public static final Text PARRY_TICKS = Text.translatable("text.frycparry.parry_attributes.parry_ticks");
    public static final Text BLOCK_DELAY = Text.translatable("text.frycparry.parry_attributes.block_delay");
    public static final Text EXPLOSION_BLOCK_DELAY = Text.translatable("text.frycparry.parry_attributes.explosion_block_delay");

    public static final Text MELEE_DAMAGE_PROTECTION = Text.translatable("text.frycparry.parry_attributes.melee_damage_protection");
    public static final Text PROJECTILE_DAMAGE_PROTECTION = Text.translatable("text.frycparry.parry_attributes.projectile_damage_protection");
    public static final Text EXPLOSION_DAMAGE_PROTECTION = Text.translatable("text.frycparry.parry_attributes.explosion_damage_protection");
    public static final Text PROTECTION = Text.translatable("text.frycparry.parry_attributes.protection");

    public static final Text BASE_COOLDOWN = Text.translatable("text.frycparry.parry_attributes.base_cooldown");
    public static final Text COOLDOWN_AFTER_PARRY = Text.translatable("text.frycparry.parry_attributes.cooldown_after_parry");
    public static final Text COOLDOWN_AFTER_ATTACK = Text.translatable("text.frycparry.parry_attributes.cooldown_after_attack");
    public static final Text COOLDOWN = Text.translatable("text.frycparry.parry_attributes.cooldown");

    public static final Text PARRY_EFFECTS = Text.translatable("text.frycparry.parry_attributes.parry_effects");
    public static final Text DURATION = Text.translatable("text.frycparry.parry_attributes.duration");
    public static final Text AMPLIFIER = Text.translatable("text.frycparry.parry_attributes.amplifier");
    public static final Text CHANCE = Text.translatable("text.frycparry.parry_attributes.chance");
    public static final Text ENCHANTMENT_MODIFIER = Text.translatable("text.frycparry.parry_attributes.enchantment_modifier");

    public static final Text SECONDS_SHORTENED = Text.translatable("text.frycparry.parry_attributes.seconds_shortened");
    public static final Text PARRY_KNOCKBACK = Text.translatable("text.frycparry.parry_attributes.parry_knockback");

    public static List<Text> getParryAttributesText(ItemStack stack, PlayerEntity player, boolean fullSize){
        ArrayList<Text> list = new ArrayList<>();

        ParryAttributes attr = ((ParryItem) stack.getItem()).getParryAttributes();
        int reflexLevel = EnchantmentHelper.getLevel(player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(ModEnchantments.REFLEX), stack);
        int blockDelay = attr.getBlockDelay() - reflexLevel;
        int parryTicks = blockDelay < 0 ? attr.getParryTicks() + Math.abs(blockDelay) : attr.getParryTicks();
        blockDelay = Math.max(blockDelay, 0);

        list.add(Text.literal(STABLE.getString() + ": " + Text.translatable("text.frycparry.parry_attributes." + !attr.shouldStopUsingItemAfterBlockOrParry()).getString()));
        list.add(Text.literal(PARRY_TICKS.getString() + ": " + parryTicks));
        if(fullSize || blockDelay > 0) list.add(Text.literal(BLOCK_DELAY.getString() + ": " + blockDelay));
        if(fullSize || attr.getExplosionBlockDelay() > 0) list.add(Text.literal(EXPLOSION_BLOCK_DELAY.getString() + ": " + attr.getExplosionBlockDelay()));

        if(fullSize){
            list.add(Text.literal(MELEE_DAMAGE_PROTECTION.getString() + ": " + getRoundedAsString((1.0F - attr.getMeleeDamageTakenAfterBlock())*100) + "%"));
            list.add(Text.literal(PROJECTILE_DAMAGE_PROTECTION.getString() + ": " + getRoundedAsString((1.0F - attr.getProjectileDamageTakenAfterBlock())*100) + "%"));
            list.add(Text.literal(EXPLOSION_DAMAGE_PROTECTION.getString() + ": " + getRoundedAsString((1.0F - attr.getExplosionDamageTakenAfterBlock())*100) + "%"));

            list.add(Text.literal(BASE_COOLDOWN.getString() + ": " + getRoundedAsString(getCooldown(player, attr.getCooldownAfterInterruptingBlockAction())/20) + SECONDS_SHORTENED.getString()));
            list.add(Text.literal(COOLDOWN_AFTER_PARRY.getString() + ": " + getRoundedAsString(getCooldown(player, attr.getCooldownAfterParryAction())/20) + SECONDS_SHORTENED.getString()));
            list.add(Text.literal(COOLDOWN_AFTER_ATTACK.getString() + ": " + getRoundedAsString(getCooldown(player, attr.getCooldownAfterAttack())/20) + SECONDS_SHORTENED.getString()));
        }
        else {
            boolean alwaysShowCd = player.getMainHandStack().equals(stack);

            list.add(Text.literal(PROTECTION.getString() + ": " +
                    getRoundedAsString((1.0F - attr.getMeleeDamageTakenAfterBlock())*100) + "% | " +
                    getRoundedAsString((1.0F - attr.getProjectileDamageTakenAfterBlock())*100) + "% | " +
                    (attr.getExplosionBlockDelay() < 0 ? "-" : getRoundedAsString((1.0F - attr.getExplosionDamageTakenAfterBlock())*100) + "%")));

            list.add(Text.literal(COOLDOWN.getString() + ": " +
                    (alwaysShowCd || attr.getCooldownAfterInterruptingBlockAction() >= 0 ?
                            getRoundedAsString(getCooldown(player, attr.getCooldownAfterInterruptingBlockAction())/20) + SECONDS_SHORTENED.getString() + " | " :
                            "- | ") +
                    (alwaysShowCd || attr.getCooldownAfterParryAction() >= 0 ?
                            getRoundedAsString(getCooldown(player, attr.getCooldownAfterParryAction())/20) + SECONDS_SHORTENED.getString() + " | " :
                            "- | ") +
                    (alwaysShowCd || attr.getCooldownAfterAttack() >= 0 ?
                            getRoundedAsString(getCooldown(player, attr.getCooldownAfterAttack())/20) +
                                    SECONDS_SHORTENED.getString() : "-")));
        }

        list.add(Text.literal(PARRY_KNOCKBACK.getString() + ": " + attr.getKnockbackAfterParryAction()));

        Set<Map.Entry<RegistryEntry<StatusEffect>, Quartet<Integer, Integer, Float, Float>>> effectSet = attr.getParryEffectsCopy().entrySet();
        if(!effectSet.isEmpty()){
            list.add(Text.literal(PARRY_EFFECTS.getString() + ":"));

            effectSet.forEach(entry -> {
                if(fullSize){
                    list.add(Text.literal(
                            "  " + Text.translatable(entry.getKey().value().getTranslationKey()).getString() +
                                    ":" +  DURATION.getString() + " - " + entry.getValue().getA() +
                                    " " + AMPLIFIER.getString() + " - " + entry.getValue().getB() +
                                    " " + CHANCE.getString() + " - " + entry.getValue().getC() +
                                    " " + ENCHANTMENT_MODIFIER.getString() + " - " + entry.getValue().getD()
                    ).formatted(Formatting.AQUA));
                }
                else {
                    list.add(Text.literal(" " + Text.translatable(entry.getKey().value().getTranslationKey()).getString() +
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
