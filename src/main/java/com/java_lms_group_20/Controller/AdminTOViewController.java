package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Model.TechnicalOfficer;
import com.java_lms_group_20.Service.AdminService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminTOViewController {
    @FXML private TableView<TechnicalOfficer> toTable;
    @FXML private TableColumn<TechnicalOfficer, String> colID;
    @FXML private TableColumn<TechnicalOfficer, String> colName;
    @FXML private TableColumn<TechnicalOfficer, String> colDept;
    @FXML private TableColumn<TechnicalOfficer, String> colSkills;

    private final AdminService adminService = new AdminService();

    @FXML
    public void initialize() {
        // Map columns to Model properties
        colID.setCellValueFactory(new PropertyValueFactory<>("techOfficerID"));
        colDept.setCellValueFactory(new PropertyValueFactory<>("department"));
        colSkills.setCellValueFactory(new PropertyValueFactory<>("technicalSkills"));

        System.out.println("DEBUG: Rows fetched from DB = " + adminService.getTOList().size());
        // Custom full name display
        colName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName())
        );

        toTable.setItems(FXCollections.observableArrayList(adminService.getTOList()));
    }
}