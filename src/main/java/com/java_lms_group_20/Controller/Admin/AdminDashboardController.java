package com.java_lms_group_20.Controller.Admin;

import com.java_lms_group_20.Controller.LoginController;
import com.java_lms_group_20.Model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AdminDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private StackPane contentArea;

    // Sidebar Buttons
    @FXML private Button btnShowDashboard;
    @FXML private Button btnRegisterUndergrad;
    @FXML private Button btnRegisterLecturer;
    @FXML private Button btnRegisterTO;
    @FXML private Button btnRegisterCourse;
    @FXML private Button btnManageMedical;
    @FXML private Button btnManageAttendance;
    @FXML private Button btnManageMarks; // Added this
    @FXML private Button btnManageNotices;
    @FXML private Button btnManageTimetable;

    private List<Button> sidebarButtons;
    private User currentUser;

    // Constants for styles
    private final String ACTIVE_STYLE = "-fx-background-color: #6366f1; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8;";
    private final String IDLE_STYLE = "-fx-background-color: transparent; -fx-text-fill: #94a3b8; -fx-padding: 12;";
    private final String HOVER_STYLE = "-fx-background-color: #1e293b; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8;";

    @FXML
    public void initialize() {
        sidebarButtons = Arrays.asList(
                btnShowDashboard,
                btnRegisterUndergrad,
                btnRegisterLecturer,
                btnRegisterTO,
                btnRegisterCourse,
                btnManageMedical,
                btnManageAttendance,
                btnManageMarks,
                btnManageNotices,
                btnManageTimetable
        );

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
        switchView("/View/AdminView/admin_dashboard_home.fxml");
    }

    @FXML
    private void showUndergradRegister() {
        setActiveButton(btnRegisterUndergrad);
        switchView("/View/AdminView/undergraduate_registration.fxml");
    }

    @FXML
    public void showLecturerRegister() {
        setActiveButton(btnRegisterLecturer);
        switchView("/View/AdminView/lecturer_registration.fxml");
    }

    @FXML
    public void showTORegister() {
        setActiveButton(btnRegisterTO);
        switchView("/View/AdminView/technical_officer_registration.fxml");
    }

    @FXML
    public void showCourseRegister() {
        setActiveButton(btnRegisterCourse);
        switchView("/View/AdminView/course_registration.fxml");
    }

    @FXML
    public void showMedicalManagement() {
        setActiveButton(btnManageMedical);
        switchView("/View/AdminView/admin_medical_view.fxml");
    }

    @FXML
    public void showAttendanceManagement() {
        setActiveButton(btnManageAttendance);
        switchView("/View/AdminView/attendance_view.fxml");
    }

    @FXML
    public void showMarksManagement() {
        setActiveButton(btnManageMarks);
        switchView("/View/AdminView/marks_view.fxml");
    }

    @FXML
    public void showNoticeManagement() {
        setActiveButton(btnManageNotices);
        switchView("/View/AdminView/admin_notice_view.fxml");
    }

    @FXML
    public void showTimetableManagement() {
        setActiveButton(btnManageTimetable);
        switchView("/View/AdminView/admin_timetable_view.fxml");
    }

    @FXML
    public void handleSignOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sign Out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to sign out?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            LoginController.logout(welcomeLabel);
        }
    }

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
