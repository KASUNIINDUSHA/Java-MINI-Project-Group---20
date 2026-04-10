package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Model.Marks;
import com.java_lms_group_20.Service.MarksService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class MarksController {
    @FXML private TableView<Marks> marksTable;
    @FXML private TableColumn<Marks, String> colStuID, colCourse, colGrade;
    @FXML private TableColumn<Marks, Double> colCA, colMid, colEnd, colFinal;
    @FXML private TextField txtSearchID, txtCA, txtMid, txtEnd;
    @FXML private Label lblStatus;

    private final MarksService service = new MarksService();

    @FXML
    public void initialize() {
        colStuID.setCellValueFactory(new PropertyValueFactory<>("undergraduateId"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colCA.setCellValueFactory(new PropertyValueFactory<>("caMarks"));
        colMid.setCellValueFactory(new PropertyValueFactory<>("midExam"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endExam"));
        colFinal.setCellValueFactory(new PropertyValueFactory<>("finalMarks"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        handleSearch();
    }

    @FXML
    private void handleSearch() {
        try {
            var results = service.getMarksByStudentId(txtSearchID.getText());
            marksTable.setItems(FXCollections.observableArrayList(results));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @FXML
    private void handleUpdate() {
        Marks selected = marksTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setCaMarks(Double.parseDouble(txtCA.getText()));
                selected.setMidExam(Double.parseDouble(txtMid.getText()));
                selected.setEndExam(Double.parseDouble(txtEnd.getText()));
                service.calculateAndSave(selected);
                lblStatus.setText("Marks calculated and updated!");
                handleSearch();
            } catch (Exception e) {
                lblStatus.setText("Invalid input!");
            }
        }
    }
}