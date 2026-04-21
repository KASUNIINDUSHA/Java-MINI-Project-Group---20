package com.java_lms_group_20.Controller.Lecturer;

import com.java_lms_group_20.Model.Lecturer;
import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Service.LecturerService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LecturerProfileController {
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtContact;
    @FXML private TextField txtDepartment;
    @FXML private TextField txtQualifications;
    @FXML private TextField txtSpecialization;
    @FXML private Label lblStatus;

    private final LecturerService service = new LecturerService();
    private int userId;

    public void initUser(User user) {
        userId = user.getUserID();
        try {
            var lecOpt = service.getByUserID(userId);
            if (lecOpt.isPresent()) {
                Lecturer l = lecOpt.get();
                txtFirstName.setText(l.getFirstName());
                txtLastName.setText(l.getLastName());
                txtEmail.setText(l.getEmail());
                txtContact.setText(l.getContactNo());
                txtDepartment.setText(l.getDepartment());
                txtQualifications.setText(l.getQualifications());
                txtSpecialization.setText(l.getSpecialization());
            }
        } catch (Exception e) {
            lblStatus.setText("Failed to load profile.");
        }
    }

    @FXML
    private void handleSave() {
        try {
            Lecturer lecturer = new Lecturer();
            lecturer.setUserID(userId);
            lecturer.setFirstName(txtFirstName.getText().trim());
            lecturer.setLastName(txtLastName.getText().trim());
            lecturer.setEmail(txtEmail.getText().trim());
            lecturer.setContactNo(txtContact.getText().trim());
            lecturer.setDepartment(txtDepartment.getText().trim());
            lecturer.setQualifications(txtQualifications.getText().trim());
            lecturer.setSpecialization(txtSpecialization.getText().trim());
            service.updateOwnProfile(lecturer);
            lblStatus.setText("Profile updated.");
            lblStatus.setStyle("-fx-text-fill: #10b981;");
        } catch (Exception e) {
            lblStatus.setText("Update failed.");
            lblStatus.setStyle("-fx-text-fill: #ef4444;");
        }
    }
}
