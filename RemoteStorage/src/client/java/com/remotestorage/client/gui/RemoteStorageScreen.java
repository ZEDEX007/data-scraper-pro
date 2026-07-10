package com.remotestorage.client.gui;

import com.remotestorage.cache.InventoryCache;
import com.remotestorage.network.ContainerSyncPayload;
import com.remotestorage.util.ContainerPosition;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.*;

public class RemoteStorageScreen extends Screen {
    private TextFieldWidget searchField;
    private ButtonWidget sortButton;
    private ButtonWidget favoriteButton;
    private ButtonWidget waypointButton;
    private ButtonWidget darkModeButton;
    private ItemListWidget itemList;
    private ScrollbarWidget scrollbar;
    
    private SortType currentSort = SortType.NAME;
    private boolean darkMode = true;
    private String searchText = "";
    
    private final List<ItemEntry> allItems = new ArrayList<>();
    private final List<ItemEntry> filteredItems = new ArrayList<>();
    
    public RemoteStorageScreen() {
        super(Text.literal("Remote Storage"));
    }
    
    @Override
    protected void init() {
        int searchBarX = width / 2 - 100;
        int searchBarY = 20;
        
        // Search field
        searchField = new TextFieldWidget(textRenderer, searchBarX, searchBarY, 200, 20, Text.literal("Search"));
        searchField.setMaxLength(50);
        searchField.setChangedListener(this::onSearchChanged);
        addDrawableChild(searchField);
        
        // Sort button
        sortButton = ButtonWidget.builder(Text.literal("Sort: Name"), b -> cycleSort())
                .dimensions(searchBarX + 210, searchBarY, 80, 20)
                .build();
        addDrawableChild(sortButton);
        
        // Dark mode button
        darkModeButton = ButtonWidget.builder(Text.literal("Dark: ON"), b -> toggleDarkMode())
                .dimensions(searchBarX + 300, searchBarY, 70, 20)
                .build();
        addDrawableChild(darkModeButton);
        
        // Item list
        itemList = new ItemListWidget(this, 20, 50, width - 40, height - 80);
        addDrawableChild(itemList);
        
        // Scrollbar
        scrollbar = new ScrollbarWidget(width - 25, 50, 15, height - 80);
        addDrawableChild(scrollbar);
        
        refreshItems();
    }
    
    private void cycleSort() {
        currentSort = currentSort.next();
        sortButton.setMessage(Text.literal("Sort: " + currentSort.displayName));
        refreshItems();
    }
    
    private void toggleDarkMode() {
        darkMode = !darkMode;
        darkModeButton.setMessage(Text.literal("Dark: " + (darkMode ? "ON" : "OFF")));
    }
    
    private void onSearchChanged(String text) {
        searchText = text.toLowerCase();
        filterItems();
    }
    
    private void refreshItems() {
        allItems.clear();
        
        // Populate items from cache (in real implementation, this would come from server)
        InventoryCache cache = new InventoryCache();
        for (Map.Entry<ItemStack, Integer> entry : cache.getItemCounts().entrySet()) {
            allItems.add(new ItemEntry(entry.getKey(), entry.getValue()));
        }
        
        sortItems();
        filterItems();
    }
    
    private void sortItems() {
        switch (currentSort) {
            case NAME -> allItems.sort(Comparator.comparing(e -> e.stack.getName().getString()));
            case COUNT -> allItems.sort((a, b) -> Integer.compare(b.count, a.count));
            case DISTANCE -> allItems.sort(Comparator.comparingInt(e -> 0)); // Placeholder
        }
    }
    
    private void filterItems() {
        filteredItems.clear();
        for (ItemEntry entry : allItems) {
            if (searchText.isEmpty() || entry.stack.getName().getString().toLowerCase().contains(searchText)) {
                filteredItems.add(entry);
            }
        }
        if (scrollbar != null) {
            scrollbar.setMaxScroll(Math.max(0, filteredItems.size() - 10));
        }
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (darkMode) {
            fill(context, 0, 0, width, height, 0x60000000);
        } else {
            fill(context, 0, 0, width, height, 0xD0FFFFFF);
        }
        
        super.render(context, mouseX, mouseY, delta);
        
        // Render total count
        String totalCount = "Total Items: " + filteredItems.size();
        context.drawText(textRenderer, totalCount, 20, height - 25, darkMode ? 0xFFFFFF : 0x000000, true);
    }
    
    public void handleContainerUpdate(ContainerSyncPayload payload) {
        // Update item list when receiving sync from server
        refreshItems();
    }
    
    public void handleChunkStatusUpdate(int containerId, boolean loaded) {
        // Handle chunk status updates
    }
    
    public List<ItemEntry> getFilteredItems() {
        return filteredItems;
    }
    
    public int getScrollbarOffset() {
        return scrollbar != null ? scrollbar.getOffset() : 0;
    }
    
    public boolean isDarkMode() {
        return darkMode;
    }
    
    public enum SortType {
        NAME("Name"),
        COUNT("Count"),
        DISTANCE("Distance");
        
        private final String displayName;
        
        SortType(String displayName) {
            this.displayName = displayName;
        }
        
        public SortType next() {
            return values()[(ordinal() + 1) % values().length];
        }
    }
    
    public record ItemEntry(ItemStack stack, int count) {}
}
