package me.serophots.epictitles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.lang.reflect.Constructor;
import java.util.List;

public class utils {
    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> chat(List<String> strings) {
        List<String> list = new java.util.ArrayList();
        for (String string : strings) {
            list.add(org.bukkit.ChatColor.translateAlternateColorCodes('&', string));
        }
        return list;
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void broadcast(String title, String subtitle, Player p){
        try {
            Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + utils.chat(utils.chat(title) + "\"}"));

            Object enumSubTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
            Object chatSubTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + utils.chat(subtitle) + "\"}");

            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);

            Object packet1 = titleConstructor.newInstance(enumTitle, chatTitle, 20, 40, 20);
            Object packet2 = titleConstructor.newInstance(enumSubTitle, chatSubTitle, 20, 40, 20);

            sendPacket(p, packet1);
            sendPacket(p, packet2);
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public static void broadcast(String subtitle, Player p){
        String title = Main.plugin.getConfig().getString("defaultTitlePrefix");
        try {
            Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + utils.chat(title) + "\"}");

            Object enumSubTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
            Object chatSubTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + utils.chat(subtitle) + "\"}");

            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);

            Object packet1 = titleConstructor.newInstance(enumTitle, chatTitle, 20, 40, 20);
            Object packet2 = titleConstructor.newInstance(enumSubTitle, chatSubTitle, 20, 30, 20);

            sendPacket(p, packet1);
            sendPacket(p, packet2);
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public static void actionbar(String text, Player p){
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            if(version.equals("v1_12_R1") || version.equals("v1_13_R1")){
                Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + utils.chat(text) + "\"}");

                Constructor<?> titleConstructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), getNMSClass("ChatMessageType"));

                Object packet1 = titleConstructor.newInstance(chatTitle, getNMSClass("ChatMessageType").getEnumConstants()[2]);

                sendPacket(p, packet1);
            }else {
                Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + utils.chat(text) + "\"}");

                Constructor<?> titleConstructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class);

                Object packet1 = titleConstructor.newInstance(chatTitle, (byte) 2);

                sendPacket(p, packet1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String DamageCause(EntityDamageEvent.DamageCause d){
        if(d == EntityDamageEvent.DamageCause.VOID){
            return "falling in the void";
        }else if(d == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION){
            return "an explosion";
        }else if(d == EntityDamageEvent.DamageCause.CONTACT){
            return "cactus";
        }else if(d == EntityDamageEvent.DamageCause.DROWNING){
            return "drowning";
        }else if(d == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
            return "a mob attack";
        }else if(d == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION){
            return "a creeper";
        }else if(d == EntityDamageEvent.DamageCause.FALL){
            return "fall damage";
        }else if(d == EntityDamageEvent.DamageCause.FIRE){
            return "fire";
        }else if(d == EntityDamageEvent.DamageCause.FIRE_TICK){
            return "fire";
        }else if(d == EntityDamageEvent.DamageCause.LAVA){
            return "fire";
        }else if(d == EntityDamageEvent.DamageCause.STARVATION){
            return "starvation";
        }else if(d == EntityDamageEvent.DamageCause.SUFFOCATION){
            return "suffocation";
        }else{
            return "death";
        }
    }
}
