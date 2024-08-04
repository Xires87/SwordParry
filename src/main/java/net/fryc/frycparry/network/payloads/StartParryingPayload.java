package net.fryc.frycparry.network.payloads;

import net.fryc.frycparry.network.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record StartParryingPayload(boolean bl) implements CustomPayload {

    public static final Id<StartParryingPayload> ID = new Id<>(ModPackets.START_PARRYING_ID);
    public static final PacketCodec<RegistryByteBuf, StartParryingPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, StartParryingPayload::bl, StartParryingPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
