package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Model.Undergraduate;
import com.java_lms_group_20.Service.UndergraduateService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UndergraduateRegistrationController {

    @FXML private TextField txtFirstName, txtLastName, txtUsername, txtEmail;
    @FXML private TextField txtStudentID, txtDegree, txtLevel, txtGPA;
    @FXML private Label statusLabel;

    private final UndergraduateService service = new UndergraduateService();

    @FXML
    private void handleSaveUser() {
        try {
            Undergraduate student = prepareStudentModel();
            if (service.registerStudent(student)) {
                showSuccess("Successfully Registered!");
                clearFields();
            }
        } catch (Exception e) {
            showError("Registration Failed: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateUser() {
        try {
            Undergraduate student = prepareStudentModel();
            if (service.updateStudent(student)) {
                showSuccess("Student Record Updated!");
            } else {
                showError("Update Failed: Student ID not found.");
            }
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteUser() {
        String id = txtStudentID.getText().trim();
        if (id.isEmpty()) {
            showError("Please enter a Student ID to delete.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete student " + id + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    if (service.deleteStudent(id)) {
                        showSuccess("Student Deleted.");
                        clearFields();
                    } else {
                        showError("Delete Failed: Student ID not found.");
                    }
                } catch (Exception e) {
                    showError("Delete Failed: " + e.getMessage());
                }
            }
        });
    }

    private Undergraduate prepareStudentModel() throws Exception {
        if(txtUsername.getText().isEmpty() || txtStudentID.getText().isEmpty()) {
            throw new Exception("Username and Student ID are required.");
        }

        Undergraduate s = new Undergraduate();
        s.setFirstName(txtFirstName.getText().trim());
        s.setLastName(txtLastName.getText().trim());
        s.setUsername(txtUsername.getText().trim());
        s.setEmail(txtEmail.getText().trim());
        s.setStudentID(txtStudentID.getText().trim());
        s.setDegreeProgram(txtDegree.getText().trim());

        try {
            s.setLevel(Integer.parseInt(txtLevel.getText().trim()));
            s.setGpa(Double.parseDouble(txtGPA.getText().trim()));
        } catch (NumberFormatException e) {
            throw new Exception("Level and GPA must be numeric.");
        }

        return s;
    }

    private void showSuccess(String msg) {
        statusLabel.setText(msg);
        statusLabel.setStyle("-fx-text-fill: #10b981;");
    }

    private void showError(String msg) {
        statusLabel.setText(msg);
        statusLabel.setStyle("-fx-text-fill: #ef4444;");
    }

    @FXML
    private void clearFields() {
        txtFirstName.clear();
        txtLastName.clear();
        txtUsername.clear();
        txtEmail.clear();
        txtStudentID.clear();
        txtDegree.clear();
        txtLevel.clear();
        txtGPA.clear();
        statusLabel.setText("");
    }
}