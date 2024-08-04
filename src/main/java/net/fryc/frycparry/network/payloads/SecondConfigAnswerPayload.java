package net.fryc.frycparry.network.payloads;

import net.fryc.frycparry.network.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record SecondConfigAnswerPayload(boolean enReflex, boolean enParry, boolean enCounterattack,
                                       int dualWieldingSettings, int shieldEnchantability) implements CustomPayload {

    public static final CustomPayload.Id<SecondConfigAnswerPayload> ID = new CustomPayload.Id<>(ModPackets.SECOND_ANSWER_CONFIG_ID);
    public static final PacketCodec<RegistryByteBuf, SecondConfigAnswerPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, SecondConfigAnswerPayload::enReflex,
            PacketCodecs.BOOL, SecondConfigAnswerPayload::enParry,
            PacketCodecs.BOOL, SecondConfigAnswerPayload::enCounterattack,
            PacketCodecs.INTEGER, SecondConfigAnswerPayload::dualWieldingSettings,
            PacketCodecs.INTEGER, SecondConfigAnswerPayload::shieldEnchantability,
            SecondConfigAnswerPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
