package com.remotestorage.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record CacheRequestPayload() implements CustomPayload {
    public static final Id<CacheRequestPayload> PACKET_ID = new Id<>(Identifier.of("remotestorage", "cache_request"));
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
    
    public static final PacketCodec<RegistryByteBuf, CacheRequestPayload> CODEC = PacketCodec.unit(new CacheRequestPayload());
}
