# Remote Storage Mod

A Fabric mod that gives the player a searchable remote storage interface using ONLY vanilla inventories.

## Features

- Press R to open Remote Storage GUI
- Search bar for instant item searching
- Scrollable item list with total counts
- Container coordinates and dimension display
- Sort by: Name, Count, Distance
- Favorite containers
- Waypoint button
- Dark mode toggle

## Supported Containers

- Chest
- Double Chest
- Barrel
- Shulker Box
- Chest Minecart

## Requirements

- Minecraft 1.21.6
- Fabric Loader 0.19.x
- Java 21
- Fabric API

## Installation

1. Install Fabric Loader for Minecraft 1.21.6
2. Install Fabric API
3. Place RemoteStorage jar in mods folder
4. Launch Minecraft

## Configuration

Edit `config/remote_storage.json` to customize:

- Hotkey (default: R)
- Search distance
- Cache enabled
- GUI animations
- Dark theme

## Building

```bash
./gradlew build
```

## License

MIT License
