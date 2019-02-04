package me.serophots.epictitles.Listeners;

import me.serophots.epictitles.Main;
import me.serophots.epictitles.utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDie implements Listener {
    private Main plugin;

    public PlayerDie(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void EntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (plugin.getConfig().getBoolean("playerDeathEvent.enabled")) {
                if(e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && e.getEntity().getKiller() != null){
                    String cause;
                    if(e.getEntity().getKiller().getItemInHand().getType() != Material.AIR) {
                        cause = "by " + e.getEntity().getKiller().getName() + " using " + e.getEntity().getKiller().getItemInHand().getItemMeta().getDisplayName();
                    }else{
                        cause = "by " + e.getEntity().getKiller().getName() + " using his fist!!";
                    }
                    for (String s : plugin.getConfig().getStringList("playerDeathEvent.types")) {
                        if (s.equalsIgnoreCase("chatlocal")) {
                            p.sendMessage(utils.chat(plugin.getConfig().getString("playerDeathEvent.chatLocalMessage").replace("{prefix}", plugin.getConfig().getString("prefix")).replace("{cause}", cause)));
                        } else if (s.equalsIgnoreCase("chatpublic")) {
                            Bukkit.broadcastMessage(utils.chat(plugin.getConfig().getString("playerDeathEvent.chatPublicMessage").replace("{prefix}", plugin.getConfig().getString("prefix")).replace("{cause}", cause)));
                        } else if (s.equalsIgnoreCase("title")) {
                            String s1 = plugin.getConfig().getString("playerDeathEvent.titleMessage").replace("|", "-").replace("{defaultprefix}", plugin.getConfig().getString("defaultTitlePrefix")).replace("{cause}", cause);
                            String[] s2 = s1.split("-");
                            utils.broadcast(s2[0], s2[1], p);
                        } else if (s.equalsIgnoreCase("actionbar")) {
                            utils.actionbar(plugin.getConfig().getString("playerDeathEvent.actionbarMessage").replace("{prefix}", plugin.getConfig().getString("prefix")).replace("{cause}", cause), p);
                        }
                    }
                }else {
                    for (String s : plugin.getConfig().getStringList("playerDeathEvent.types")) {
                        if (s.equalsIgnoreCase("chatlocal")) {
                            p.sendMessage(utils.chat(plugin.getConfig().getString("playerDeathEvent.chatLocalMessage").replace("{prefix}", plugin.getConfig().getString("prefix")).replace("{cause}", utils.DamageCause(e.getEntity().getLastDamageCause().getCause()))));
                        } else if (s.equalsIgnoreCase("chatpublic")) {
                            Bukkit.broadcastMessage(utils.chat(plugin.getConfig().getString("playerDeathEvent.chatPublicMessage").replace("{prefix}", plugin.getConfig().getString("prefix")).replace("{cause}", utils.DamageCause(e.getEntity().getLastDamageCause().getCause()))));
                        } else if (s.equalsIgnoreCase("title")) {
                            String s1 = plugin.getConfig().getString("playerDeathEvent.titleMessage").replace("|", "-").replace("{defaultprefix}", plugin.getConfig().getString("defaultTitlePrefix")).replace("{cause}", utils.DamageCause(e.getEntity().getLastDamageCause().getCause()));
                            String[] s2 = s1.split("-");
                            utils.broadcast(s2[0], s2[1], p);
                        } else if (s.equalsIgnoreCase("actionbar")) {
                            utils.actionbar(plugin.getConfig().getString("playerDeathEvent.actionbarMessage").replace("{prefix}", plugin.getConfig().getString("prefix")).replace("{cause}", utils.DamageCause(e.getEntity().getLastDamageCause().getCause())), p);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void PlayerDeath(PlayerDeathEvent e) {
        if (plugin.getConfig().getBoolean("playerDeathEvent.enabled") && plugin.getConfig().getBoolean("playerDeathEvent.override")) {
            e.setDeathMessage("");
        }
    }
}