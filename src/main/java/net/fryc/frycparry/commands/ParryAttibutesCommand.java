package net.fryc.frycparry.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.util.TextHelper;
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
                player.sendMessage(Text.literal(player.getMainHandStack().getName().getString() + " parry attributes:"));
                player.sendMessage(Text.literal("======   ======"));
                TextHelper.getParryAttributesText(player.getMainHandStack(), player, true).forEach(player::sendMessage);
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
