package net.fryc.frycparry.action;

import net.fryc.frycmua.action.ItemUseInstance;
import net.fryc.frycmua.action.registry.UseActionRegistries;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.client.ClientParryHelper;
import net.fryc.frycparry.util.interfaces.HasParryCooldownManager;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;

public class FrycParryUseActions {

    public static ItemUseInstance FRYCPARRY_BLOCK = new ItemUseInstance(
            Item.class,
            true,
            (item, world, playerEntity, hand) -> {
                ItemStack itemStack = playerEntity.getStackInHand(hand);
                if(parryingIsNotPossible(itemStack, playerEntity, hand)) return TypedActionResult.fail(playerEntity.getStackInHand(hand));

                if(ParryHelper.canParryWithoutShield(playerEntity)){
                    //((CanBlock) playerEntity).setCurrentHandParry(hand);
                    //((CanBlock) playerEntity).setBlockingDataToTrue();
                    playerEntity.setCurrentHand(hand);
                    return TypedActionResult.consume(itemStack);
                }
                else return TypedActionResult.fail(playerEntity.getStackInHand(hand));
            },
            (world, livingEntity, itemStack, i) -> {},
            (itemStack, world, livingEntity, i) -> {
                //((CanBlock) livingEntity).setBlockingDataToFalse();

                //if player stops blocking after parry, cooldown is shorter (and depends on item used)
                if(livingEntity instanceof ServerPlayerEntity player){
                    ((HasParryCooldownManager) player).getParryCooldownManager().addCooldown(player, ParryHelper.getParryCooldown(player, itemStack.getItem()));
                }
            },
            (itemStack, world, livingEntity) -> {
                //((CanBlock) livingEntity).setBlockingDataToFalse();

                //if player stops blocking after parry, cooldown is shorter (and depends on item used)
                if(livingEntity instanceof ServerPlayerEntity player){
                    ((HasParryCooldownManager) player).getParryCooldownManager().addCooldown(player, ParryHelper.getParryCooldown(player, itemStack.getItem()));
                }

                return itemStack;
            },
            (itemStack, livingEntity) -> ((ParryItem) itemStack.getItem()).getParryAttributes().getMaxUseTimeParry(),
            itemStack -> UseAction.BLOCK,
            itemStack -> false,
            (player, hand) -> {
                if(player.getWorld().isClient()) {
                    return ClientParryHelper.canParry((ClientPlayerEntity) player);
                }

                return true;
            }
    );

    public static void registerFrycParryUseActions() {
        UseActionRegistries.registerUseAction(Identifier.ofVanilla("block"), FRYCPARRY_BLOCK);
    }

    private static boolean parryingIsNotPossible(ItemStack stack, PlayerEntity user, Hand hand){
        return !ParryHelper.isReadyToBlock(user) ||
                ParryHelper.isItemParryDisabledWithConfig(user.getWorld(), stack) ||
                user.hasStatusEffect(ModEffects.DISARMED) ||
                hand == Hand.OFF_HAND;
    }
}
