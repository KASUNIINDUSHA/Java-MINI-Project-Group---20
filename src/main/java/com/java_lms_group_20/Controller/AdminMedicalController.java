package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Model.Medical;
import com.java_lms_group_20.Service.MedicalService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class AdminMedicalController {
    @FXML private TableView<Medical> medicalTable;
    @FXML private TableColumn<Medical, String> colStuID, colStatus, colDesc;
    @FXML private TableColumn<Medical, java.sql.Date> colFrom, colTo;
    @FXML private TextField txtSearchID;
    @FXML private Label lblStatus;

    // Use the Service layer
    private final MedicalService service = new MedicalService();

    @FXML
    public void initialize() {
        // Linking Table Columns to Medical Model Properties
        colStuID.setCellValueFactory(new PropertyValueFactory<>("undergraduateId"));
        colFrom.setCellValueFactory(new PropertyValueFactory<>("validFrom"));
        colTo.setCellValueFactory(new PropertyValueFactory<>("validTo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Optional: Load all records on startup
        handleSearch();
    }

    @FXML
    private void handleSearch() {
        try {
            String query = txtSearchID.getText().trim();
            var results = service.getMedicalsByStudentId(query);

            medicalTable.setItems(FXCollections.observableArrayList(results));

            if (results.isEmpty()) {
                lblStatus.setText("No records found.");
                lblStatus.setStyle("-fx-text-fill: #e11d48;"); // Red-ish
            } else {
                lblStatus.setText("Found " + results.size() + " records.");
                lblStatus.setStyle("-fx-text-fill: #10b981;"); // Green
            }
        } catch (SQLException e) {
            lblStatus.setText("Search Error!");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleApprove() { updateStatus("Approved", "#10b981"); }

    @FXML
    private void handleDeny() { updateStatus("Rejected", "#ef4444"); }

    private void updateStatus(String status, String color) {
        Medical selected = medicalTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("Please select a row first!");
            lblStatus.setStyle("-fx-text-fill: #f59e0b;"); // Amber
            return;
        }
        try {
            boolean success = service.processMedicalStatus(
                    selected.getUndergraduateId(),
                    selected.getValidFrom(),
                    status
            );

            if (success) {
                lblStatus.setText("Successfully marked as " + status);
                lblStatus.setStyle("-fx-text-fill: " + color);
                handleSearch(); // Refresh the table
            }
        } catch (SQLException e) {
            lblStatus.setText("Update Failed!");
            e.printStackTrace();
        }
    }
}