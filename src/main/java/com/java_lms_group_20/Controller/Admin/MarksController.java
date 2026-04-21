package com.java_lms_group_20.Controller.Admin;

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
    @FXML private TableColumn<Marks, Double> colQuiz1, colQuiz2, colQuiz3, colAssignment, colProject;
    @FXML private TableColumn<Marks, Double> colCA, colMid, colEnd, colFinal;
    @FXML private TextField txtSearchID, txtQuiz1, txtQuiz2, txtQuiz3, txtAssignment, txtProject, txtCA, txtMid, txtEnd;
    @FXML private Label lblStatus;

    private final MarksService service = new MarksService();

    @FXML
    public void initialize() {
        colStuID.setCellValueFactory(new PropertyValueFactory<>("undergraduateId"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colQuiz1.setCellValueFactory(new PropertyValueFactory<>("quiz1"));
        colQuiz2.setCellValueFactory(new PropertyValueFactory<>("quiz2"));
        colQuiz3.setCellValueFactory(new PropertyValueFactory<>("quiz3"));
        colAssignment.setCellValueFactory(new PropertyValueFactory<>("assignment"));
        colProject.setCellValueFactory(new PropertyValueFactory<>("project"));
        colCA.setCellValueFactory(new PropertyValueFactory<>("caMarks"));
        colMid.setCellValueFactory(new PropertyValueFactory<>("midExam"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endExam"));
        colFinal.setCellValueFactory(new PropertyValueFactory<>("finalMarks"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        marksTable.setOnMouseClicked(e -> handleRowSelect());
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
                selected.setQuiz1(parseOrZero(txtQuiz1));
                selected.setQuiz2(parseOrZero(txtQuiz2));
                selected.setQuiz3(parseOrZero(txtQuiz3));
                selected.setAssignment(parseOrZero(txtAssignment));
                selected.setProject(parseOrZero(txtProject));
                selected.setCaMarks(parseOrZero(txtCA));
                selected.setMidExam(parseOrZero(txtMid));
                selected.setEndExam(parseOrZero(txtEnd));
                service.calculateAndSave(selected);
                lblStatus.setText("Marks calculated and updated!");
                handleSearch();
            } catch (Exception e) {
                lblStatus.setText("Invalid input!");
            }
        }
    }

    private double parseOrZero(TextField field) {
        String value = field.getText() == null ? "" : field.getText().trim();
        return value.isEmpty() ? 0.0 : Double.parseDouble(value);
    }

    private void handleRowSelect() {
        Marks selected = marksTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        txtQuiz1.setText(String.valueOf(selected.getQuiz1()));
        txtQuiz2.setText(String.valueOf(selected.getQuiz2()));
        txtQuiz3.setText(String.valueOf(selected.getQuiz3()));
        txtAssignment.setText(String.valueOf(selected.getAssignment()));
        txtProject.setText(String.valueOf(selected.getProject()));
        txtCA.setText(String.valueOf(selected.getCaMarks()));
        txtMid.setText(String.valueOf(selected.getMidExam()));
        txtEnd.setText(String.valueOf(selected.getEndExam()));
    }
}
