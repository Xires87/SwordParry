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
import net.minecraft.client.network.ClientPlayerEntity;
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

    private static boolean bl = false;
    private static boolean dontParryKeyPressed = false;


    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            boolean rightClick = parrykey.isUnbound();
            if(player != null && client.interactionManager != null){
                if(dontParryKey.wasPressed()){
                    dontParryKeyPressed = !dontParryKeyPressed;
                    if(!FrycParry.config.client.holdDontUseParryKey) player.sendMessage(Text.of("Prevent using parry: " + dontParryKeyPressed), true);
                }

                if(parrykey.isPressed() || (rightClick && client.options.useKey.isPressed())) {
                    if(!player.isUsingItem() && !player.hasStatusEffect(ModEffects.DISARMED)){
                        if(!isDontParryKeyPressed()){
                            bl = true;
                            if(player.getMainHandStack().getUseAction() == UseAction.BLOCK && !player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem())){
                                if(player.getMainHandStack().getItem() instanceof ShieldItem){
                                    client.gameRenderer.firstPersonRenderer.resetEquipProgress(Hand.MAIN_HAND);
                                }
                                client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                            }
                            else if(player.getOffHandStack().getUseAction() == UseAction.BLOCK && !player.getItemCooldownManager().isCoolingDown(player.getOffHandStack().getItem())){
                                if(player.getOffHandStack().getItem() instanceof ShieldItem){
                                    client.gameRenderer.firstPersonRenderer.resetEquipProgress(Hand.OFF_HAND);
                                }
                                client.interactionManager.interactItem(client.player, Hand.OFF_HAND);
                            }
                            else{
                                if(ClientParryHelper.canParry(player)){ // <-- checking client sided config
                                    if(ParryHelper.canParryWithoutShield(player)){ // <-- checking server sided config
                                        if(!player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem())){
                                            if(((ParryItem) player.getMainHandStack().getItem()).getUseParryAction(player.getMainHandStack()) == UseAction.BLOCK){
                                                ((ParryInteraction) client.interactionManager).interactItemParry(client.player, Hand.MAIN_HAND);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    if(!player.isUsingItem()){
                        if(bl){
                            ((CanBlock) player).setBlockingDataToFalse();
                            ((CanBlock) player).setParryDataToFalse();
                            bl = false;
                        }
                    }
                    else if(((CanBlock) player).getBlockingDataValue()){
                        ((ParryInteraction) client.interactionManager).stopUsingItemParry(player); // <---- it sends STOP_BLOCKING_ID packet
                    }
                }
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
}
