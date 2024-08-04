package net.fryc.frycparry.network.payloads;

import net.fryc.frycparry.network.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record FirstConfigAnswerPayload(boolean enSwordBlocking, boolean enAxeBlocking, boolean enPickaxeBlocking,
                                       boolean enShovelBlocking, boolean enHoeBlocking, boolean enOtherBlocking) implements CustomPayload {

    public static final CustomPayload.Id<FirstConfigAnswerPayload> ID = new CustomPayload.Id<>(ModPackets.FIRST_ANSWER_CONFIG_ID);
    public static final PacketCodec<RegistryByteBuf, FirstConfigAnswerPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enSwordBlocking,
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enAxeBlocking,
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enPickaxeBlocking,
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enShovelBlocking,
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enHoeBlocking,
            PacketCodecs.BOOL, FirstConfigAnswerPayload::enOtherBlocking,
            FirstConfigAnswerPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}