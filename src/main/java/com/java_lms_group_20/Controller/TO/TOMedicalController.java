package com.java_lms_group_20.Controller.TO;

import com.java_lms_group_20.Model.Medical;
import com.java_lms_group_20.Service.MedicalService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;

public class TOMedicalController {
    @FXML private TextField txtSearchID;
    @FXML private TextField txtStudentId;
    @FXML private TextField txtDescription;
    @FXML private TextField txtFrom;
    @FXML private TextField txtTo;
    @FXML private TextField txtStatus;
    @FXML private Label lblStatus;

    @FXML private TableView<Medical> medicalTable;
    @FXML private TableColumn<Medical, String> colStuID, colDesc, colStatus;
    @FXML private TableColumn<Medical, Date> colFrom, colTo;

    private final MedicalService service = new MedicalService();

    @FXML
    public void initialize() {
        colStuID.setCellValueFactory(new PropertyValueFactory<>("undergraduateId"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colFrom.setCellValueFactory(new PropertyValueFactory<>("validFrom"));
        colTo.setCellValueFactory(new PropertyValueFactory<>("validTo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        handleSearch();
    }

    @FXML
    private void handleSearch() {
        try {
            var results = service.getMedicalsByStudentId(txtSearchID.getText());
            medicalTable.setItems(FXCollections.observableArrayList(results));
            lblStatus.setText("Loaded " + results.size() + " medical row(s).");
        } catch (SQLException e) {
            lblStatus.setText("Failed to load medicals.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        try {
            Medical medical = new Medical();
            medical.setUndergraduateId(txtStudentId.getText().trim());
            medical.setDescription(txtDescription.getText().trim());
            medical.setValidFrom(Date.valueOf(txtFrom.getText().trim()));
            medical.setValidTo(Date.valueOf(txtTo.getText().trim()));
            medical.setStatus(txtStatus.getText().trim());
            service.addMedical(medical);
            lblStatus.setText("Medical record added.");
            handleSearch();
        } catch (Exception e) {
            lblStatus.setText("Invalid input or add failed.");
        }
    }

    @FXML
    private void handleUpdateStatus() {
        Medical selected = medicalTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("Select a row to update status.");
            return;
        }
        try {
            service.processMedicalStatus(selected.getUndergraduateId(), selected.getValidFrom(), txtStatus.getText().trim());
            lblStatus.setText("Medical status updated.");
            handleSearch();
        } catch (SQLException e) {
            lblStatus.setText("Update failed.");
        }
    }
}
