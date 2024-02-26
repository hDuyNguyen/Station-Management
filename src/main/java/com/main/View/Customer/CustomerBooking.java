package com.main.View.Customer;

import com.main.Controller.Customer.CustomerBookingController;
import com.main.Model.Customer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomerBooking extends Application {
    private Customer customer;

    public CustomerBooking(Customer customer) {
        this.customer = customer;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/customer/booking.fxml"));
        loader.setControllerFactory(c -> new CustomerBookingController(customer));
        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Đặt sân bóng");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
