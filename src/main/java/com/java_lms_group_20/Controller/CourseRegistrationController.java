package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Model.Course;
import com.java_lms_group_20.Service.CourseService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CourseRegistrationController {

    @FXML private TextField txtCourseCode, txtCourseName, txtCredits;
    @FXML private Label statusLabel;

    private final CourseService service = new CourseService();

    @FXML
    private void handleSaveCourse() {
        processAction("SAVE");
    }

    @FXML
    private void handleUpdateCourse() {
        processAction("UPDATE");
    }

    @FXML
    private void handleDeleteCourse() {
        try {
            service.deleteCourse(txtCourseCode.getText().trim());
            showStatus("Course Deleted!", "#10b981");
            clearFields();
        } catch (Exception e) {
            showStatus(e.getMessage(), "#ef4444");
        }
    }

    private void processAction(String type) {
        try {
            Course course = new Course();
            course.setCourseCode(txtCourseCode.getText().trim());
            course.setCourseName(txtCourseName.getText().trim());

            if (txtCredits.getText().isEmpty()) throw new Exception("Credits required.");
            course.setCredits(Integer.parseInt(txtCredits.getText().trim()));

            if (type.equals("SAVE")) {
                service.registerCourse(course);
                showStatus("Course Saved!", "#10b981");
            } else {
                service.updateCourse(course);
                showStatus("Course Updated!", "#6366f1");
            }
            clearFields();
        } catch (Exception e) {
            showStatus(e.getMessage(), "#ef4444");
        }
    }

    private void showStatus(String msg, String color) {
        statusLabel.setText(msg);
        statusLabel.setStyle("-fx-text-fill: " + color + ";");
    }

    private void clearFields() {
        txtCourseCode.clear();
        txtCourseName.clear();
        txtCredits.clear();
        txtCourseCode.requestFocus();
    }
}