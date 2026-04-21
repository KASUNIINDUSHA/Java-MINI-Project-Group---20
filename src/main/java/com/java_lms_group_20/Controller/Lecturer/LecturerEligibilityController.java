package com.java_lms_group_20.Controller.Lecturer;

import com.java_lms_group_20.Model.LecturerEligibility;
import com.java_lms_group_20.Service.LecturerUndergraduateService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class LecturerEligibilityController {
    @FXML private TextField txtSearchStudentId;
    @FXML private Label lblStatus;
    @FXML private TableView<LecturerEligibility> eligibilityTable;
    @FXML private TableColumn<LecturerEligibility, String> colStudentId;
    @FXML private TableColumn<LecturerEligibility, String> colCourseCode;
    @FXML private TableColumn<LecturerEligibility, Double> colAttendancePercentage;
    @FXML private TableColumn<LecturerEligibility, String> colEligibility;

    private final LecturerUndergraduateService service = new LecturerUndergraduateService();

    @FXML
    public void initialize() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("undergraduateId"));
        colCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colAttendancePercentage.setCellValueFactory(new PropertyValueFactory<>("attendancePercentage"));
        colEligibility.setCellValueFactory(new PropertyValueFactory<>("eligibilityStatus"));

        handleSearch();
    }

    @FXML
    private void handleSearch() {
        try {
            var data = service.getEligibilityRecords(txtSearchStudentId.getText());
            eligibilityTable.setItems(FXCollections.observableArrayList(data));
            lblStatus.setText("Found " + data.size() + " eligibility record(s).");
            lblStatus.setStyle("-fx-text-fill: #10b981;");
        } catch (SQLException e) {
            lblStatus.setText("Failed to load eligibility records.");
            lblStatus.setStyle("-fx-text-fill: #ef4444;");
            e.printStackTrace();
        }
    }
}
