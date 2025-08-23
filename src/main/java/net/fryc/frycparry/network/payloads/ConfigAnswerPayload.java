package net.fryc.frycparry.network.payloads;

import net.fryc.frycparry.network.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ConfigAnswerPayload(boolean enSwordBlocking, boolean enAxeBlocking, boolean enPickaxeBlocking,
                                  boolean enShovelBlocking, boolean enHoeBlocking, boolean enOtherBlocking,
                                  boolean enMaceBlocking, int dualWieldingSettings, int shieldEnchantability) implements CustomPayload {

    public static final CustomPayload.Id<ConfigAnswerPayload> ID = new CustomPayload.Id<>(ModPackets.ANSWER_CONFIG_ID);
    /*
    public static final PacketCodec<RegistryByteBuf, FirstConfigAnswerPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enSwordBlocking,
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enAxeBlocking,
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enPickaxeBlocking,
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enShovelBlocking,
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enHoeBlocking,
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enOtherBlocking,
            FirstConfigAnswerPayload::new
    );

     */

    public static final PacketCodec<RegistryByteBuf, ConfigAnswerPayload> CODEC = new PacketCodec<>() {
        @Override
        public ConfigAnswerPayload decode(RegistryByteBuf buf) {
            boolean enSwordBlocking = PacketCodecs.BOOL.decode(buf);
            boolean enAxeBlocking = PacketCodecs.BOOL.decode(buf);
            boolean enPickaxeBlocking = PacketCodecs.BOOL.decode(buf);
            boolean enShovelBlocking = PacketCodecs.BOOL.decode(buf);
            boolean enHoeBlocking = PacketCodecs.BOOL.decode(buf);
            boolean enOtherBlocking = PacketCodecs.BOOL.decode(buf);
            boolean enMaceBlocking = PacketCodecs.BOOL.decode(buf);

            int dualWieldingSettings = PacketCodecs.INTEGER.decode(buf);
            int shieldEnchantability = PacketCodecs.INTEGER.decode(buf);

            return new ConfigAnswerPayload(enSwordBlocking, enAxeBlocking, enPickaxeBlocking, enShovelBlocking, enHoeBlocking, enOtherBlocking, enMaceBlocking, dualWieldingSettings, shieldEnchantability);
        }

        @Override
        public void encode(RegistryByteBuf buf, ConfigAnswerPayload value) {
            PacketCodecs.BOOL.encode(buf, value.enSwordBlocking());
            PacketCodecs.BOOL.encode(buf, value.enAxeBlocking());
            PacketCodecs.BOOL.encode(buf, value.enPickaxeBlocking());
            PacketCodecs.BOOL.encode(buf, value.enShovelBlocking());
            PacketCodecs.BOOL.encode(buf, value.enHoeBlocking());
            PacketCodecs.BOOL.encode(buf, value.enOtherBlocking());
            PacketCodecs.BOOL.encode(buf, value.enMaceBlocking());

            PacketCodecs.INTEGER.encode(buf, value.dualWieldingSettings());
            PacketCodecs.INTEGER.encode(buf, value.shieldEnchantability());
        }
    };



    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}