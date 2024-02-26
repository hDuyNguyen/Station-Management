package com.main.Model;

import com.main.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Station {
    private int stationId;
    private String stationName;
    private Double stationPrice;

    public Station(int stationId, String stationName, Double stationPrice) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationPrice = stationPrice;
    }
    public Station(){

    }


    public Station(String stationName, Double stationPrice) {
        this.stationName = stationName;
        this.stationPrice = stationPrice;
    }

    public Station(int stationId) {
        this.stationId =stationId;
    }

    public Station(String stationName) {
        this.stationName = stationName;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Double getStationPrice() {
        return stationPrice;
    }

    public void setStationPrice(Double stationPrice) {
        this.stationPrice = stationPrice;
    }

    public static ObservableList<Station> getStationFromData(){
        ObservableList<Station> stationList = FXCollections.observableArrayList();
        try{
            Connection connection = DbConnection.getConnection();
            String sql ="SELECT * FROM stations";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int stationId = rs.getInt("stationId");
                String stationName = rs.getString("stationName");
                double stationPrice = rs.getDouble("stationPrice");
                Station station = new Station(stationId,stationName,stationPrice);
                stationList.add(station);
            }
            statement.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return stationList;
    }

    public static boolean addStationToData(Station station){
        try{
            Connection conn = DbConnection.getConnection();
            String sql = "INSERT INTO stations(stationName,stationPrice) VALUES (?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, station.getStationName());
            statement.setDouble(2,station.getStationPrice());
            int rowAdded = statement.executeUpdate();
            statement.close();
            conn.close();
            return rowAdded > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Station searchStation(String stationId) {
        Station station = new Station();
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT * FROM stations WHERE stationId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, stationId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                station.setStationId(rs.getInt("stationId"));
                station.setStationName(rs.getString("stationName"));
                station.setStationPrice(rs.getDouble("stationPrice"));
            } else {
                return null;
            }
            statement.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return station;
    }
    public static boolean updateStation(String stationId, String stationName,String stationPrice){
        try{
            Connection connection = DbConnection.getConnection();
            String sql = "UPDATE stations SET stationName = ?, stationPrice = ? WHERE stationId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,stationName);
            statement.setString(2,stationPrice);
            statement.setString(3,stationId);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            return rowsAffected > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteStation(String stationId){
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "DELETE FROM stations WHERE stationId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,stationId);
            int rs = statement.executeUpdate();
            statement.close();
            return rs > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static double getPrice(String stationName){
        double price = 0;
        try{
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT stationPrice FROM stations WHERE stationName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,stationName);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                 price = rs.getDouble("stationPrice");
            }
            statement.close();
            rs.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return price;
    }
}
