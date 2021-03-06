package me.serophots.epictitles;

import me.serophots.epictitles.Commands.actionbarCmd;
import me.serophots.epictitles.Commands.broadcastCmd;
import me.serophots.epictitles.Listeners.PlayerDie;
import me.serophots.epictitles.Listeners.PlayerJoin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main plugin;
    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        new broadcastCmd(this);
        new actionbarCmd(this);

        new PlayerJoin(this);
        new PlayerDie(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
