package com.remotestorage.client.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onInventoryS2CPacket", at = @At("HEAD"))
    private void onInventoryPacket(net.minecraft.network.packet.s2c.play.InventoryS2CPacket packet, CallbackInfo ci) {
        // Handle inventory sync packets for remote storage
    }
}
