module com.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.main to javafx.fxml;
    opens com.main.Model to javafx.base;
    opens com.main.Controller to javafx.fxml;
    opens com.main.View to javafx.graphics;
    exports com.main;
    opens com.main.Controller.Admin to javafx.fxml;
    opens com.main.Controller.Customer to javafx.fxml;
    opens com.main.Controller.Employee to javafx.fxml;
    opens com.main.View.Admin to javafx.graphics;
    opens com.main.View.Employee to javafx.graphics;
    opens com.main.View.Customer to javafx.graphics;
}