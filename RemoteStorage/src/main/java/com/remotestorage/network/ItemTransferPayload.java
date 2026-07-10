package com.remotestorage.network;

import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ItemTransferPayload(int containerId, int slot, ItemStack stack, boolean deposit) implements CustomPayload {
    public static final Id<ItemTransferPayload> PACKET_ID = new Id<>(Identifier.of("remotestorage", "item_transfer"));
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
    
    public static final PacketCodec<RegistryByteBuf, ItemTransferPayload> CODEC = PacketCodec.tuple(
            PacketCodec.VAR_INT, ItemTransferPayload::containerId,
            PacketCodec.VAR_INT, ItemTransferPayload::slot,
            ItemStack.PACKET_CODEC, ItemTransferPayload::stack,
            PacketCodec.BOOL, ItemTransferPayload::deposit,
            ItemTransferPayload::new
    );
}
