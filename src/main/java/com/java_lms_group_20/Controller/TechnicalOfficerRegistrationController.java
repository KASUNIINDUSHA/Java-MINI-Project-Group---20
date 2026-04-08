package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Model.TechnicalOfficer;
import com.java_lms_group_20.Service.TechnicalOfficerService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TechnicalOfficerRegistrationController {

    @FXML private TextField txtFirstName, txtLastName, txtUsername, txtEmail;
    @FXML private TextField txtTechOfficerID, txtDepartment, txtSkills;
    @FXML private Label statusLabel;

    private final TechnicalOfficerService service = new TechnicalOfficerService();

    @FXML
    private void handleSaveTO() {
        try {
            if (service.registerTechnicalOfficer(prepareModel())) {
                showSuccess("TO Registered Successfully!");
                clearFields();
            }
        } catch (Exception e) { showError(e.getMessage()); }
    }

    @FXML
    private void handleUpdateTO() {
        try {
            if (service.updateTechnicalOfficer(prepareModel())) {
                showSuccess("TO Record Updated!");
            } else { showError("Officer ID not found."); }
        } catch (Exception e) { showError(e.getMessage()); }
    }

    @FXML
    private void handleDeleteTO() {
        String id = txtTechOfficerID.getText().trim();
        if (id.isEmpty()) { showError("Enter ID to delete."); return; }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete TO " + id + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.YES) {
                try {
                    if (service.deleteTechnicalOfficer(id)) {
                        showSuccess("TO Deleted Successfully.");
                        clearFields();
                    } else { showError("ID not found."); }
                } catch (Exception e) { showError(e.getMessage()); }
            }
        });
    }

    private TechnicalOfficer prepareModel() {
        TechnicalOfficer to = new TechnicalOfficer();
        to.setFirstName(txtFirstName.getText().trim());
        to.setLastName(txtLastName.getText().trim());
        to.setUsername(txtUsername.getText().trim());
        to.setEmail(txtEmail.getText().trim());
        to.setTechOfficerID(txtTechOfficerID.getText().trim());
        to.setDepartment(txtDepartment.getText().trim());
        to.setTechnicalSkills(txtSkills.getText().trim());
        return to;
    }

    private void showSuccess(String m) { statusLabel.setText(m); statusLabel.setStyle("-fx-text-fill: #10b981;"); }
    private void showError(String m) { statusLabel.setText(m); statusLabel.setStyle("-fx-text-fill: #ef4444;"); }

    @FXML
    private void clearFields() {
        txtFirstName.clear(); txtLastName.clear(); txtUsername.clear(); txtEmail.clear();
        txtTechOfficerID.clear(); txtDepartment.clear(); txtSkills.clear();
    }
}