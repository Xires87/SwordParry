package net.fryc.frycparry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.keybind.ModKeyBinds;
import net.fryc.frycparry.network.ModPackets;
import net.fryc.frycparry.util.ParryHelper;
import net.fryc.frycparry.util.TextHelper;
import net.fryc.frycparry.util.client.HudRenderingHelper;
import net.fryc.frycparry.util.interfaces.HasParryCooldownManager;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ShieldItem;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FrycParryClient implements ClientModInitializer {

    public static final Identifier EMPTY_SHIELD_TEXTURE = Identifier.of(FrycParry.MOD_ID, "textures/gui/crosshair/shield_empty.png");
    public static final Identifier FULL_SHIELD_TEXTURE = Identifier.of(FrycParry.MOD_ID, "textures/gui/crosshair/shield_full.png");

    public static final Identifier PARRY_INDICATOR_TEXTURE = Identifier.of(FrycParry.MOD_ID, "textures/gui/crosshair/indicator_parry.png");
    public static final Identifier BLOCK_INDICATOR_TEXTURE = Identifier.of(FrycParry.MOD_ID, "textures/gui/crosshair/indicator_block.png");
    public static final Identifier DELAY_INDICATOR_TEXTURE = Identifier.of(FrycParry.MOD_ID, "textures/gui/crosshair/indicator_delay.png");


    @Override
    public void onInitializeClient() {

        ModPackets.registerS2CPackets();
        //ModKeyBinds.register();

        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if(FrycParry.config.client.showParryAttributesInTooltips){
                ClientWorld world = MinecraftClient.getInstance().world;
                ClientPlayerEntity player = MinecraftClient.getInstance().player;
                if(world != null && player != null){
                    ParryAttributes attr = ((ParryItem) stack.getItem()).getParryAttributes();
                    if(!attr.getId().equals("default")){
                        if(ParryHelper.isItemParryEnabled(stack) && !ParryHelper.isItemParryDisabledWithConfig(world, stack)){
                            if(Screen.hasControlDown()){
                                lines.addAll(TextHelper.getParryAttributesText(stack, player, false));
                            }
                            else {
                                lines.add(Text.literal(TextHelper.PARRY_ATTRIBUTES.getString() + " <CTRL>"));
                            }
                        }
                    }
                }
            }
        });

        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity player = client.player;
            if(player != null){
                int l = (int) (((HasParryCooldownManager) player).getParryCooldownManager().getCooldownProgress() * 13);
                if(l < 13){
                    HudRenderingHelper.drawFullIcon(context, EMPTY_SHIELD_TEXTURE);
                    HudRenderingHelper.drawPartialIcon(context, FULL_SHIELD_TEXTURE, l, 12);
                }
                else if(FrycParry.config.client.showParryIndicator){
                    if(player.isBlocking()){
                        ParryItem item = player.getOffHandStack().getItem() instanceof ShieldItem ?
                                (ParryItem) player.getOffHandStack().getItem() :
                                (ParryItem) player.getMainHandStack().getItem();


                        int parryTicks = item.getParryAttributes().getParryTicks();
                        int blockDelay = item.getParryAttributes().getBlockDelay() - ModEnchantments.getReflexEnchantment(player);
                        parryTicks += Math.abs(blockDelay);

                        HudRenderingHelper.drawFullIcon(context, getParryIndicatorTexture(blockDelay, parryTicks, player.getItemUseTime()));
                    }
                }
            }
        });

    }

    private static Identifier getParryIndicatorTexture(int blockDelay, int parryTicks, int useTime){
        if(blockDelay > useTime){
            return DELAY_INDICATOR_TEXTURE;
        }

        return parryTicks > useTime ? PARRY_INDICATOR_TEXTURE : BLOCK_INDICATOR_TEXTURE;
    }

}
