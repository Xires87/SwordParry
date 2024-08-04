package net.fryc.frycparry.network.payloads;

import net.fryc.frycparry.network.ModPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ResetLastAttackedTicksPayload(boolean bl) implements CustomPayload {

    public static final Id<ResetLastAttackedTicksPayload> ID = new Id<>(ModPackets.RESET_LAST_ATTACKED_TICKS_ID);
    public static final PacketCodec<RegistryByteBuf, ResetLastAttackedTicksPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, ResetLastAttackedTicksPayload::bl, ResetLastAttackedTicksPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
