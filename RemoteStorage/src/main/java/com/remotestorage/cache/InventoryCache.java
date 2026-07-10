package com.remotestorage.cache;

import com.remotestorage.storage.ContainerRegistry;
import com.remotestorage.util.ContainerPosition;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class InventoryCache {
    private final Map<ContainerPosition, CachedInventory> cache = new HashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final ReentrantLock lock = new ReentrantLock();
    
    public record CachedInventory(Inventory inventory, long lastUpdated, ContainerPosition position) {}
    
    public void updateAsync(ContainerPosition position, Inventory inventory) {
        executor.submit(() -> update(position, inventory));
    }
    
    public void update(ContainerPosition position, Inventory inventory) {
        lock.lock();
        try {
            cache.put(position, new CachedInventory(inventory, System.currentTimeMillis(), position));
        } finally {
            lock.unlock();
        }
    }
    
    @Nullable
    public CachedInventory get(ContainerPosition position) {
        lock.lock();
        try {
            return cache.get(position);
        } finally {
            lock.unlock();
        }
    }
    
    public boolean isChunkLoaded(ContainerPosition position) {
        // This will be implemented server-side
        return true;
    }
    
    public List<ItemStack> getAllItems() {
        List<ItemStack> allItems = new ArrayList<>();
        lock.lock();
        try {
            for (CachedInventory cached : cache.values()) {
                for (int i = 0; i < cached.inventory.size(); i++) {
                    ItemStack stack = cached.inventory.getStack(i);
                    if (!stack.isEmpty()) {
                        allItems.add(stack);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
        return allItems;
    }
    
    public Map<ItemStack, Integer> getItemCounts() {
        Map<ItemStack, Integer> counts = new HashMap<>();
        lock.lock();
        try {
            for (CachedInventory cached : cache.values()) {
                for (int i = 0; i < cached.inventory.size(); i++) {
                    ItemStack stack = cached.inventory.getStack(i);
                    if (!stack.isEmpty()) {
                        counts.merge(stack, stack.getCount(), Integer::sum);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
        return counts;
    }
    
    public int getTotalCount(ItemStack itemStack) {
        int total = 0;
        lock.lock();
        try {
            for (CachedInventory cached : cache.values()) {
                for (int i = 0; i < cached.inventory.size(); i++) {
                    ItemStack stack = cached.inventory.getStack(i);
                    if (!stack.isEmpty() && ItemStack.areItemsAndComponentsEqual(stack, itemStack)) {
                        total += stack.getCount();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
        return total;
    }
    
    public Collection<CachedInventory> getAllCached() {
        lock.lock();
        try {
            return Collections.unmodifiableCollection(new ArrayList<>(cache.values()));
        } finally {
            lock.unlock();
        }
    }
    
    public void remove(ContainerPosition position) {
        lock.lock();
        try {
            cache.remove(position);
        } finally {
            lock.unlock();
        }
    }
    
    public void clear() {
        lock.lock();
        try {
            cache.clear();
        } finally {
            lock.unlock();
        }
    }
    
    public void shutdown() {
        executor.shutdown();
    }
}
