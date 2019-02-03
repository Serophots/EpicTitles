package me.serophots.epictitles.Commands;

import me.serophots.epictitles.Main;
import me.serophots.epictitles.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class broadcastCmd implements CommandExecutor {
    private Main plugin;
    public broadcastCmd(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("broadcast").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission(plugin.getConfig().getString("commands.broadcastPerm"))){
                p.sendMessage(utils.chat(plugin.getConfig().getString("noPerm").replace("{prefix}", plugin.getConfig().getString("prefix"))));
                return false;
            }
        }
        if(args.length > 0){
            String message = "";
            Boolean customTitle = false;
            for (int i = 0; i < args.length; i++) {
                if (!args[i].equals("-t")) {
                    message += args[i] + " ";
                }else{
                    customTitle = true;
                    args[i] = "";
                }
            }
            if(customTitle){
                if(args.length > 2) {
                    String title = args[0];
                    args[0] = "";
                    message = "";
                    for (int i = 0; i < args.length; i++) {
                        message += args[i] + " ";
                    }
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        utils.broadcast(title, message, p);
                    }
                }
            }else{
                for(Player p : Bukkit.getOnlinePlayers()) {
                    utils.broadcast(message, p);
                }
            }
        }else{
            sender.sendMessage(utils.chat(plugin.getConfig().getString("incorrectArgs").replace("{prefix}", plugin.getConfig().getString("prefix"))));
        }
        return false;
    }
}
