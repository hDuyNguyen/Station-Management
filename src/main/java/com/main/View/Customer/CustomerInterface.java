package com.main.View.Customer;

import com.main.Controller.Customer.CustomerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomerInterface extends Application {
    private String username;

    public CustomerInterface(String username) {
        this.username = username;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)throws  Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/customer/customerInterface.fxml"));
        loader.setControllerFactory(c -> new CustomerController(username));
        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Employee!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
