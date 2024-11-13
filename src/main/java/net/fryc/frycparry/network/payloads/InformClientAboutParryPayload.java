package net.fryc.frycparry.network.payloads;

import net.fryc.frycparry.network.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record InformClientAboutParryPayload(int ticks) implements CustomPayload {

    public static final Id<InformClientAboutParryPayload> ID = new Id<>(ModPackets.INFORM_CLIENT_ABOUT_PARRY_ID);
    public static final PacketCodec<RegistryByteBuf, InformClientAboutParryPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, InformClientAboutParryPayload::ticks,
            InformClientAboutParryPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}