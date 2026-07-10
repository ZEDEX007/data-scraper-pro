package com.remotestorage.storage;

import com.remotestorage.util.ContainerPosition;
import net.minecraft.block.entity.*;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ContainerRegistry {
    private final Map<ContainerPosition, Inventory> containers = new ConcurrentHashMap<>();
    private final Set<ContainerPosition> favorites = ConcurrentHashMap.newKeySet();
    
    public void registerContainer(World world, BlockPos pos, Inventory inventory) {
        ContainerPosition position = new ContainerPosition(world.getRegistryKey(), pos);
        containers.put(position, inventory);
    }
    
    public void unregisterContainer(World world, BlockPos pos) {
        ContainerPosition position = new ContainerPosition(world.getRegistryKey(), pos);
        containers.remove(position);
    }
    
    public Inventory getContainer(ContainerPosition position) {
        return containers.get(position);
    }
    
    public Collection<Inventory> getAllContainers() {
        return containers.values();
    }
    
    public Map<ContainerPosition, Inventory> getAllContainersWithPositions() {
        return new HashMap<>(containers);
    }
    
    public boolean isFavorite(ContainerPosition position) {
        return favorites.contains(position);
    }
    
    public void toggleFavorite(ContainerPosition position) {
        if (favorites.contains(position)) {
            favorites.remove(position);
        } else {
            favorites.add(position);
        }
    }
    
    public Set<ContainerPosition> getFavorites() {
        return Collections.unmodifiableSet(favorites);
    }
    
    public int getContainerCount() {
        return containers.size();
    }
    
    public void clear() {
        containers.clear();
    }
    
    public static boolean isValidContainer(BlockEntity blockEntity) {
        return blockEntity instanceof ChestBlockEntity ||
               blockEntity instanceof BarrelBlockEntity ||
               blockEntity instanceof ShulkerBoxBlockEntity ||
               blockEntity instanceof AbstractFurnaceBlockEntity;
    }
    
    public static boolean isValidContainer(net.minecraft.entity.Entity entity) {
        return entity instanceof ChestMinecartEntity;
    }
}
