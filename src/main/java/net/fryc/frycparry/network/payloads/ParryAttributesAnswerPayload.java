package net.fryc.frycparry.network.payloads;

import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.network.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

import java.util.HashMap;
import java.util.Map;

public record ParryAttributesAnswerPayload(Map<String, ParryAttributes> parryAttributesMap) implements CustomPayload {

    public static final CustomPayload.Id<ParryAttributesAnswerPayload> ID = new CustomPayload.Id<>(ModPackets.ANSWER_PARRY_ATTRIBUTES_ID);

    public static final PacketCodec<RegistryByteBuf, ParryAttributesAnswerPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.map(HashMap::new, PacketCodecs.STRING, ParryAttributes.PACKET_CODEC), ParryAttributesAnswerPayload::parryAttributesMap,
            ParryAttributesAnswerPayload::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

}
