module com.example.java_lms_group_20 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;

    opens com.java_lms_group_20 to javafx.fxml;
    opens com.java_lms_group_20.Controller to javafx.fxml;
    exports com.java_lms_group_20;
}
