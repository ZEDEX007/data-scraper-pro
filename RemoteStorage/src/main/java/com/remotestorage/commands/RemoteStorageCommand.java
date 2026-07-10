package com.remotestorage.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.remotestorage.RemoteStorageMod;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class RemoteStorageCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("remotestorage")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("clear")
                        .executes(context -> {
                            var registry = RemoteStorageMod.getContainerRegistry();
                            if (registry != null) {
                                registry.clear();
                                context.getSource().sendFeedback(() -> Text.literal("Remote storage cache cleared"), true);
                                return 1;
                            }
                            return 0;
                        }))
                .then(CommandManager.literal("count")
                        .executes(context -> {
                            var registry = RemoteStorageMod.getContainerRegistry();
                            if (registry != null) {
                                int count = registry.getContainerCount();
                                context.getSource().sendFeedback(() -> Text.literal("Registered containers: " + count), true);
                                return 1;
                            }
                            return 0;
                        }))
                .then(CommandManager.literal("reload")
                        .executes(context -> {
                            var config = RemoteStorageMod.getConfig();
                            if (config != null) {
                                config.save();
                                context.getSource().sendFeedback(() -> Text.literal("Configuration reloaded"), true);
                                return 1;
                            }
                            return 0;
                        })));
    }
}
