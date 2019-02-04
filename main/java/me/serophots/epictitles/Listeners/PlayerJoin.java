package me.serophots.epictitles.Listeners;

import me.serophots.epictitles.Main;
import me.serophots.epictitles.utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private Main plugin;
    public PlayerJoin(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(plugin.getConfig().getBoolean("joinEvent.enabled")) {
            if (plugin.getConfig().getBoolean("joinEvent.override")) {
                e.setJoinMessage("");
            }
            for (String s : plugin.getConfig().getStringList("joinEvent.types")) {
                if (s.equalsIgnoreCase("chatlocal")) {
                    p.sendMessage(utils.chat(plugin.getConfig().getString("joinEvent.chatLocalMessage").replace("{prefix}", plugin.getConfig().getString("prefix"))));
                } else if (s.equalsIgnoreCase("chatpublic")) {
                    Bukkit.broadcastMessage(utils.chat(plugin.getConfig().getString("joinEvent.chatPluginMessage").replace("{prefix}", plugin.getConfig().getString("prefix"))));
                } else if (s.equalsIgnoreCase("title")) {
                    String s1 = plugin.getConfig().getString("joinEvent.titleMessage").replace("|", "-").replace("{defaultprefix}", plugin.getConfig().getString("defaultTitlePrefix"));
                    String[] s2 = s1.split("-");
                    utils.broadcast(s2[0], s2[1], p);
                } else if (s.equalsIgnoreCase("actionbar")) {
                    utils.actionbar(plugin.getConfig().getString("joinEvent.actionbarMessage").replace("{prefix}", plugin.getConfig().getString("prefix")), p);
                }
            }
        }
    }
}
