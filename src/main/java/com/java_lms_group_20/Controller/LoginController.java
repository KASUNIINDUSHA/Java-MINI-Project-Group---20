package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Controller.Admin.AdminDashboardController;
import com.java_lms_group_20.Controller.Student.StudentDashboardController;
import com.java_lms_group_20.Controller.Lecturer.LecturerDashboardController; // Added Import
import com.java_lms_group_20.Controller.TO.TODashboardController;
import com.java_lms_group_20.Model.Role;
import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Service.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
    @FXML private Button loginButton;

    private final LoginService loginService = new LoginService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter all fields.");
            return;
        }

        try {
            User user = loginService.authenticate(username, password);

            // Navigate based on roles
            if (user.getRoles().size() > 1) {
                navigateTo("/View/role_selection.fxml", "Choose Your Role", user);
            } else if (user.getRoles().contains(Role.ADMIN)) {
                navigateTo("/View/AdminView/admin_dashboard.fxml", "Admin Dashboard", user);
            } else if (user.getRoles().contains(Role.UNDERGRADUATE)) {
                navigateTo("/View/Student/student_dashboard.fxml", "Student Portal", user);
            } else if (user.getRoles().contains(Role.LECTURER)) { // Role already handled here
                navigateTo("/View/Lecturer/lecturer_dashboard.fxml", "Lecturer Portal", user);
            } else if (user.getRoles().contains(Role.TO)) {
                navigateTo("/View/TO/to_dashboard.fxml", "Technical Officer Portal", user);
            } else {
                messageLabel.setText("Access Denied: No valid role assigned.");
            }

        } catch (Exception e) {
            messageLabel.setText(e.getMessage() );
            messageLabel.setStyle("-fx-text-fill: #e11d48;");
        }
    }

    private void navigateTo(String fxmlPath, String title, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();

            // Pass user data to the relevant controller
            if (controller instanceof AdminDashboardController) {
                ((AdminDashboardController) controller).initUser(user);
            } else if (controller instanceof StudentDashboardController) {
                ((StudentDashboardController) controller).initUser(user);
            } else if (controller instanceof LecturerDashboardController) { // FIXED: Pass to Lecturer
                ((LecturerDashboardController) controller).initUser(user);
            } else if (controller instanceof TODashboardController) {
                ((TODashboardController) controller).initUser(user);
            } else if (controller instanceof RoleSelectionController) {
                ((RoleSelectionController) controller).initUser(user);
            }

            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Navigation Error: Check FXML Paths");
        }
    }

    // Inside LoginController class
    public static void logout(javafx.scene.Node node) {
        try {
            // Load the login view - ensure this path is correct for your resources
            java.net.URL fxmlLocation = LoginController.class.getResource("/View/login_page.fxml");

            if (fxmlLocation == null) {
                System.err.println("Logout Error: Could not find /View/login_page.fxml");
                return;
            }

            Parent root = FXMLLoader.load(fxmlLocation);
            Stage stage = (Stage) node.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("LMS - Login");
            stage.centerOnScreen();
            stage.show();

            System.out.println("Session ended. Redirecting to login...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
