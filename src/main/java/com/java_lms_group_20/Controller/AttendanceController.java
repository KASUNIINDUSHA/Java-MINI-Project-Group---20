package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Model.Attendance;
import com.java_lms_group_20.Service.AttendanceService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class AttendanceController {
    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance, String> colStuID, colCourse, colStatus;
    @FXML private TableColumn<Attendance, java.sql.Date> colDate;
    @FXML private TextField txtSearchID;
    @FXML private Label lblStatus;

    private final AttendanceService service = new AttendanceService();

    @FXML
    public void initialize() {
        colStuID.setCellValueFactory(new PropertyValueFactory<>("undergraduateId"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        handleSearch();
    }

    @FXML
    private void handleSearch() {
        try {
            var results = service.getAttendanceByStudentId(txtSearchID.getText());
            attendanceTable.setItems(FXCollections.observableArrayList(results));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @FXML
    private void markPresent() { updateStatus("Present", "#10b981"); }

    @FXML
    private void markAbsent() { updateStatus("Absent", "#ef4444"); }

    private void updateStatus(String status, String color) {
        Attendance selected = attendanceTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                service.changeAttendanceStatus(
                        selected.getUndergraduateId(),
                        selected.getCourseCode(),
                        selected.getSessionDate(),
                        status
                );
                lblStatus.setText("Updated " + selected.getCourseCode() + " to " + status);
                lblStatus.setStyle("-fx-text-fill: " + color);
                handleSearch();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}