package net.fryc.frycparry.keybind;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fryc.frycparry.effects.ModEffects;
import net.fryc.frycparry.network.ModPackets;
import net.fryc.frycparry.util.CanBlock;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.ParryInteraction;
import net.fryc.frycparry.util.ParryItem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {
    public static final String KEY_CATEGORY_SWORDPARRY = "key.category.frycparry.key_category_swordparry";
    public static final String KEY_PARRY = "key.frycparry.key_parry";

    public static KeyBinding parrykey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(parrykey.isPressed()) {
                ClientPlayerEntity player = client.player;
                if(player != null && client.interactionManager != null){
                    if(!player.isUsingItem() && !player.hasStatusEffect(ModEffects.DISARMED)){
                        if(player.getMainHandStack().getUseAction() == UseAction.BLOCK){
                            client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                        }
                        else if(player.getOffHandStack().getUseAction() == UseAction.BLOCK){
                            client.interactionManager.interactItem(client.player, Hand.OFF_HAND);
                        }
                        else{
                            if(ParryHelper.canParry(player) && ((ParryItem) player.getMainHandStack().getItem()).getUseParryAction(player.getMainHandStack()) == UseAction.BLOCK){
                                ClientPlayNetworking.send(ModPackets.PARRY_ID, PacketByteBufs.create()); //<----- informs server that player pressed parry key
                                ((ParryInteraction) client.interactionManager).interactItemParry(client.player, Hand.MAIN_HAND);
                            }
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
