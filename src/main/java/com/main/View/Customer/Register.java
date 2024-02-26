package com.main.View.Customer;

import com.main.View.Admin.AdminInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Register extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Register.class.getResource("/com/main/customer/register.fxml"));
        primaryStage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root, 350, 600);
        primaryStage.setTitle("Register!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
