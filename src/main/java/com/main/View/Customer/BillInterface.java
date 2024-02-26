package com.main.View.Customer;

import com.main.Controller.Customer.CustomerBill;
import com.main.Controller.Customer.CustomerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BillInterface extends Application {
    private int bookingId;

    public BillInterface(int bookingId){
        this.bookingId = bookingId;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/customer/billCustomer.fxml"));
        loader.setControllerFactory(c -> new CustomerBill(bookingId));
        Parent root = loader.load();
        Scene scene = new Scene(root, 362, 468);
        primaryStage.setTitle("Employee!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
