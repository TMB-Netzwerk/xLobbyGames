package de.xenodev;

import de.xenodev.events.OpenGUIEvent;
import de.xenodev.games.fireball.*;
import de.xenodev.mysql.MySQL;
import de.xenodev.utils.CooldownBuilder;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class xLobbyGames extends JavaPlugin {

    private static xLobbyGames instance;
    private static MySQL mySQL;

    @Override
    public void onEnable() {
        instance = this;

        if(!new File("plugins/" + getName(), "config.yml").exists()){
            saveDefaultConfig();
        }

        init(getServer().getPluginManager());
        CooldownBuilder.handlePlayerCooldown();
        FireBallBuilder.handleFireBallUptime();

        checkMySQL();
    }

    @Override
    public void onDisable() {
        mySQL.closeDatabaseConnectionPool();
    }

    private void init(PluginManager pluginManager){
        pluginManager.registerEvents(new OpenGUIEvent(), this);

        //FireBall Fight
        pluginManager.registerEvents(new FireBallJoinEvent(), this);
        pluginManager.registerEvents(new FireBallQuitEvent(), this);
        pluginManager.registerEvents(new FireBallGameEvent(), this);
        pluginManager.registerEvents(new FireBallItemEvent(), this);
    }

    public static String getPrefix() {
        return getInstance().getConfig().getString("Settings.Chatprefix").replace("&", "§");
    }

    public static xLobbyGames getInstance(){
        return instance;
    }

    public static MySQL getMySQL() {
        return mySQL;
    }

    private void checkMySQL(){
        mySQL = new MySQL(getConfig().getString("MySQL.Host"), getConfig().getString("MySQL.Database"), getConfig().getString("MySQL.Username"), getConfig().getString("MySQL.Password"));
        try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
            PreparedStatement preparedStatement1 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS FireBall_Game(UUID VARCHAR(100),ALIVE INT, KILLS INT, DEATHS INT)");
            preparedStatement1.execute();
            preparedStatement1.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

}
