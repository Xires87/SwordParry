package net.fryc.frycparry.mixin.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fryc.frycparry.network.payloads.StartParryingPayload;
import net.fryc.frycparry.network.payloads.StopBlockingPayload;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.fryc.frycparry.util.interfaces.ParryInteraction;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.GameMode;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerInteractionManager.class)
abstract class ClientPlayerInteractionManagerMixin implements ParryInteraction {

    @Shadow private GameMode gameMode;
    @Shadow private @Final MinecraftClient client;

    @Shadow private @Final ClientPlayNetworkHandler networkHandler;

    @Shadow
    private void syncSelectedSlot() {
    }


    public ActionResult interactItemParry(PlayerEntity player, Hand hand) {
        if (this.gameMode == GameMode.SPECTATOR) {
            return ActionResult.PASS;
        } else {
            this.syncSelectedSlot();
            this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch(), player.isOnGround()));
            MutableObject<ActionResult> mutableObject = new MutableObject();
            ItemStack itemStack = player.getStackInHand(hand);
            if (player.getItemCooldownManager().isCoolingDown(itemStack.getItem())) {
                mutableObject.setValue(ActionResult.PASS);
                return (ActionResult)mutableObject.getValue();
            } else {
                TypedActionResult<ItemStack> typedActionResult;
                if(ParryHelper.isItemParryEnabled(itemStack)){
                    typedActionResult = ((ParryItem) itemStack.getItem()).useParry(this.client.world, player, hand);
                    ClientPlayNetworking.send(new StartParryingPayload(true));
                }
                else {
                    typedActionResult = TypedActionResult.fail(itemStack);
                }
                ItemStack itemStack2 = (ItemStack)typedActionResult.getValue();
                if (itemStack2 != itemStack) {
                    player.setStackInHand(hand, itemStack2);
                }

                mutableObject.setValue(typedActionResult.getResult());
            }
            return (ActionResult)mutableObject.getValue();
        }
    }

    public void stopUsingItemParry(PlayerEntity player) {
        this.syncSelectedSlot();
        ClientPlayNetworking.send(new StopBlockingPayload(true));
        ((CanBlock) player).stopUsingItemParry();
    }
}
