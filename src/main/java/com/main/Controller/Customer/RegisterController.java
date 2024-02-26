package com.main.Controller.Customer;

import com.main.Model.Customer;
import com.main.View.Login;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegisterController {
    @FXML
    private Button cancelButton;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button createButton;

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

    public void createCustomerOnAction() throws Exception{
        String name = customerNameField.getText();
        LocalDate dob = dobField.getValue();
        String idCard = customerIdCardField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String address = customerAddressField.getText();
        String phone = customerPhoneField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String formattedDob = dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (name.isEmpty() || formattedDob.isEmpty() || idCard.isEmpty() || username.isEmpty() || password.isEmpty() || address.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Phải điền đầy đủ thông tin");
        }
        else if(password.equals(confirmPassword)){
            //thêm ngoại lệ kiểm tra username trùng nhau
            Customer customer = new Customer(name,address,idCard,phone,username,formattedDob,password,3);
            if (Customer.addCustomerToDatabase(customer)){
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đăng ký thành công");
                Login login = new Login();
                login.start(new Stage());
                Stage currentStage = (Stage) createButton.getScene().getWindow();
                currentStage.close();
            }else {
                showAlert(Alert.AlertType.ERROR, "Error", "Lỗi");
            }
        }else {
            showAlert(Alert.AlertType.ERROR,"Error","Mật khẩu không trùng khớp");
        }

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
