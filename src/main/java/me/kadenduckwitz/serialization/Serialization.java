package me.kadenduckwitz.serialization;

import me.kadenduckwitz.serialization.commands.SerializeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Serialization extends JavaPlugin {

    @Override
    public void onEnable() {
        // Commands
        registerCommands();
    }

    private void registerCommands() {
        getCommand("serialize").setExecutor(new SerializeCommand());
    }
}