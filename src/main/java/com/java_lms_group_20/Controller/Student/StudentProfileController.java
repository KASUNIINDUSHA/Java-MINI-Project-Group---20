package com.java_lms_group_20.Controller.Student;

import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Service.UndergraduateService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StudentProfileController {
    @FXML private Label lblStudentId;
    @FXML private Label lblName;
    @FXML private TextField txtContact;
    @FXML private TextField txtProfilePicture;
    @FXML private Label lblStatus;

    private final UndergraduateService service = new UndergraduateService();
    private int userId;

    public void initUser(User user) {
        userId = user.getUserID();
        try {
            var studentOpt = service.getByUserID(userId);
            if (studentOpt.isPresent()) {
                var student = studentOpt.get();
                lblStudentId.setText(student.getStudentID());
                lblName.setText(student.getFirstName() + " " + student.getLastName());
                txtContact.setText(student.getContactNo());
                txtProfilePicture.setText(student.getProfilePicture());
            }
        } catch (Exception e) {
            lblStatus.setText("Failed to load profile.");
        }
    }

    @FXML
    private void handleSave() {
        try {
            service.updateOwnProfile(userId, txtContact.getText().trim(), txtProfilePicture.getText().trim());
            lblStatus.setText("Profile updated.");
            lblStatus.setStyle("-fx-text-fill: #10b981;");
        } catch (Exception e) {
            lblStatus.setText("Update failed.");
            lblStatus.setStyle("-fx-text-fill: #ef4444;");
        }
    }
}
