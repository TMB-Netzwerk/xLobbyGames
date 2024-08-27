package de.xenodev.mysql;

import de.xenodev.xLobbyGames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventAPI {

    private static boolean eventExists(){

        try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Event");

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("NAME") != null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void createEvent(){
        if(!eventExists()){
            try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Event(NAME) VALUES ('Nothing');");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }


    public static String getEvent(){
        if(eventExists()){
            try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Event");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (String.valueOf(rs.getString("NAME")) == null));
                return rs.getString("NAME");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createEvent();
            getEvent();
        }
        return null;
    }

    public static void setEvent(String name){
        if(eventExists()){
            try (Connection connection = xLobbyGames.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `Event` SET `NAME` = ?;");
                preparedStatement.setString(1, name);
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createEvent();
            setEvent(name);
        }
    }
}
