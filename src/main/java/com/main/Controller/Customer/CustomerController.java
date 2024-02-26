package com.main.Controller.Customer;

import com.main.Model.Customer;
import com.main.View.Customer.CustomerBooking;
import com.main.View.Customer.CustomerInformation;
import com.main.View.Customer.HistoryBooking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CustomerController {
    @FXML
    private Button logoutButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button createBookingButton;
    @FXML
    private Button historyBookingButton;
    private final String username;

    public CustomerController(String username){
        this.username = username;
    }
    public void updateEmployeeOnAction() throws Exception{
        Customer customer = Customer.searchCustomer(username);
        CustomerInformation customerInformation = new CustomerInformation(customer);
        customerInformation.start(new Stage());
        Stage currentStage = (Stage) updateButton.getScene().getWindow();
        currentStage.close();
    }
    public void createBookingOnAction()throws Exception{
        Customer customer = Customer.searchCustomer(username);
        CustomerBooking customerBooking = new CustomerBooking(customer);
        customerBooking.start(new Stage());
        Stage currentStage = (Stage) createBookingButton.getScene().getWindow();
        currentStage.close();
    }
    public void historyBookingOnAction() throws Exception{
        Customer customer = Customer.searchCustomer(username);
        HistoryBooking historyBooking = new HistoryBooking(customer);
        historyBooking.start(new Stage());
        Stage currentStage = (Stage) historyBookingButton.getScene().getWindow();
        currentStage.close();

    }
}
