package com.main.Controller.Customer;

import com.main.Model.Customer;
import com.main.View.Customer.CustomerBooking;
import com.main.View.Customer.CustomerInformation;
import com.main.View.Customer.CustomerInterface;
import com.main.View.Customer.HistoryBooking;
import com.main.View.Login;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CustomerInformationController implements Initializable {
    @FXML
    private TextField customerAddressField;

    @FXML
    private TextField customerIdCardField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField customerPhoneField;

    @FXML
    private DatePicker dobField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;
    @FXML
    private Button updateButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;

    @FXML
    private Button inforButton;

    @FXML
    private Button logOutButton;
    @FXML
    private Button historyBookingButton;

    private final Customer customer;

    public CustomerInformationController(Customer customer) {
        this.customer = customer;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerNameField.setText(customer.getCustomerName());
        customerAddressField.setText(customer.getCustomerAddress());
        customerIdCardField.setText(customer.getCustomerIdCard());
        dobField.setValue(LocalDate.parse(customer.getDob()));
        customerPhoneField.setText(customer.getCustomerPhone());
        usernameField.setText(customer.getUsername());
        passwordField.setText(customer.getPassword());
        usernameField.setEditable(false);
//        System.out.println(customer.getCustomerName());
    }
    public void updateInformationOnAction(){
        String name = customerNameField.getText();
        String address = customerAddressField.getText();
        String idCard = customerIdCardField.getText();
        LocalDate dob = dobField.getValue();
        String phone = customerPhoneField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String formattedDob = dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        boolean isUpdated = Customer.updateCustomer(name,idCard,address,formattedDob,phone,username,password);
        if(isUpdated){
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thông tin  được cập nhật ");
        }else {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật thông tin");
        }
    }
    public void cancelOnAction() throws Exception{
        CustomerInterface customerInterface = new CustomerInterface(customer.getUsername());
        customerInterface.start(new Stage());
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void createBooking()throws Exception{
        CustomerBooking controller = new CustomerBooking(customer);
        controller.start(new Stage());
        Stage currentStage = (Stage) createButton.getScene().getWindow();
        currentStage.close();
    }
    public void historyBooking() throws Exception{
        HistoryBooking historyBooking = new HistoryBooking(customer);
        historyBooking.start(new Stage());
        Stage currentStage = (Stage) historyBookingButton.getScene().getWindow();
        currentStage.close();
    }
    public void updateInformation() throws Exception {
        CustomerInformation customerInformation = new CustomerInformation(customer);
        customerInformation.start(new Stage());
        Stage currentStage = (Stage) inforButton.getScene().getWindow();
        currentStage.close();
    }
    public void logOut()throws Exception{
        Login login = new Login();
        login.start(new Stage());
        Stage currentStage = (Stage) logOutButton.getScene().getWindow();
        currentStage.close();
    }



}
