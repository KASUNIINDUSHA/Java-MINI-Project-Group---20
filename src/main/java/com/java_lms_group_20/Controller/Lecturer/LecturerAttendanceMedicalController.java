package com.java_lms_group_20.Controller.Lecturer;

import com.java_lms_group_20.Model.Attendance;
import com.java_lms_group_20.Model.Medical;
import com.java_lms_group_20.Service.AttendanceService;
import com.java_lms_group_20.Service.MedicalService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class LecturerAttendanceMedicalController {
    @FXML private TextField txtSearchStudentId;
    @FXML private Label lblStatus;

    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance, String> colAttStudentId;
    @FXML private TableColumn<Attendance, String> colAttCourseCode;
    @FXML private TableColumn<Attendance, String> colAttSessionType;
    @FXML private TableColumn<Attendance, java.sql.Date> colAttDate;
    @FXML private TableColumn<Attendance, String> colAttStatus;

    @FXML private TableView<Medical> medicalTable;
    @FXML private TableColumn<Medical, String> colMedStudentId;
    @FXML private TableColumn<Medical, String> colMedDescription;
    @FXML private TableColumn<Medical, java.sql.Date> colMedFrom;
    @FXML private TableColumn<Medical, java.sql.Date> colMedTo;
    @FXML private TableColumn<Medical, String> colMedStatus;

    private final AttendanceService attendanceService = new AttendanceService();
    private final MedicalService medicalService = new MedicalService();

    @FXML
    public void initialize() {
        colAttStudentId.setCellValueFactory(new PropertyValueFactory<>("undergraduateId"));
        colAttCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colAttSessionType.setCellValueFactory(new PropertyValueFactory<>("sessionType"));
        colAttDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colAttStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colMedStudentId.setCellValueFactory(new PropertyValueFactory<>("undergraduateId"));
        colMedDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colMedFrom.setCellValueFactory(new PropertyValueFactory<>("validFrom"));
        colMedTo.setCellValueFactory(new PropertyValueFactory<>("validTo"));
        colMedStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        handleSearch();
    }

    @FXML
    private void handleSearch() {
        try {
            String studentId = txtSearchStudentId.getText();
            var attendanceData = attendanceService.getRawAttendanceByStudentId(studentId);
            var medicalData = medicalService.getMedicalsByStudentId(studentId);

            attendanceTable.setItems(FXCollections.observableArrayList(attendanceData));
            medicalTable.setItems(FXCollections.observableArrayList(medicalData));

            lblStatus.setText("Loaded " + attendanceData.size() + " attendance and " + medicalData.size() + " medical record(s).");
            lblStatus.setStyle("-fx-text-fill: #10b981;");
        } catch (SQLException e) {
            lblStatus.setText("Failed to load attendance/medical records.");
            lblStatus.setStyle("-fx-text-fill: #ef4444;");
            e.printStackTrace();
        }
    }
}
