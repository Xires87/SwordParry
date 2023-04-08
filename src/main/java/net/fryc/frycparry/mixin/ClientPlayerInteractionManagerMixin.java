package net.fryc.frycparry.mixin;

import net.fryc.frycparry.util.IsParry;
import net.fryc.frycparry.util.ParryInteraction;
import net.fryc.frycparry.util.ParryItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
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

    @Shadow
    private void sendSequencedPacket(ClientWorld world, SequencedPacketCreator packetCreator){
    }

    public ActionResult interactItemParry(PlayerEntity player, Hand hand) {
        if (this.gameMode == GameMode.SPECTATOR) {
            return ActionResult.PASS;
        } else {
            this.syncSelectedSlot();
            this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch(), player.isOnGround()));
            MutableObject<ActionResult> mutableObject = new MutableObject();
            this.sendSequencedPacket(this.client.world, (sequence) -> {
                PlayerInteractItemC2SPacket playerInteractItemC2SPacket = new PlayerInteractItemC2SPacket(hand, sequence);
                ((IsParry) playerInteractItemC2SPacket).isParry();
                ItemStack itemStack = player.getStackInHand(hand);
                if (player.getItemCooldownManager().isCoolingDown(itemStack.getItem())) {
                    mutableObject.setValue(ActionResult.PASS);
                    return playerInteractItemC2SPacket;
                } else {
                    TypedActionResult<ItemStack> typedActionResult;
                    if(itemStack.getItem() instanceof ToolItem tool){
                        typedActionResult = ((ParryItem) tool).useParry(this.client.world, player, hand);
                    }
                    else {
                        typedActionResult = TypedActionResult.fail(itemStack);
                    }
                    ItemStack itemStack2 = (ItemStack)typedActionResult.getValue();
                    if (itemStack2 != itemStack) {
                        player.setStackInHand(hand, itemStack2);
                    }

                    mutableObject.setValue(typedActionResult.getResult());
                    return playerInteractItemC2SPacket;
                }
            });
            return (ActionResult)mutableObject.getValue();
        }
    }
}
