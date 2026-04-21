package com.java_lms_group_20.Controller.Student;

import com.java_lms_group_20.Controller.LoginController;
import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Repository.UndergraduateRepository;
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
import java.util.Objects;
import java.util.stream.Collectors;

public class StudentDashboardController {

    @FXML private Label lblWelcome, lblStudentID;
    @FXML private StackPane contentArea;
    @FXML private Button btnProfile, btnHome, btnCourses, btnAttendance, btnNotices, btnTimetable;

    private List<Button> sidebarButtons;
    private User currentUser;
    private final UndergraduateRepository undergradRepo = new UndergraduateRepository();

    private final String ACTIVE_STYLE = "-fx-background-color: #6366f1; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8;";
    private final String IDLE_STYLE = "-fx-background-color: transparent; -fx-text-fill: #94a3b8; -fx-padding: 12;";
    private final String HOVER_STYLE = "-fx-background-color: #1e293b; -fx-text-fill: white; -fx-padding: 12; -fx-background-radius: 8;";

    @FXML
    public void initialize() {
        sidebarButtons = Arrays.asList(btnProfile, btnHome, btnCourses, btnAttendance, btnNotices, btnTimetable)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        for (Button btn : sidebarButtons) {
            btn.setOnMouseEntered(e -> { if (!btn.getStyle().equals(ACTIVE_STYLE)) btn.setStyle(HOVER_STYLE); });
            btn.setOnMouseExited(e -> { if (!btn.getStyle().equals(ACTIVE_STYLE)) btn.setStyle(IDLE_STYLE); });
        }
    }

    public void initUser(User user) {
        if (user == null) return;
        this.currentUser = user;

        String studentID = undergradRepo.getStudentIDByUserID(user.getUserID());

        Platform.runLater(() -> {
            lblWelcome.setText("Welcome, " + user.getFirstName());
            lblStudentID.setText(studentID != null ? studentID : "ID Not Found");
        });

        showProfile();
    }

    @FXML
    public void showProfile() {
        if (currentUser == null) return;
        setActiveButton(btnProfile);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Student/student_profile_view.fxml"));
            Parent view = loader.load();
            StudentProfileController controller = loader.getController();
            controller.initUser(currentUser);
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showHome() {
        setActiveButton(btnHome);
        switchView("/View/Student/student_home.fxml");
    }

    @FXML
    public void showCourses() {
        if (currentUser == null) return;

        setActiveButton(btnCourses);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Student/courses_view.fxml"));
            Parent view = loader.load();

            // FIXED: Cast to the specific controller and call the method
            CourseViewController controller = loader.getController();
            String studentID = undergradRepo.getStudentIDByUserID(currentUser.getUserID());

            if (studentID != null) {
                System.out.println("Loading courses for student: " + studentID);
                controller.loadCourses(studentID);
            } else {
                System.err.println("Student ID not found for user: " + currentUser.getUserID());
            }

            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error loading courses view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void showAttendance() {
        if (currentUser == null) return;

        setActiveButton(btnAttendance);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Student/student_attendance_view.fxml"));
            Parent view = loader.load();

            StudentAttendanceViewController controller = loader.getController();
            String studentID = undergradRepo.getStudentIDByUserID(currentUser.getUserID());

            if (studentID != null) {
                controller.loadAttendance(studentID);
            }

            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showNotices() {
        setActiveButton(btnNotices);
        switchView("/View/Student/student_notices_view.fxml");
    }

    @FXML
    public void showTimetable() {
        if (currentUser == null) return;
        setActiveButton(btnTimetable);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Student/student_timetable_view.fxml"));
            Parent view = loader.load();
            StudentTimetableController controller = loader.getController();
            String studentID = undergradRepo.getStudentIDByUserID(currentUser.getUserID());
            if (studentID != null) {
                controller.loadByStudentId(studentID);
            }
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSignOut() {
        LoginController.logout(lblWelcome);
    }

    private void setActiveButton(Button activeBtn) {
        if (activeBtn == null) return;
        for (Button btn : sidebarButtons) {
            btn.setStyle(btn == activeBtn ? ACTIVE_STYLE : IDLE_STYLE);
        }
    }

    private void switchView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Could not load view: " + fxmlPath);
        }
    }
}
