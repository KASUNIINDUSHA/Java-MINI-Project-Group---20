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
import java.util.Arrays;
import java.util.List;

public class AdminDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private StackPane contentArea;

    // Sidebar Buttons
    @FXML private Button btnShowDashboard; // Added this missing injection
    @FXML private Button btnRegisterUndergrad;
    @FXML private Button btnRegisterLecturer;
    @FXML private Button btnRegisterTO;
    @FXML private Button btnRegisterCourse;

    private List<Button> sidebarButtons;
    private User currentUser;

    // Constants for styles to keep code clean
    private final String ACTIVE_STYLE = "-fx-background-color: #6366f1; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8;";
    private final String IDLE_STYLE = "-fx-background-color: transparent; -fx-text-fill: #94a3b8; -fx-padding: 12;";
    private final String HOVER_STYLE = "-fx-background-color: #1e293b; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8;";

    @FXML
    public void initialize() {
        // Group buttons for easier management
        sidebarButtons = Arrays.asList(btnShowDashboard, btnRegisterUndergrad,
                btnRegisterLecturer, btnRegisterTO, btnRegisterCourse);

        // Apply Hover effects to all buttons
        for (Button btn : sidebarButtons) {
            btn.setOnMouseEntered(e -> {
                if (!btn.getStyle().equals(ACTIVE_STYLE)) {
                    btn.setStyle(HOVER_STYLE);
                }
            });
            btn.setOnMouseExited(e -> {
                if (!btn.getStyle().equals(ACTIVE_STYLE)) {
                    btn.setStyle(IDLE_STYLE);
                }
            });
        }

        // Load initial dashboard view
        showDashboard();
    }

    public void initUser(User user) {
        this.currentUser = user;
        Platform.runLater(() -> {
            if (welcomeLabel != null) {
                welcomeLabel.setText("Welcome, " + user.getFirstName());
            }
        });
    }

    @FXML
    public void showDashboard() {
        setActiveButton(btnShowDashboard);
        switchView("/View/admin_dashboard_home.fxml");
    }

    @FXML
    private void showUndergradRegister() {
        setActiveButton(btnRegisterUndergrad);
        switchView("/View/undergraduate_registration.fxml");
    }

    @FXML
    public void showLecturerRegister() {
        setActiveButton(btnRegisterLecturer);
        switchView("/View/lecturer_registration.fxml");
    }

    @FXML
    public void showTORegister() {
        setActiveButton(btnRegisterTO);
        switchView("/View/technical_officer_registration.fxml");
    }

    @FXML
    public void showCourseRegister() {
        setActiveButton(btnRegisterCourse);
        switchView("/View/course_registration.fxml");
    }

    // Helper method to handle UI state
    private void setActiveButton(Button activeBtn) {
        for (Button btn : sidebarButtons) {
            if (btn == activeBtn) {
                btn.setStyle(ACTIVE_STYLE);
            } else {
                btn.setStyle(IDLE_STYLE);
            }
        }
    }

    private void switchView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Could not load FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}