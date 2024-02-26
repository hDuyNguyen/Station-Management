package com.main.Model;

import com.main.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;

public class Bill {
    private int billId;
    private Booking booking;
    private Employee employee;

    private Station station;

    private Customer customer;

    public Bill(){

    }

    public Bill(int billId, Booking booking, Employee employee) {
        this.billId = billId;
        this.booking = booking;
        this.employee = employee;
    }

    public Bill(Booking booking, Employee employee) {
        this.booking = booking;
        this.employee = employee;
    }
    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Bill(Booking booking) {
        this.booking = booking;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public static ObservableList<Bill> getBillList() {
        ObservableList<Bill> billList = FXCollections.observableArrayList();
        try{
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT bo.bookingId, s.stationName, c.customerName, c.customerPhone, bo.bookingDate, bo.timeIn, bo.timeOut, bo.totalPrice, bo.status " +
                    "FROM bookings bo " +
                    "JOIN customers c ON bo.customerId = c.customerId " +
                    "JOIN stations s ON bo.stationId = s.stationId";
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
                Bill bill = new Bill(booking);
                billList.add(bill);
            }
            statement.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return billList;

    }

    public static boolean createBill(Bill bill){
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "INSERT INTO bills (bookingId, employeeId) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bill.booking.getBookingId());
            statement.setInt(2, bill.employee.getEmployeeId());
            statement.executeUpdate();
            // Đóng kết nối và statement
            statement.close();
            connection.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
            // Hiển thị thông báo lỗi nếu xảy ra lỗi trong quá trình lưu hoá đơn
        }
    }
        public static void updateStatus(int bookingId) {
            try {
                Connection connection = DbConnection.getConnection();
                String sql = "UPDATE bookings SET status = true WHERE bookingId = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, bookingId);
                statement.executeUpdate();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public static ObservableList<Bill> getBillFromData() {
        ObservableList<Bill> billList = FXCollections.observableArrayList();
        try{
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT b.billId, c.customerName, bo.bookingDate, c.customerPhone, s.stationName, bo.timeIn, bo.timeOut, e.username, bo.totalPrice " +
                    "FROM bills b " +
                    "JOIN bookings bo ON b.bookingId = bo.bookingId " +
                    "JOIN customers c ON bo.customerId = c.customerId " +
                    "JOIN stations s ON bo.stationId = s.stationId " +
                    "JOIN employees e ON b.employeeId = e.employeeId";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int billId = rs.getInt("billId");
                String customerName = rs.getString("customerName");
                String customerPhone = rs.getString("customerPhone");
                String stationName = rs.getString("stationName");
                LocalDate bookingDate = rs.getDate("bookingDate").toLocalDate();
                LocalTime timeIn = rs.getTime("timeIn").toLocalTime();
                LocalTime timeOut = rs.getTime("timeOut").toLocalTime();
                String username = rs.getString("username");
                double totalPrice = rs.getDouble("totalPrice");
                Customer customer = new Customer(customerName,customerPhone);
                Station station = new Station(stationName);
                Booking booking = new Booking(customer,station,bookingDate,timeIn,timeOut,totalPrice);
                Employee employee = new Employee(username);
                Bill bill = new Bill(billId,booking,employee);
                billList.add(bill);
            }
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return billList;
    }
    public static ObservableList<Bill> getBillsByDate(LocalDate date) {
        ObservableList<Bill> billList = FXCollections.observableArrayList();

        try {
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT b.billId, c.customerName, bo.bookingDate, c.customerPhone, s.stationName, bo.timeIn, bo.timeOut, e.username, bo.totalPrice " +
                    "FROM bills b " +
                    "JOIN bookings bo ON b.bookingId = bo.bookingId " +
                    "JOIN customers c ON bo.customerId = c.customerId " +
                    "JOIN stations s ON bo.stationId = s.stationId " +
                    "JOIN employees e ON b.employeeId = e.employeeId " +
                    "WHERE bo.bookingDate = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(date));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int billId = rs.getInt("billId");
                String customerName = rs.getString("customerName");
                String customerPhone = rs.getString("customerPhone");
                String stationName = rs.getString("stationName");
                LocalDate bookingDate = rs.getDate("bookingDate").toLocalDate();
                LocalTime timeIn = rs.getTime("timeIn").toLocalTime();
                LocalTime timeOut = rs.getTime("timeOut").toLocalTime();
                String username = rs.getString("username");
                double totalPrice = rs.getDouble("totalPrice");
                Customer customer = new Customer(customerName,customerPhone);
                Station station = new Station(stationName);
                Booking booking = new Booking(customer,station,bookingDate,timeIn,timeOut,totalPrice);
                Employee employee = new Employee(username);
                Bill bill = new Bill(billId,booking,employee);
                billList.add(bill);
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return billList;
    }
    public static Bill searchBill(int bookingId){
        Bill bill = new Bill();
        try {
            Connection conn = DbConnection.getConnection();
            String sql = "SELECT b.billId, c.customerName, bo.bookingDate, c.customerPhone, s.stationName, bo.timeIn, bo.timeOut, e.username, bo.totalPrice " +
                    "FROM bills b " +
                    "JOIN bookings bo ON b.bookingId = bo.bookingId " +
                    "JOIN customers c ON bo.customerId = c.customerId " +
                    "JOIN stations s ON bo.stationId = s.stationId " +
                    "JOIN employees e ON b.employeeId = e.employeeId " +
                    "WHERE bo.bookingId = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,bookingId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int billId = rs.getInt("billId");
                String customerName = rs.getString("customerName");
                String customerPhone = rs.getString("customerPhone");
                String stationName = rs.getString("stationName");
                LocalDate bookingDate = rs.getDate("bookingDate").toLocalDate();
                LocalTime timeIn = rs.getTime("timeIn").toLocalTime();
                LocalTime timeOut = rs.getTime("timeOut").toLocalTime();
                String username = rs.getString("username");
                double totalPrice = rs.getDouble("totalPrice");
                Customer customer = new Customer(customerName,customerPhone);
                Station station = new Station(stationName);
                Booking booking = new Booking(customer,station,bookingDate,timeIn,timeOut,totalPrice);
                Employee employee = new Employee(username);
                bill = new Bill(billId, booking, employee);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bill;
    }


}
