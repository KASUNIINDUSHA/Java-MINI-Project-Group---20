package com.java_lms_group_20.Controller;

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

            if (user.getRoles().size() > 1) {
                navigateTo("/View/role_selection.fxml", "Choose Your Role", user);
            } else if (user.getRoles().contains(Role.ADMIN)) {
                navigateTo("/View/admin_dashboard.fxml", "Admin Dashboard", user);
            } else if (user.getRoles().contains(Role.UNDERGRADUATE)) {
                navigateTo("/View/student_dashboard.fxml", "Student Portal", user);
            } else {
                messageLabel.setText("Access Denied: No valid role assigned.");
            }

        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #e11d48;");
        }
    }

    private void navigateTo(String fxmlPath, String title, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.centerOnScreen();

            // CRITICAL: Show the stage FIRST so the FXML fields are injected
            stage.show();

            // Now safely pass the user to the specific controller
            if (fxmlPath.contains("admin_dashboard")) {
                AdminDashboardController controller = loader.getController();
                controller.initUser(user);
            } else if (fxmlPath.contains("student_dashboard")) {
                StudentDashboardController controller = loader.getController();
                controller.initUser(user);
            } else if (fxmlPath.contains("role_selection")) {
                RoleSelectionController controller = loader.getController();
                controller.initUser(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Navigation Error!");
        }
    }
}