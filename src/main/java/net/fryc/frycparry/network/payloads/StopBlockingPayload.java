package net.fryc.frycparry.network.payloads;

import net.fryc.frycparry.network.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record StopBlockingPayload(boolean bl) implements CustomPayload {

    public static final CustomPayload.Id<StopBlockingPayload> ID = new CustomPayload.Id<>(ModPackets.STOP_BLOCKING_ID);
    public static final PacketCodec<RegistryByteBuf, StopBlockingPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, StopBlockingPayload::bl, StopBlockingPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
