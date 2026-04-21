package com.java_lms_group_20.Controller.TO;

import com.java_lms_group_20.Model.TechnicalOfficer;
import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Service.TechnicalOfficerService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TOProfileController {
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtContact;
    @FXML private TextField txtDepartment;
    @FXML private TextField txtSkills;
    @FXML private Label lblStatus;

    private final TechnicalOfficerService service = new TechnicalOfficerService();
    private int userId;

    public void initUser(User user) {
        this.userId = user.getUserID();
        try {
            var toOpt = service.getByUserID(userId);
            if (toOpt.isPresent()) {
                var to = toOpt.get();
                txtFirstName.setText(to.getFirstName());
                txtLastName.setText(to.getLastName());
                txtEmail.setText(to.getEmail());
                txtContact.setText(to.getContactNo());
                txtDepartment.setText(to.getDepartment());
                txtSkills.setText(to.getTechnicalSkills());
            }
        } catch (Exception e) {
            lblStatus.setText("Failed to load profile.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSave() {
        try {
            TechnicalOfficer officer = new TechnicalOfficer();
            officer.setUserID(userId);
            officer.setFirstName(txtFirstName.getText().trim());
            officer.setLastName(txtLastName.getText().trim());
            officer.setEmail(txtEmail.getText().trim());
            officer.setContactNo(txtContact.getText().trim());
            officer.setDepartment(txtDepartment.getText().trim());
            officer.setTechnicalSkills(txtSkills.getText().trim());
            service.updateOwnProfile(officer);
            lblStatus.setText("Profile updated.");
            lblStatus.setStyle("-fx-text-fill: #10b981;");
        } catch (Exception e) {
            lblStatus.setText("Update failed.");
            lblStatus.setStyle("-fx-text-fill: #ef4444;");
        }
    }
}
