package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Model.Lecturer;
import com.java_lms_group_20.Service.LecturerService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LecturerRegistrationController {

    @FXML private TextField txtFirstName, txtLastName, txtUsername, txtEmail;
    @FXML private TextField txtLecturerID, txtDepartment, txtQualifications, txtSpecialization;
    @FXML private Label statusLabel;

    private final LecturerService service = new LecturerService();

    @FXML
    private void handleSaveLecturer() {
        try {
            Lecturer lecturer = prepareModel();
            boolean isSaved = service.registerLecturer(lecturer);

            if (isSaved) {
                showSuccess("Lecturer Registered Successfully!");
                clearFields();
            } else {
                showError("Registration failed. Please check your data.");
            }
        } catch (Exception e) {
            // This catches things like duplicate usernames or SQL errors
            showError("Error: " + e.getMessage());
            e.printStackTrace(); // Check your console for the real error!
        }
    }

    @FXML
    private void handleUpdateLecturer() {
        try {
            Lecturer lecturer = prepareModel();
            if (service.updateLecturer(lecturer)) {
                showSuccess("Lecturer Updated!");
            } else { showError("Lecturer ID not found."); }
        } catch (Exception e) { showError(e.getMessage()); }
    }

    @FXML
    private void handleDeleteLecturer() {
        String id = txtLecturerID.getText().trim();
        if (id.isEmpty()) { showError("Enter Lecturer ID to delete."); return; }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Lecturer " + id + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.YES) {
                try {
                    if (service.deleteLecturer(id)) {
                        showSuccess("Lecturer Deleted.");
                        clearFields();
                    } else { showError("ID not found."); }
                } catch (Exception e) { showError("Error: " + e.getMessage()); }
            }
        });
    }

    private Lecturer prepareModel() throws Exception {
        if (txtLecturerID.getText().isEmpty() || txtUsername.getText().isEmpty()) {
            throw new Exception("ID and Username are required.");
        }
        Lecturer l = new Lecturer();
        l.setFirstName(txtFirstName.getText().trim());
        l.setLastName(txtLastName.getText().trim());
        l.setUsername(txtUsername.getText().trim());
        l.setEmail(txtEmail.getText().trim());
        l.setLecturerID(txtLecturerID.getText().trim());
        l.setDepartment(txtDepartment.getText().trim());
        l.setQualifications(txtQualifications.getText().trim());
        l.setSpecialization(txtSpecialization.getText().trim());
        return l;
    }

    private void showSuccess(String m) { statusLabel.setText(m); statusLabel.setStyle("-fx-text-fill: #10b981;"); }
    private void showError(String m) { statusLabel.setText(m); statusLabel.setStyle("-fx-text-fill: #ef4444;"); }

    @FXML
    private void clearFields() {
        txtFirstName.clear(); txtLastName.clear(); txtUsername.clear(); txtEmail.clear();
        txtLecturerID.clear(); txtDepartment.clear(); txtQualifications.clear(); txtSpecialization.clear();

    }
}