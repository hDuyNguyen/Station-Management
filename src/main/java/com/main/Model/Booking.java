package com.main.Model;

import com.main.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private int bookingId;
    private Customer customer;
    private Station station;
    private LocalDate bookingDate;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private double totalPrice;
    private boolean status;

    public Booking(Customer customer, Station station, LocalDate bookingDate, LocalTime timeIn, LocalTime timeOut,double totalPrice) {
        this.customer = customer;
        this.station = station;
        this.bookingDate = bookingDate;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.totalPrice = totalPrice;
    }


    public Booking(int bookingId, Station station, LocalDate bookingDate, LocalTime timeIn, LocalTime timeOut, double totalPrice, boolean status) {
        this.bookingId = bookingId;
        this.station = station;
        this.bookingDate = bookingDate;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Booking(int bookingId,Customer customer,Station station, LocalDate bookingDate, LocalTime timeIn, LocalTime timeOut, double totalPrice, boolean status) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.station =station;
        this.bookingDate = bookingDate;
        this.timeIn =timeIn;
        this.timeOut = timeOut;
        this.totalPrice = totalPrice;
        this.status =status;
    }

    public Booking(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }
    public String getStationName() {
        return station.getStationName();
    }
    public boolean createBooking() {
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "INSERT INTO bookings (customerId, stationId, bookingDate, timeIn, timeOut, totalPrice) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customer.getCustomerId());
            statement.setInt(2, station.getStationId());
            statement.setDate(3, Date.valueOf(bookingDate));
            statement.setTime(4, Time.valueOf(timeIn));
            statement.setTime(5, Time.valueOf(timeOut));
            statement.setDouble(6, totalPrice);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static ObservableList<Booking> getBookingList(int customerId) {
        ObservableList<Booking> bookingList = FXCollections.observableArrayList();
        try{
            Connection connection =DbConnection.getConnection();
            String sql = "SELECT b.bookingId, s.stationName, b.bookingDate, b.timeIn, b.timeOut, b.totalPrice, b.status FROM bookings b "
                    + "INNER JOIN stations s ON b.stationId = s.stationId "
                    + "WHERE b.customerId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,customerId);
            ResultSet rs =statement.executeQuery();
            while (rs.next()){
                int bookingId = rs.getInt("bookingId");
                String stationName = rs.getString("stationName");
                LocalDate bookingDate = rs.getDate("bookingDate").toLocalDate();
                LocalTime timeIn = rs.getTime("timeIn").toLocalTime();
                LocalTime timeOut = rs.getTime("timeOut").toLocalTime();
                double totalPrice = rs.getDouble("totalPrice");
                boolean status = rs.getBoolean("status");
                Station station = new Station(stationName);
                Booking booking = new Booking(bookingId,station,bookingDate,timeIn,timeOut,totalPrice,status);
                bookingList.add(booking);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookingList;
    }

    public static List<Station> getAvailableStations(LocalDate date, LocalTime timeIn, LocalTime timeOut) {
        List<Station> availableStations = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT stationId,stationName FROM stations WHERE stationId NOT IN (SELECT stationId FROM bookings WHERE bookingDate = ? AND (timeIn >= ? AND timeOut <= ?))";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(date));
            statement.setTime(2, Time.valueOf(timeIn));
            statement.setTime(3, Time.valueOf(timeOut));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int stationId = rs.getInt("stationId");
                String stationName = rs.getString("stationName");
                Station station = new Station(stationId);
                station.setStationName(stationName);
                availableStations.add(station);
            }
            statement.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableStations;
    }
    public static ObservableList<Booking> getBooking() {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        try{
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT bo.bookingId, s.stationName, c.customerName, c.customerPhone, bo.bookingDate, bo.timeIn, bo.timeOut, bo.totalPrice, bo.status " +
                    "FROM bookings bo " +
                    "JOIN customers c ON bo.customerId = c.customerId " +
                    "JOIN stations s ON bo.stationId = s.stationId " +
                    "WHERE bo.status = false";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int bookingId = rs.getInt("bookingId");
                String stationName = rs.getString("stationName");
                String customerName = rs.getString("customerName");
                String customerPhone = rs.getString("customerPhone");
                LocalDate bookingDate = rs.getDate("bookingDate").toLocalDate();
                LocalTime timeIn = rs.getTime("timeIn").toLocalTime();
                LocalTime timeOut = rs.getTime("timeOut").toLocalTime();
                double totalPrice = rs.getDouble("totalPrice");
                boolean status = rs.getBoolean("status");
                Customer customer = new Customer(customerName,customerPhone);
                Station station = new Station(stationName);
                Booking booking = new Booking(bookingId,customer,station,bookingDate,timeIn,timeOut,totalPrice,status);
                bookings.add(booking);
            }
            statement.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookings;

    }

}
