package net.fryc.frycparry.keybind;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fryc.frycparry.FrycParry;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.client.ClientParryHelper;
import net.fryc.frycparry.util.interfaces.CanBlock;
import net.fryc.frycparry.util.interfaces.ParryInteraction;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ShieldItem;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {
    public static final String KEY_CATEGORY_SWORDPARRY = "key.category.frycparry.key_category_swordparry";
    public static final String KEY_PARRY = "key.frycparry.key_parry";
    public static final String KEY_DONT_PARRY = "key.frycparry.key_dont_parry";

    public static KeyBinding parrykey;
    public static KeyBinding dontParryKey;

    private static boolean playerTriedToUseItem = false;
    private static boolean dontParryKeyPressed = false;


    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if(player != null && client.interactionManager != null){
                handleDontParryKey(player);

                handleParryKey(client, player, client.interactionManager, parrykey.isUnbound());
            }
        });
    }

    public static void register() {
        parrykey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_PARRY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                KEY_CATEGORY_SWORDPARRY
        ));
        dontParryKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DONT_PARRY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                KEY_CATEGORY_SWORDPARRY
        ));

        registerKeyInputs();
    }

    private static boolean isDontParryKeyPressed(){
        if(FrycParry.config.client.holdDontUseParryKey) return dontParryKey.isPressed();
        return dontParryKeyPressed;
    }

    private static void handleDontParryKey(ClientPlayerEntity player){
        if(dontParryKey.wasPressed()){
            dontParryKeyPressed = !dontParryKeyPressed;
            if(!FrycParry.config.client.holdDontUseParryKey) player.sendMessage(Text.of("Prevent using parry: " + dontParryKeyPressed), true);
        }
    }

    private static void handleParryKey(MinecraftClient client, ClientPlayerEntity player, ClientPlayerInteractionManager manager, boolean rightClick){
        if(parrykey.isPressed() || (rightClick && client.options.useKey.isPressed())) {
            doWhenParryKeyPressed(client, player, manager);
        }
        else {
            doWhenParryKeyNotPressed(client, player, manager);
        }
    }

    private static void doWhenParryKeyPressed(MinecraftClient client, ClientPlayerEntity player, ClientPlayerInteractionManager manager){
        if(!player.isUsingItem() && !player.hasStatusEffect(ModEffects.DISARMED) && ParryHelper.isReadyToBlock(player)){
            if(!isDontParryKeyPressed()){
                playerTriedToUseItem = true;
                if(player.getMainHandStack().getItem() instanceof ShieldItem && !player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem())){
                    manager.interactItem(client.player, Hand.MAIN_HAND);
                    client.gameRenderer.firstPersonRenderer.resetEquipProgress(Hand.MAIN_HAND);
                }
                else if(player.getOffHandStack().getItem() instanceof ShieldItem && !player.getItemCooldownManager().isCoolingDown(player.getOffHandStack().getItem())){
                    manager.interactItem(client.player, Hand.OFF_HAND);
                    client.gameRenderer.firstPersonRenderer.resetEquipProgress(Hand.OFF_HAND);
                }
                else{
                    if(ClientParryHelper.canParry(player)){ // <-- checking client sided config
                        if(ParryHelper.canParryWithoutShield(player)){ // <-- checking server sided config
                            if(!player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem())){
                                if(((ParryItem) player.getMainHandStack().getItem()).getUseParryAction(player.getMainHandStack()) == UseAction.BLOCK){
                                    ((ParryInteraction) manager).interactItemParry(client.player, Hand.MAIN_HAND);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void doWhenParryKeyNotPressed(MinecraftClient client, ClientPlayerEntity player, ClientPlayerInteractionManager manager){
        if(!player.isUsingItem()){
            if(playerTriedToUseItem){
                ((CanBlock) player).setBlockingDataToFalse();
                ((CanBlock) player).setParryDataToFalse();
                playerTriedToUseItem = false;
            }
        }
        else if(((CanBlock) player).getBlockingDataValue()){
            ((ParryInteraction) manager).stopUsingItemParry(player); // <---- it sends STOP_BLOCKING_ID packet
        }
    }
}
