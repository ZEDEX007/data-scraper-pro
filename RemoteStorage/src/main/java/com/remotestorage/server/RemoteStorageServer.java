package com.remotestorage.server;

import com.remotestorage.RemoteStorageMod;
import com.remotestorage.cache.InventoryCache;
import com.remotestorage.network.*;
import com.remotestorage.storage.ContainerRegistry;
import com.remotestorage.util.ContainerPosition;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class RemoteStorageServer {
    private final InventoryCache cache = new InventoryCache();
    
    public void initialize() {
        ModPacketHandler.registerS2CPackets();
        ModPacketHandler.registerC2SPackets();
        
        ServerPlayNetworking.registerGlobalReceiver(ItemTransferPayload.PACKET_ID, this::handleItemTransfer);
        ServerPlayNetworking.registerGlobalReceiver(CacheRequestPayload.PACKET_ID, this::handleCacheRequest);
        
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            // Clean up on disconnect if needed
        });
    }
    
    private void handleItemTransfer(ServerPlayNetworking.Context context, ItemTransferPayload payload) {
        ServerPlayerEntity player = context.player();
        int containerId = payload.containerId();
        int slot = payload.slot();
        ItemStack stack = payload.stack();
        boolean deposit = payload.deposit();
        
        // Validate and process item transfer
        // This is a simplified implementation - full validation would check:
        // - Player has permission
        // - Container exists and is accessible
        // - Chunk is loaded
        // - Item counts match
        
        context.server().execute(() -> {
            // Process the transfer on the main thread
            // In a full implementation, this would interact with the actual container
        });
    }
    
    private void handleCacheRequest(ServerPlayNetworking.Context context, CacheRequestPayload payload) {
        ServerPlayerEntity player = context.player();
        
        // Send cached inventory data to the client
        context.server().execute(() -> {
            // Send container sync packets
        });
    }
    
    public void registerContainer(ContainerPosition position, Inventory inventory) {
        cache.update(position, inventory);
    }
    
    public void unregisterContainer(ContainerPosition position) {
        cache.remove(position);
    }
    
    public InventoryCache getCache() {
        return cache;
    }
}
