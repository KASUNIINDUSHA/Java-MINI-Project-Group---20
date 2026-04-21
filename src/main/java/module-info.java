module com.example.java_lms_group_20 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires com.jfoenix;
    requires java.sql;

    // 1. Open all controller packages to FXML so JavaFX can inject @FXML fields
    opens com.java_lms_group_20.Controller.Lecturer to javafx.fxml;
    opens com.java_lms_group_20.Controller to javafx.fxml;
    opens com.java_lms_group_20.Controller.Student to javafx.fxml;
    opens com.java_lms_group_20.Controller.Admin to javafx.fxml;
    opens com.java_lms_group_20.Controller.TO to javafx.fxml;
    opens com.java_lms_group_20 to javafx.fxml;
    opens View.Lecturer to javafx.fxml;

    // 2. VERY IMPORTANT: Open Model to javafx.base
    // This allows TableView to access your getters (e.g., getCourseCode)
    opens com.java_lms_group_20.Model to javafx.base, javafx.fxml;

    // 3. Export packages so other modules/JavaFX can use them
    exports com.java_lms_group_20;
    exports com.java_lms_group_20.Controller;
    exports com.java_lms_group_20.Controller.Student;
    exports com.java_lms_group_20.Controller.Admin;
    exports com.java_lms_group_20.Controller.TO;
    exports com.java_lms_group_20.Model;
}
