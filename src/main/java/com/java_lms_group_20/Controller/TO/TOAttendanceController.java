package com.java_lms_group_20.Controller.TO;

import com.java_lms_group_20.Model.Attendance;
import com.java_lms_group_20.Service.AttendanceService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;

public class TOAttendanceController {
    @FXML private TextField txtSearchID;
    @FXML private TextField txtStudentId;
    @FXML private TextField txtCourseCode;
    @FXML private TextField txtSessionType;
    @FXML private TextField txtDate;
    @FXML private TextField txtStatus;
    @FXML private Label lblStatus;
    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance, Integer> colId;
    @FXML private TableColumn<Attendance, String> colStuID, colCourse, colType, colStatus;
    @FXML private TableColumn<Attendance, Date> colDate;

    private final AttendanceService service = new AttendanceService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("attendanceID"));
        colStuID.setCellValueFactory(new PropertyValueFactory<>("undergraduateId"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colType.setCellValueFactory(new PropertyValueFactory<>("sessionType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        handleSearch();
    }

    @FXML
    private void handleSearch() {
        try {
            var results = service.getRawAttendanceByStudentId(txtSearchID.getText());
            attendanceTable.setItems(FXCollections.observableArrayList(results));
            lblStatus.setText("Loaded " + results.size() + " attendance row(s).");
        } catch (SQLException e) {
            lblStatus.setText("Failed to load attendance.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        try {
            Attendance attendance = new Attendance();
            attendance.setUndergraduateId(txtStudentId.getText().trim());
            attendance.setCourseCode(txtCourseCode.getText().trim());
            attendance.setSessionType(txtSessionType.getText().trim());
            attendance.setSessionDate(Date.valueOf(txtDate.getText().trim()));
            attendance.setStatus(txtStatus.getText().trim());
            service.addAttendance(attendance);
            lblStatus.setText("Attendance added.");
            handleSearch();
        } catch (Exception e) {
            lblStatus.setText("Invalid input or add failed.");
        }
    }

    @FXML
    private void handleUpdateStatus() {
        Attendance selected = attendanceTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("Select a row to update status.");
            return;
        }
        try {
            service.changeAttendanceStatus(selected.getAttendanceID(), txtStatus.getText().trim());
            lblStatus.setText("Attendance status updated.");
            handleSearch();
        } catch (SQLException e) {
            lblStatus.setText("Update failed.");
        }
    }
}
