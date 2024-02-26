package com.main.Controller.Employee;

import com.main.Model.Booking;
import com.main.Model.Employee;
import com.main.View.Employee.CustomerManagement;
import com.main.View.Employee.EmployeeInterface;
import com.main.View.Login;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static com.main.Model.Employee.getEmployee;

public class Bill implements Initializable {
    @FXML
    private TableColumn<Booking, String> customerName;

    @FXML
    private TableColumn<Booking, String> customerPhone;

    @FXML
    private TableColumn<Booking, String> bookingDate;

    @FXML
    private TableColumn<Booking, Integer> id;

    @FXML
    private TableColumn<Booking, String> stationName;
    @FXML
    private TableColumn<Booking,Boolean> status;
    @FXML
    private TableView<Booking> table;
    @FXML
    private TableColumn<Booking, LocalTime> timeIn;

    @FXML
    private TableColumn<Booking, LocalTime> timeOut;
    @FXML
    private TableColumn<Booking, Double> totalPrice;
    @FXML
    private TextField totalPriceField;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private TextField bookingDateField;
    @FXML
    private TextField timeInField;
    @FXML
    private TextField timeOutField;
    @FXML
    private TextField stationNameField;
    @FXML
    private Button confirmButton;
    @FXML
    private Button searchBookingButton;
    @FXML
    private TextField bookingIdField;
    @FXML
    private CheckBox statusCheckBox;
    @FXML
    private TextField employeeNameField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button customerButton;
    @FXML
    private Button billButton;
    private String username;
    public Bill(String username){
        this.username = username;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        stationName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStation().getStationName()));
        bookingDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBookingDate().toString()));
        customerName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getCustomerName()));
        customerPhone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getCustomerPhone()));
        timeIn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        timeOut.setCellValueFactory(new PropertyValueFactory<>("timeOut"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Booking> bookingList = Booking.getBooking();
        table.setItems(bookingList);
        Employee employee = getEmployee(username);
        employeeNameField.setText(employee != null ? employee.getEmployeeName() : "");
    }
    public void searchBookingOnAction(){
        String bookingId =bookingIdField.getText();
        if (bookingId.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION,"Thông báo","Phải nhập phiếu sân");
        }else {
            searchBooking();
        }
    }
    private void searchBooking(){
        int bookingId = Integer.parseInt(bookingIdField.getText());
        ObservableList<Booking> bookings = Booking.getBooking();
        Booking foundBooking = null;
        for(Booking booking : bookings){
            if(booking.getBookingId() == bookingId){
                foundBooking = booking;
                break;
            }
        }
        if(foundBooking != null){
            showAlert(Alert.AlertType.INFORMATION,"Thông báo","Đã tìm thấy thông tin");
            bookingIdField.setText(String.valueOf(foundBooking.getBookingId()));
            customerNameField.setText(foundBooking.getCustomer().getCustomerName());
            customerPhoneField.setText(foundBooking.getCustomer().getCustomerPhone());
            stationNameField.setText(foundBooking.getStation().getStationName());
            bookingDateField.setText(String.valueOf(foundBooking.getBookingDate()));
            timeInField.setText(String.valueOf(foundBooking.getTimeIn()));
            timeOutField.setText(String.valueOf(foundBooking.getTimeOut()));
            totalPriceField.setText(String.valueOf(foundBooking.getTotalPrice()));
            statusCheckBox.setSelected(foundBooking.isStatus());
        }else {
            showAlert(Alert.AlertType.ERROR,"Thông báo","Không tìm thấy thông tin");
        }

    }
    public void confirmBillOnAction(){
        int employeeId = getEmployee(username).getEmployeeId();
        int bookingId = Integer.parseInt(bookingIdField.getText());
        if(bookingId == 0){
            showAlert(Alert.AlertType.ERROR,"Thông báo","Phải nhập ID phiếu");
        }else if(!statusCheckBox.isSelected()){
            showAlert(Alert.AlertType.ERROR,"Thông báo","Phải xác nhận phiếu trước khi tạo hoá đơn");
        }
        else  {
            com.main.Model.Bill bill = new com.main.Model.Bill();
            bill.setBooking(new Booking(bookingId));
            bill.setEmployee(new Employee(employeeId));
            com.main.Model.Bill.updateStatus(bookingId);
            boolean success = com.main.Model.Bill.createBill(bill);
            if(success){
                showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Hoá đơn đã được tạo thành công!");
                ObservableList<Booking> bookingList = Booking.getBooking();
                table.setItems(bookingList);
            } else {
                showAlert(Alert.AlertType.ERROR, "Thông báo", "Lỗi khi tạo hoá đơn!");
            }
        }
    }
    public void cancelOnAction() throws Exception{
        EmployeeInterface employeeInterface = new EmployeeInterface(username);
        employeeInterface.start(new Stage());
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
    }
    private void showAlert(Alert.AlertType alertType, String title, String message ) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
    public void createCustomerOnAction() throws Exception{
        com.main.View.Employee.CustomerManagement customerManagement = new CustomerManagement(username);
        customerManagement.start(new Stage());
        Stage currentStage = (Stage) customerButton.getScene().getWindow();
        currentStage.close();
    }
    public void logoutButtonOnAction() throws Exception{
        Login login = new Login();
        login.start(new Stage());
        Stage currentStage = (Stage) logOutButton.getScene().getWindow();
        currentStage.close();
    }

}
