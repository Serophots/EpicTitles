package me.serophots.epictitles.Commands;

import me.serophots.epictitles.Main;
import me.serophots.epictitles.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class actionbarCmd implements CommandExecutor {
    private Main plugin;
    public actionbarCmd(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("actionbar").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission(plugin.getConfig().getString("commands.actionbarPerm"))){
                p.sendMessage(utils.chat(plugin.getConfig().getString("noPerm").replace("{prefix}", plugin.getConfig().getString("prefix"))));
                return false;
            }
        }
        if(args.length > 0){
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message += args[i] + " ";
            }
            for(Player p : Bukkit.getOnlinePlayers()) {
                utils.actionbar(message, p);
            }
        }
        return false;
    }
}
