package com.remotestorage.mixin;

import com.remotestorage.RemoteStorageMod;
import com.remotestorage.events.ContainerOpenListener;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public class ServerPlayNetworkHandlerMixin {
    @Inject(method = "<init>(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntityType;)V", at = @At("TAIL"))
    private void onChestCreated(BlockPos pos, net.minecraft.block.entity.BlockEntityType<?> type, CallbackInfo ci) {
        // This mixin is a placeholder for server-side packet handling
    }
}
