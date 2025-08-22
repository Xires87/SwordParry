package net.fryc.frycparry.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fryc.frycparry.util.TextHelper;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ParryAttibutesCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("parryAttributes")).executes((context) -> {
            return execute((ServerCommandSource)context.getSource(), ((ServerCommandSource)context.getSource()).getEntityOrThrow());
        })));
    }

    private static int execute(ServerCommandSource source, Entity target) {
        if(target instanceof ServerPlayerEntity player){
            if(!player.getMainHandStack().isEmpty()){
                player.sendMessage(Text.literal(player.getMainHandStack().getName().getString() + " " + TextHelper.PARRY_ATTRIBUTES.getString().toLowerCase() +":"));
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
