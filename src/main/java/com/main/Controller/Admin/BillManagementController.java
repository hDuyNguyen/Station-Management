package com.main.Controller.Admin;

import com.main.Model.Bill;
import com.main.View.Admin.AdminInterface;
import com.main.View.Admin.BillManagement;
import com.main.View.Admin.CusManagement;
import com.main.View.Admin.EmployeeManagement;
import com.main.View.Admin.StationManagement;
import com.main.View.Employee.CustomerManagement;
import com.main.View.Login;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class BillManagementController implements Initializable {
    @FXML
    private TableColumn<Bill, Integer> billId;

    @FXML
    private TableColumn<Bill, LocalDate> bookingDate;

    @FXML
    private TableColumn<Bill, String> customerName;

    @FXML
    private TableColumn<Bill, String> customerPhone;

    @FXML
    private TableColumn<Bill, String> username;

    @FXML
    private TableColumn<Bill, String> stationName;

    @FXML
    private TableView<Bill> table;

    @FXML
    private TableColumn<Bill, LocalTime> timeIn;
    @FXML
    private TableColumn<Bill, LocalTime> timeOut;
    @FXML
    private TableColumn<Bill, Double> totalPrice;
    @FXML
    private Button searchButton;
    @FXML
    private DatePicker bookingDateField;

    @FXML
    private Button employeeButton;
    @FXML
    private Button stationButton;
    @FXML
    private Button billButton;
    @FXML
    private Button customerButton;
    @FXML
    private Button logOutButton;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        billId.setCellValueFactory(new PropertyValueFactory<>("billId"));
        customerName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBooking().getCustomer().getCustomerName()));
        customerPhone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBooking().getCustomer().getCustomerPhone()));
        bookingDate.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBooking().getBookingDate()));
        timeIn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBooking().getTimeIn()));
        timeOut.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBooking().getTimeOut()));
        username.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmployee().getUsername()));
        stationName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBooking().getStation().getStationName()));
        totalPrice.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getBooking().getTotalPrice()));
        ObservableList<Bill> billList = Bill.getBillFromData();
        table.setItems(FXCollections.observableList(billList));
    }
    public void searchBillOnAction(){
    LocalDate selectedDate = bookingDateField.getValue();
        if(selectedDate != null){
            ObservableList<Bill> filteredBills = Bill.getBillsByDate(selectedDate);
            table.setItems(filteredBills);
        }else {
            ObservableList<Bill> billList = Bill.getBillFromData();
            table.setItems(FXCollections.observableList(billList));
        }
    }
    public void employeeButtonOnAction() throws Exception {
        EmployeeManagement employeeManagement = new EmployeeManagement();
        employeeManagement.start(new Stage());
        Stage currentStage = (Stage) employeeButton.getScene().getWindow();
        currentStage.close();
    }
    public void stationButtonOnAction() throws Exception {
        StationManagement stationManagement = new StationManagement();
        stationManagement.start(new Stage());
        Stage currentStage = (Stage) stationButton.getScene().getWindow();
        currentStage.close();
    }
    public void billButtonOnAction() throws Exception {
        BillManagement billManagement = new BillManagement();
        billManagement.start(new Stage());
        Stage currentStage = (Stage) billButton.getScene().getWindow();
        currentStage.close();
    }
    public void customerButtonOnAction() throws Exception{
        CusManagement customerManagement = new CusManagement();
        customerManagement.start(new Stage());
        Stage currentStage = (Stage) customerButton.getScene().getWindow();
        currentStage.close();
    }
    public void logOutOnAction() throws Exception {
        Login login = new Login();
        login.start(new Stage());
        Stage currentStage = (Stage) logOutButton.getScene().getWindow();
        currentStage.close();
    }
}
