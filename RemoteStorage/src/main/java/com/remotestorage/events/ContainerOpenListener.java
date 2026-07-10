package com.remotestorage.events;

import com.remotestorage.RemoteStorageMod;
import com.remotestorage.storage.ContainerRegistry;
import com.remotestorage.util.ContainerPosition;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ContainerOpenListener {
    Event<ContainerOpenListener> EVENT = EventFactory.createArrayBacked(ContainerOpenListener.class,
            (listeners) -> (world, pos, inventory) -> {
                for (ContainerOpenListener listener : listeners) {
                    listener.onContainerOpen(world, pos, inventory);
                }
            });
    
    void onContainerOpen(World world, BlockPos pos, Inventory inventory);
    
    static void register() {
        EVENT.register((world, pos, inventory) -> {
            ContainerRegistry registry = RemoteStorageMod.getContainerRegistry();
            if (registry != null) {
                registry.registerContainer(world, pos, inventory);
            }
        });
    }
}
