package com.main.Controller;

import com.main.Model.User;
import com.main.View.Admin.AdminInterface;
import com.main.View.Customer.CustomerInterface;
import com.main.View.Customer.Register;
import com.main.View.Employee.EmployeeInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Button cancelButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox adminCheck;
    @FXML
    private CheckBox customerCheck;
    @FXML
    private CheckBox employeeCheck;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    private int roleId = 0;


    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void adminCheckOnAction(ActionEvent e){
        roleId = 1;
        customerCheck.setSelected(false);
        employeeCheck.setSelected(false);
    }
    public void employeeCheckOnAction(ActionEvent e){
        roleId = 2;
        customerCheck.setSelected(false);
        adminCheck.setSelected(false);
    }
    public void customerCheckOnAction(ActionEvent e){
        roleId = 3;
        adminCheck.setSelected(false);
        employeeCheck.setSelected(false);
    }

    public void loginButtonOnAction(ActionEvent e) throws Exception  {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || roleId == 0) {
            showAlert(Alert.AlertType.ERROR,"Thông báo","Bạn cần điền đủ thông tin");
        } else if (User.checkLogin(username, password, roleId)) {
            showAlert(Alert.AlertType.INFORMATION,"Thông báo","Đăng nhập thành công");
            if(roleId == 1){
                AdminInterface adminInterface = new AdminInterface();
                adminInterface.start(new Stage());
            }else if(roleId == 2){
                EmployeeInterface employeeInterface = new EmployeeInterface(username);
                employeeInterface.start(new Stage());
            } else {
                CustomerInterface customerInterface = new CustomerInterface(username);
                customerInterface.start(new Stage());

            }

            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
        } else {
            showAlert(Alert.AlertType.ERROR,"Thông báo","Đăng nhập thất bại");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title,String message ) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void registerOnAction() throws Exception{
        Register register = new Register();
        register.start(new Stage());
        Stage currentStage = (Stage) registerButton.getScene().getWindow();
        currentStage.close();
    }

}
