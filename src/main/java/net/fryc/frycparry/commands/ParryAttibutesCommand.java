package net.fryc.frycparry.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import oshi.util.tuples.Quartet;

import java.util.Iterator;
import java.util.Map;

public class ParryAttibutesCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("parryAttributes")).executes((context) -> {
            return execute((ServerCommandSource)context.getSource(), ((ServerCommandSource)context.getSource()).getEntityOrThrow());
        })));
    }

    private static int execute(ServerCommandSource source, Entity target) {
        if(target instanceof ServerPlayerEntity player){
            if(!player.getMainHandStack().isEmpty()){
                int reflexLevel = EnchantmentHelper.getLevel(player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(ModEnchantments.REFLEX), player.getMainHandStack());
                ParryAttributes attr = ((ParryItem) player.getMainHandStack().getItem()).getParryAttributes();
                int blockDelay = attr.getBlockDelay() - reflexLevel;
                int parryTicks = blockDelay < 0 ? attr.getParryTicks() + Math.abs(blockDelay) : attr.getParryTicks();
                blockDelay = Math.max(blockDelay, 0);

                player.sendMessage(Text.literal(player.getMainHandStack().getName().getString() + " parry attributes:"));
                player.sendMessage(Text.literal("======   ======"));
                player.sendMessage(Text.literal("Stable: " + !attr.shouldStopUsingItemAfterBlockOrParry()));
                player.sendMessage(Text.literal("Parry ticks: " + parryTicks));
                player.sendMessage(Text.literal("Block delay: " + blockDelay));
                player.sendMessage(Text.literal("Explosion block delay: " + attr.getExplosionBlockDelay()));

                player.sendMessage(Text.literal("Melee damage protection: " + (1.0F - attr.getMeleeDamageTakenAfterBlock())*100 + "%"));
                player.sendMessage(Text.literal("Projectile damage protection: " + (1.0F - attr.getProjectileDamageTakenAfterBlock())*100 + "%"));
                player.sendMessage(Text.literal("Explosion damage protection: " + (1.0F - attr.getExplosionDamageTakenAfterBlock())*100 + "%"));

                player.sendMessage(Text.literal("Base cooldown: " + getCooldown(player, attr.getCooldownAfterInterruptingBlockAction())/20 + "s"));
                player.sendMessage(Text.literal("Cooldown after parry: " + getCooldown(player, attr.getCooldownAfterParryAction())/20 + "s"));
                player.sendMessage(Text.literal("Cooldown after attack: " + getCooldown(player, attr.getCooldownAfterAttack())/20 + "s"));

                player.sendMessage(Text.literal("Parry knockback: " + attr.getKnockbackAfterParryAction()));
                player.sendMessage(Text.literal("Parry effects:"));
                Iterator<Map.Entry<RegistryEntry<StatusEffect>, Quartet<Integer, Integer, Float, Float>>> iterator = attr.getParryEffectsIterator();
                while(iterator.hasNext()){
                    Map.Entry<RegistryEntry<StatusEffect>, Quartet<Integer, Integer, Float, Float>> entry = iterator.next();
                    player.sendMessage(
                            Text.literal(
                                    "  " + entry.getKey().value().getName().getString() +
                                            ": Duration - " + entry.getValue().getA() +
                                            " Amplifier - " + entry.getValue().getB() +
                                            " Chance -  " + entry.getValue().getC() +
                                            " Enchantment modifier - " + entry.getValue().getD()
                            ).formatted(Formatting.AQUA)
                    );
                }
            }
            else {
                source.sendFeedback(() -> {
                    return Text.literal("Your main hand is empty!").formatted(Formatting.RED);
                }, false);
            }
        }
        else {
            source.sendFeedback(() -> {
                return Text.literal("Unable to execute command").formatted(Formatting.RED);
            }, false);
        }



        return 0;
    }

    private static float getCooldown(PlayerEntity player, float cooldown){
        return cooldown < 0 ? (player.getAttackCooldownProgressPerTick() - 1) * Math.abs(cooldown) : cooldown;
    }
}
