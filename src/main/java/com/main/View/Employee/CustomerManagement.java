package com.main.View.Employee;

import com.main.Controller.Employee.EmployeeController;
import com.main.View.Admin.AdminInterface;
import com.main.View.Admin.CusManagement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomerManagement extends Application {

    private String username1;
    public CustomerManagement(String username1){
        this.username1 = username1;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/employee/customerManagement.fxml"));
        loader.setControllerFactory(c -> new com.main.Controller.Employee.CustomerManagement(username1));
        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Employee!");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
