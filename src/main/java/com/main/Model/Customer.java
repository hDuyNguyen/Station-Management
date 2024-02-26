package com.main.Model;

import com.main.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Customer {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerIdCard;
    private String customerPhone;
    private String username;
    private String dob;
    private String password;
    private int roleId=3;

    public Customer(int customerId, String customerName, String customerAddress, String customerIdCard, String customerPhone, String username, String password,String dob) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerIdCard = customerIdCard;
        this.customerPhone = customerPhone;
        this.username = username;
        this.password = password;
        this.dob = dob;
    }

    public Customer(String customerName, String customerAddress, String customerIdCard, String customerPhone, String username, String dob, String password, int roleId) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerIdCard = customerIdCard;
        this.customerPhone = customerPhone;
        this.username = username;
        this.dob = dob;
        this.password = password;
        this.roleId = roleId;
    }
    public Customer(){

    }

    public Customer(int customerId) {
        this.customerId = customerId;
    }

    public Customer(String customerName, String customerPhone) {
        this.customerName =customerName;
        this.customerPhone = customerPhone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerIdCard() {
        return customerIdCard;
    }

    public void setCustomerIdCard(String customerIdCard) {
        this.customerIdCard = customerIdCard;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public static boolean addCustomerToDatabase(Customer customer){
        try{
            Connection conn = DbConnection.getConnection();
            String sql = "INSERT INTO customers(customerName,customerPhone,customerAddress,customerIdCard,username,password,dob,roleId) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, customer.getCustomerName());
            statement.setString(2,customer.getCustomerPhone());
            statement.setString(3,customer.getCustomerAddress());
            statement.setString(4,customer.getCustomerIdCard());
            statement.setString(5,customer.getUsername());
            statement.setString(6,customer.getPassword());
            statement.setString(7,customer.getDob());
            statement.setInt(8,3);
            int rowAdded = statement.executeUpdate();
            statement.close();
            conn.close();
            return rowAdded > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static Customer searchCustomer(String username) {
        Customer customer = new Customer();
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT * FROM customers WHERE roleId = 3 AND username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                customer.setCustomerId(rs.getInt("customerId"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setCustomerAddress(rs.getString("customerAddress"));
                customer.setCustomerPhone(rs.getString("customerPhone"));
                customer.setDob(rs.getString("dob"));
                customer.setCustomerIdCard(rs.getString("customerIdCard"));
                customer.setUsername(rs.getString("username"));
                customer.setPassword(rs.getString("password"));
            } else {
                return null;
            }
            statement.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }
    public static ObservableList<Customer> getCustomerFromData(){
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try{
            Connection conn = DbConnection.getConnection();
            String sql = "SELECT * FROM customers WHERE roleId = 3";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                String username = rs.getString("username");
                String customerPhone = rs.getString("customerPhone");
                String customerIdCard = rs.getString("customerIdCard");
                String password = rs.getString("password");
                String dob = rs.getString("dob");
                String customerAddress = rs.getString("customerAddress");
                Customer customer = new Customer(customerId,customerName,customerAddress,customerIdCard,customerPhone,username,password,dob);
                customerList.add(customer);
            }
            statement.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return customerList;
    }
    public static boolean updateCustomer(String customerName,String customerIdCard,String customerAddress,String dob,String customerPhone,String username,String password){
        try{
            Connection connection = DbConnection.getConnection();
            String sql = "UPDATE customers SET customerName = ?, customerAddress = ?, customerIdCard = ?, dob = ?, customerPhone = ?, password = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,customerName);
            statement.setString(2,customerAddress);
            statement.setString(3,customerIdCard);
            statement.setString(4,dob);
            statement.setString(5,customerPhone);
            statement.setString(6,password);
            statement.setString(7,username);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            return rowsAffected > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCustomer(String customerId){
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "DELETE FROM customers WHERE customerId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,customerId);
            int rs = statement.executeUpdate();
            statement.close();
            return rs > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Customer searchCustomers(String customerId){
        Customer customer = new Customer();
        try{
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT customerId,customerName,customerAddress,customerIdCard,dob,customerPhone,username,password FROM customers WHERE customerId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,customerId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                customer.setCustomerId(rs.getInt("customerId"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setCustomerAddress(rs.getString("customerAddress"));
                customer.setCustomerIdCard(rs.getString("customerIdCard"));
                customer.setDob(rs.getString("dob"));
                customer.setCustomerPhone(rs.getString("customerPhone"));
                customer.setUsername(rs.getString("username"));
                customer.setPassword(rs.getString("password"));
            }else {
                return null;
            }
            statement.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return customer;
    }
}
