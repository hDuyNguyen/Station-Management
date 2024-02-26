package com.main.View.Employee;

import com.main.Controller.Employee.EmployeeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmployeeInterface extends Application {

    private final String username;

    public EmployeeInterface(String username) {
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage)throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/employee/employeeInterface.fxml"));
        loader.setControllerFactory(c -> new EmployeeController(username));
        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Employee!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
