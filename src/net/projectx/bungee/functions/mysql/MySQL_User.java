package net.projectx.bungee.functions.mysql;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.projectx.bungee.util.UUIDFetcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * ~Yannick on 16.10.2019 at 18:45 o´ clock
 */
public class MySQL_User {
    public static void createUserTable() {
        MySQL.update("CREATE TABLE IF NOT EXISTS user(uuid VARCHAR(64), name VARCHAR(16), coins BIGINT, playtime BIGINT, firstJoin DATETIME, lastSeen DATETIME, discordid VARCHAR(18))");
    }

    public static void createUser(UUID uuid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = Date.from(Instant.now());
        System.out.println("INSERT INTO `user` VALUES ('" + uuid.toString() + "', 0, 0, '" + sdf.format(time) + "', -1, -1)");
        MySQL.update("INSERT INTO `user` VALUES ('" + uuid.toString() + "','" + UUIDFetcher.getName(uuid) + "', 0, 0, '" + sdf.format(time) + "', -1, -1)");
    }

    public static void deleteUser() {
        MySQL.update("DELETE * FROM `user`");
    }

    public static boolean isUserExists(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT * FROM user WHERE uuid = '" + uuid + "'");
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<UUID> getUsers() {
        ArrayList<UUID> list = new ArrayList<>();
        try {
            ResultSet rs = MySQL.querry("SELECT uuid FROM `user` WHERE 1");
            UUID uuid;
            while (rs.next()) {
                list.add(UUID.fromString(rs.getString("uuid")));
            }
            return list;
        } catch (SQLException e) {
            return null;
        }
    }

    public static int getFruits(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT fruits FROM user WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getInt("fruits");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setCoins(UUID uuid, int fruits) {
        MySQL.update("UPDATE user SET coins = " + fruits);
    }

    public static void addCoins(UUID uuid, int fruits) {
        setCoins(uuid, getFruits(uuid) + fruits);
    }

    public static void removeCoins(UUID uuid, int fruits) {
        setCoins(uuid, getFruits(uuid) - fruits);
    }

    public static void setPlaytime(UUID uuid, long playtime) {
        MySQL.update("UPDATE user SET playtime = " + playtime);
    }

    public static int getPlaytime(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT onlinetime FROM user WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getInt("onlinetime");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static LocalDateTime getFirstTime(UUID uuid) {
        ResultSet rs = MySQL.querry("SELECT  firstJoin FROM user WHERE uuid = '" + uuid.toString() + "'");
        try {
            while (rs.next()) {
                return LocalDateTime.of(rs.getDate("firstJoin").toLocalDate(), rs.getTime("firstJoin").toLocalTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LocalDateTime getFirstTime(ProxiedPlayer pp) {
        ResultSet rs = MySQL.querry("SELECT  firstJoin FROM `user` WHERE uuid = '" + pp.getUniqueId().toString() + "'");
        try {
            while (rs.next()) {
                return LocalDateTime.of(rs.getDate("firstJoin").toLocalDate(), rs.getTime("firstJoin").toLocalTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getLastTime(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT lastseen FROM `user` WHERE uuid = '" + uuid + "'");
            return rs.getLong("lastseen");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static long getLastTime(ProxiedPlayer pp) {
        UUID uuid = pp.getUniqueId();
        try {
            ResultSet rs = MySQL.querry("SELECT lastseen FROM `user` WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getLong("lastseen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setLastTime(UUID uuid, long time) {
        MySQL.update("UPDATE `user` SET lastseen = " + time + " WHERE uuid = '" + uuid + "'");
    }

    public static void setLastTime(ProxiedPlayer pp, long time) {
        UUID uuid = pp.getUniqueId();
        MySQL.update("UPDATE `user` SET lastseen = " + time + " WHERE uuid = '" + uuid + "'");
    }

    public static HashMap<String, Integer> getTop5() {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            ResultSet rs = MySQL.querry("SELECT  name, playtime FROM user ORDER BY playtime DESC LIMIT 5");
            while (rs.next()) {
                map.put(rs.getString("name"), rs.getInt("playtime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setDiscordId(UUID uuid, String id) {
        MySQL.update("UPDATE `user` SET discordid = '" + id + "' WHERE uuid = '" + uuid + "'");
    }

    public static String getDiscordId(UUID uuid) {
        try {
            ResultSet rs = MySQL.querry("SELECT discordid FROM `user` WHERE uuid = '" + uuid + "'");
            while (rs.next()) {
                return rs.getString("discordid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
