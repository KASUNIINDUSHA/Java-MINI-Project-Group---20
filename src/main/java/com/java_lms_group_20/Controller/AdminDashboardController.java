package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class AdminDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private StackPane contentArea;
    @FXML private Button btnRegisterUndergrad;
    @FXML private Button btnRegisterLecturer;
    @FXML private Button btnRegisterTO;

    private User currentUser;

    public void initUser(User user) {
        this.currentUser = user;
        Platform.runLater(() -> {
            if (welcomeLabel != null) {
                welcomeLabel.setText("Welcome, " + user.getFirstName());
            }
        });
    }

    @FXML
    private void showUndergradRegister() {
        // 1. Reset all buttons to default style first
        resetButtonStyles();

        // 2. Apply "Active" background to this button (Indigo color)
        btnRegisterUndergrad.setStyle("-fx-background-color: #6366f1; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8;");

        // 3. Switch the view
        switchView("/View/undergraduate_registration.fxml");
    }

    private void resetButtonStyles() {
        // Set back to transparent/default slate color
        btnRegisterUndergrad.setStyle("-fx-background-color: transparent; -fx-text-fill: #94a3b8; -fx-padding: 12;");
    }

    private void switchView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Add Hover Effect via Code (Simple way without external CSS)
        btnRegisterUndergrad.setOnMouseEntered(e -> {
            if (!btnRegisterUndergrad.getStyle().contains("#6366f1")) { // Only hover if not active
                btnRegisterUndergrad.setStyle("-fx-background-color: #1e293b; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8;");
            }
        });

        btnRegisterUndergrad.setOnMouseExited(e -> {
            if (!btnRegisterUndergrad.getStyle().contains("#6366f1")) { // Only reset if not active
                resetButtonStyles();
            }
        });
    }
}