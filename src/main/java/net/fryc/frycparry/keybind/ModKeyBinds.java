package net.fryc.frycparry.keybind;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fryc.frycparry.util.ParryHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {
    public static final String KEY_CATEGORY_SWORDPARRY = "key.category.frycparry.key_category_swordparry";
    public static final String KEY_PARRY = "key.frycparry.key_parry";

    public static KeyBinding parrykey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(parrykey.wasPressed()) {
                ClientPlayerEntity player = client.player;
                if(player != null && client.interactionManager != null){
                    if(player.getMainHandStack().getUseAction() == UseAction.BLOCK || player.getOffHandStack().getUseAction() == UseAction.BLOCK){
                        if(player.getMainHandStack().getItem() instanceof ShieldItem){
                            client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                        }
                        else if(player.getOffHandStack().getItem() instanceof ShieldItem){
                            client.interactionManager.interactItem(client.player, Hand.OFF_HAND);
                        }
                        else if(player.getOffHandStack().isEmpty()){
                            client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                        }
                        else if(ParryHelper.checkDualWieldingWeapons(player) && player.getMainHandStack().getUseAction() == UseAction.BLOCK){
                            if(player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem())){
                                client.interactionManager.interactItem(client.player, Hand.OFF_HAND);
                            }
                            else{
                                client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                            }
                        }
                        else if(ParryHelper.checkDualWieldingItems(player) && player.getMainHandStack().getUseAction() == UseAction.BLOCK){
                            client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                        }
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

        registerKeyInputs();
    }
}
