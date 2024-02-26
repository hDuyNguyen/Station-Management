package com.main.View.Admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StationManagement extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/main/admin/stationManagement.fxml"));
        primaryStage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Admin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
