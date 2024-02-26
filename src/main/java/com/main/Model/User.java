package com.main.Model;

import com.main.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    private int id;
    private String username;
    private String password;

    private int roleId;

    private String name;

    private String address;
    private String idCard;
    private String dob;

    private String phone;

    public User() {

    }

    public User(String username, String password, int roleId, String name, String address, String idCard, String dob, String phone) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.name = name;
        this.address = address;
        this.idCard = idCard;
        this.dob = dob;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public static User Admin(Admin admin) {
        User user = new User();
        user.setUsername(admin.getUsername());
        user.setPassword(admin.getPassword());
        user.setRoleId(admin.getRoleId());
        return user;
    }

    public static User Employee(Employee employee) {
        User user = new User();
        user.setId(employee.getEmployeeId());
        user.setName(employee.getEmployeeName());
        user.setAddress(employee.getEmployeeAddress());
        user.setIdCard(employee.getEmployeeIdCard());
        user.setUsername(employee.getUsername());
        user.setPassword(employee.getPassword());
        user.setPhone(employee.getEmployeePhone());
        user.setRoleId(employee.getRoleId());
        return user;
    }

    public static User Customer(Customer customer){
        User user = new User();
        user.setName(customer.getCustomerName());
        user.setAddress(customer.getCustomerAddress());
        user.setIdCard(customer.getCustomerIdCard());
        user.setUsername(customer.getUsername());
        user.setPassword(customer.getPassword());
        user.setPhone(customer.getCustomerPhone());
        user.setRoleId(customer.getRoleId());
        return user;
    }

    public static boolean checkLogin(String username, String password, int roleId) {
        try {
            Connection conn = DbConnection.getConnection();
            String sql = "SELECT *\n" +
                    "FROM\n" +
                    "(\n" +
                    "    SELECT username, password, roleId FROM admins\n" +
                    "    UNION ALL\n" +
                    "    SELECT username, password, roleId FROM employees\n" +
                    "    UNION ALL\n" +
                    "    SELECT username, password, roleId FROM customers\n" +
                    ") AS users\n" +
                    "WHERE users.username = ? AND users.password = ? AND\n" +
                    "    EXISTS (\n" +
                    "        SELECT * FROM roles WHERE roles.roleId = users.roleId AND roles.roleId = ?\n" +
                    ")";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, roleId);
            ResultSet rs = statement.executeQuery();
            boolean result = rs.next();
            rs.close();
            statement.close();
            conn.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
