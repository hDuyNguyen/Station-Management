package com.main.Model;

import com.main.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Employee {
    private int employeeId;
    private String employeeName;
    private String employeeAddress;
    private String employeeIdCard;
    private String employeePhone;
    private String username;
    private String password;
    private int roleId=2;
    private String dob;
    private int hours_worked;


    public Employee(){

    }
    public Employee(int employeeId, String employeeName) {
        this.employeeId = employeeId;
        this.employeeName =employeeName;
    }
    public Employee(int employeeId) {
        this.employeeId = employeeId;
    }


    public Employee(int employeeId, String employeeName, String employeeAddress, String employeeIdCard, String employeePhone, String username, String password, String dob) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employeeIdCard = employeeIdCard;
        this.employeePhone = employeePhone;
        this.username = username;
        this.password = password;
        this.dob = dob;
    }

    public Employee(int employeeId, String employeeName, String employeeAddress, String employeeIdCard, String employeePhone, String dob, String username, String password, int hours_worked) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employeeIdCard = employeeIdCard;
        this.employeePhone = employeePhone;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.hours_worked = hours_worked;
    }

    public Employee(String username){
        this.username = username;
    }

    public Employee(String employeeName, String employeeAddress, String employeeIdCard, String employeePhone, String username, String password, int roleId, String dob) {
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employeeIdCard = employeeIdCard;
        this.employeePhone = employeePhone;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.dob = dob;
    }
    public int getHours_worked() {
        return hours_worked;
    }

    public void setHours_worked(int hours_worked) {
        this.hours_worked = hours_worked;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeIdCard() {
        return employeeIdCard;
    }

    public void setEmployeeIdCard(String employeeIdCard) {
        this.employeeIdCard = employeeIdCard;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
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

    public static boolean addEmployeeToDatabase(Employee employee){
        try{
            Connection conn = DbConnection.getConnection();
            String sql = "INSERT INTO employees(employeeName,employeePhone,employeeAddress,employeeIdCard,username,password,dob,roleId) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, employee.getEmployeeName());
            statement.setString(2,employee.getEmployeePhone());
            statement.setString(3,employee.getEmployeeAddress());
            statement.setString(4,employee.getEmployeeIdCard());
            statement.setString(5,employee.getUsername());
            statement.setString(6,employee.getPassword());
            statement.setString(7,employee.getDob());
            statement.setInt(8,2);
            int rowAdded = statement.executeUpdate();
            statement.close();
            conn.close();
            return rowAdded > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static ObservableList<Employee> getEmployeeFromData(){
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        try{
            Connection conn = DbConnection.getConnection();
            String sql = "SELECT * FROM employees WHERE roleId = 2";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int employeeId = rs.getInt("employeeId");
                String employeeName = rs.getString("employeeName");
                String username = rs.getString("username");
                String employeePhone = rs.getString("employeePhone");
                String employeeIdCard = rs.getString("employeeIdCard");
                String password = rs.getString("password");
                String dob = rs.getString("dob");
                String employeeAddress = rs.getString("employeeAddress");
                int hours_worked = rs.getInt("hours_worked");
                Employee employee = new Employee(employeeId,employeeName,employeeAddress,employeeIdCard,employeePhone,dob,username,password,hours_worked);
                employeeList.add(employee);
            }
            statement.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return employeeList;
    }
    public static Employee searchEmployee(String employeeId){
        Employee employee = new Employee();
        try{
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT employeeId,employeeName,employeeAddress,employeeIdCard,dob,employeePhone,username,password FROM employees WHERE employeeId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,employeeId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                employee.setEmployeeId(rs.getInt("employeeId"));
                employee.setEmployeeName(rs.getString("employeeName"));
                employee.setEmployeeAddress(rs.getString("employeeAddress"));
                employee.setEmployeeIdCard(rs.getString("employeeIdCard"));
                employee.setDob(rs.getString("dob"));
                employee.setEmployeePhone(rs.getString("employeePhone"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
            }else {
                return null;
            }
            statement.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return employee;
    }
    public static boolean updateEmployee(String employeeId, String employeeName,String employeeIdCard,String employeeAddress,String dob,String employeePhone,String password){
        try{
            Connection connection = DbConnection.getConnection();
            String sql = "UPDATE employees SET employeeName = ?, employeeAddress = ?, employeeIdCard = ?, dob = ?, employeePhone = ?, password = ? WHERE employeeId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,employeeName);
            statement.setString(2,employeeAddress);
            statement.setString(3,employeeIdCard);
            statement.setString(4,dob);
            statement.setString(5,employeePhone);
            statement.setString(6,password);
            statement.setString(7,employeeId);
            int rowsAffected = statement.executeUpdate();
            statement.close();
            return rowsAffected > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteEmployee(String employeeId){
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "DELETE FROM employees WHERE employeeId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,employeeId);
            int rs = statement.executeUpdate();
            statement.close();
            return rs > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkAttend(String username){
        try{
            Connection conn = DbConnection.getConnection();
            String sql = "UPDATE employees SET hours_worked = hours_worked +2 WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,username);
            statement.executeUpdate();
            statement.close();
            conn.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static Employee getEmployee(String username) {
        Employee employee = null;

        try {
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT employeeName,employeeId FROM employees WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String employeeName = rs.getString("employeeName");
                int employeeId = rs.getInt("employeeId");
                employee = new Employee(employeeId,employeeName);
            }

            statement.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employee;
    }



}
