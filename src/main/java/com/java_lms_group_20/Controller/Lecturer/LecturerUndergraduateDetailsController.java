package com.java_lms_group_20.Controller.Lecturer;

import com.java_lms_group_20.Model.Undergraduate;
import com.java_lms_group_20.Service.LecturerUndergraduateService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class LecturerUndergraduateDetailsController {
    @FXML private TextField txtSearch;
    @FXML private Label lblStatus;
    @FXML private TableView<Undergraduate> studentTable;
    @FXML private TableColumn<Undergraduate, String> colStudentId;
    @FXML private TableColumn<Undergraduate, String> colFirstName;
    @FXML private TableColumn<Undergraduate, String> colLastName;
    @FXML private TableColumn<Undergraduate, String> colEmail;
    @FXML private TableColumn<Undergraduate, String> colContact;
    @FXML private TableColumn<Undergraduate, String> colDegree;
    @FXML private TableColumn<Undergraduate, Integer> colLevel;
    @FXML private TableColumn<Undergraduate, Double> colGpa;

    private final LecturerUndergraduateService service = new LecturerUndergraduateService();

    @FXML
    public void initialize() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colDegree.setCellValueFactory(new PropertyValueFactory<>("degreeProgram"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        colGpa.setCellValueFactory(new PropertyValueFactory<>("gpa"));

        handleSearch();
    }

    @FXML
    private void handleSearch() {
        try {
            var data = service.getUndergraduateDetails(txtSearch.getText());
            studentTable.setItems(FXCollections.observableArrayList(data));
            lblStatus.setText("Found " + data.size() + " undergraduate record(s).");
            lblStatus.setStyle("-fx-text-fill: #10b981;");
        } catch (SQLException e) {
            lblStatus.setText("Failed to load undergraduate details.");
            lblStatus.setStyle("-fx-text-fill: #ef4444;");
            e.printStackTrace();
        }
    }
}
