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
            Undergraduate student = new Undergraduate();
            student.setFirstName(txtFirstName.getText().trim());
            student.setLastName(txtLastName.getText().trim());
            student.setUsername(txtUsername.getText().trim());
            student.setEmail(txtEmail.getText().trim());
            student.setStudentID(txtStudentID.getText().trim());
            student.setDegreeProgram(txtDegree.getText().trim());

            // Basic validation check before parsing
            if(txtLevel.getText().isEmpty() || txtGPA.getText().isEmpty()){
                throw new Exception("Level and GPA cannot be empty.");
            }

            student.setLevel(Integer.parseInt(txtLevel.getText().trim()));
            student.setGpa(Double.parseDouble(txtGPA.getText().trim()));

            service.registerStudent(student);

            statusLabel.setText("Successfully Registered!");
            statusLabel.setStyle("-fx-text-fill: #10b981;"); // Success Green

            // --- ADDED THIS LINE ---
            clearFields();

        } catch (NumberFormatException e) {
            statusLabel.setText("Level and GPA must be numbers.");
            statusLabel.setStyle("-fx-text-fill: #ef4444;");
        } catch (Exception e) {
            statusLabel.setText(e.getMessage());
            statusLabel.setStyle("-fx-text-fill: #ef4444;");
        }
    }

    /**
     * Resets all text fields to empty strings
     */
    private void clearFields() {
        txtFirstName.clear();
        txtLastName.clear();
        txtUsername.clear();
        txtEmail.clear();
        txtStudentID.clear();
        txtDegree.clear();
        txtLevel.clear();
        txtGPA.clear();

        // Move focus back to the first field for better user experience
        txtFirstName.requestFocus();
    }
}