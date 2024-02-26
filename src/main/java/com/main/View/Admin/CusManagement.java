package com.main.View.Admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CusManagement extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(CusManagement.class.getResource("/com/main/admin/cusManagement.fxml"));
        primaryStage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Admin");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
