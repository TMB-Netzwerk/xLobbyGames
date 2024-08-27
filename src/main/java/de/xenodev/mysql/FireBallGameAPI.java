package de.xenodev.mysql;

import de.xenodev.xLobbyGames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FireBallGameAPI {

    private static boolean playerExists(UUID uuid){

        try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FireBall_Game WHERE UUID= '" + uuid + "'");

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void createPlayer(UUID uuid){
        if(!playerExists(uuid)){
            try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO FireBall_Game(UUID, ALIVE, KILLS, DEATHS) VALUES ('" + uuid + "', '0', '0', '0');");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    public static Integer getDeaths(UUID uuid){
        if(playerExists(uuid)){
            try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FireBall_Game WHERE UUID= '" + uuid + "'");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (Integer.valueOf(rs.getInt("DEATHS")) == null));
                return rs.getInt("DEATHS");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getDeaths(uuid);
        }
        return null;
    }

    public static void setDeaths(UUID uuid, Integer deaths){
        if(playerExists(uuid)){
            try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `FireBall_Game` SET `DEATHS` = ? WHERE `UUID` = ?;");
                preparedStatement.setInt(1, deaths);
                preparedStatement.setString(2, uuid.toString());
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            setDeaths(uuid, deaths);
        }
    }

    public static void addDeaths(UUID uuid, Integer deaths){
        if(playerExists(uuid)){
            setDeaths(uuid, Integer.valueOf(getDeaths(uuid).intValue() + deaths.intValue()));
        }else{
            createPlayer(uuid);
            addDeaths(uuid, deaths);
        }
    }

    public static Integer getKills(UUID uuid){
        if(playerExists(uuid)){
            try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FireBall_Game WHERE UUID= '" + uuid + "'");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (Integer.valueOf(rs.getInt("KILLS")) == null));
                return rs.getInt("KILLS");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getKills(uuid);
        }
        return null;
    }

    public static void setKills(UUID uuid, Integer kills){
        if(playerExists(uuid)){
            try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `FireBall_Game` SET `KILLS` = ? WHERE `UUID` = ?;");
                preparedStatement.setInt(1, kills);
                preparedStatement.setString(2, uuid.toString());
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            setKills(uuid, kills);
        }
    }

    public static void addKills(UUID uuid, Integer kills){
        if(playerExists(uuid)){
            setKills(uuid, Integer.valueOf(getKills(uuid).intValue() + kills.intValue()));
        }else{
            createPlayer(uuid);
            addKills(uuid, kills);
        }
    }

    public static Integer getAlive(UUID uuid){
        if(playerExists(uuid)){
            try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FireBall_Game WHERE UUID= '" + uuid + "'");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (Integer.valueOf(rs.getInt("ALIVE")) == null));
                return rs.getInt("ALIVE");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getAlive(uuid);
        }
        return null;
    }

    public static void setAlive(UUID uuid, Integer seconds){
        if(playerExists(uuid)){
            try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `FireBall_Game` SET `ALIVE` = ? WHERE `UUID` = ?;");
                preparedStatement.setInt(1, seconds);
                preparedStatement.setString(2, uuid.toString());
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            setAlive(uuid, seconds);
        }
    }

    public static void addAlive(UUID uuid, Integer seconds){
        if(playerExists(uuid)){
            setAlive(uuid, Integer.valueOf(getAlive(uuid).intValue() + seconds.intValue()));
        }else{
            createPlayer(uuid);
            addAlive(uuid, seconds);
        }
    }

    public String formateTime(UUID uuid){
        Integer time = getAlive(uuid);
        Integer seconds = 0, minutes = 0, hours = 0;

        while (time != 0){
            time--;
            seconds++;
            if(seconds >= 60){
                seconds = 0;
                minutes++;
            }
            if(minutes >= 60){
                minutes = 0;
                hours++;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(check(hours))
                .append(":")
                .append(check(minutes))
                .append(":")
                .append(check(seconds));
        return stringBuilder.toString();
    }

    private String check(Integer time){
        return (time >= 10) ? ("" + time) : ("0" + time);
    }
}
