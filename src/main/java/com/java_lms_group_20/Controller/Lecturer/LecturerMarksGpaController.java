package com.java_lms_group_20.Controller.Lecturer;

import com.java_lms_group_20.Model.Marks;
import com.java_lms_group_20.Model.LecturerMarksGpa;
import com.java_lms_group_20.Service.LecturerUndergraduateService;
import com.java_lms_group_20.Service.MarksService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class LecturerMarksGpaController {
    @FXML private TextField txtSearchStudentId;
    @FXML private TextField txtStudentId;
    @FXML private TextField txtCourseCode;
    @FXML private TextField txtQuiz1;
    @FXML private TextField txtQuiz2;
    @FXML private TextField txtQuiz3;
    @FXML private TextField txtAssignment;
    @FXML private TextField txtProject;
    @FXML private TextField txtMid;
    @FXML private TextField txtEnd;
    @FXML private Label lblStatus;
    @FXML private TableView<LecturerMarksGpa> marksTable;
    @FXML private TableColumn<LecturerMarksGpa, String> colStudentId;
    @FXML private TableColumn<LecturerMarksGpa, String> colCourseCode;
    @FXML private TableColumn<LecturerMarksGpa, Double> colQuiz1;
    @FXML private TableColumn<LecturerMarksGpa, Double> colQuiz2;
    @FXML private TableColumn<LecturerMarksGpa, Double> colQuiz3;
    @FXML private TableColumn<LecturerMarksGpa, Double> colAssignment;
    @FXML private TableColumn<LecturerMarksGpa, Double> colProject;
    @FXML private TableColumn<LecturerMarksGpa, Double> colCa;
    @FXML private TableColumn<LecturerMarksGpa, Double> colMid;
    @FXML private TableColumn<LecturerMarksGpa, Double> colEnd;
    @FXML private TableColumn<LecturerMarksGpa, Double> colFinal;
    @FXML private TableColumn<LecturerMarksGpa, String> colGrade;
    @FXML private TableColumn<LecturerMarksGpa, Double> colGpa;

    private final LecturerUndergraduateService service = new LecturerUndergraduateService();
    private final MarksService marksService = new MarksService();

    @FXML
    public void initialize() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("undergraduateId"));
        colCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colQuiz1.setCellValueFactory(new PropertyValueFactory<>("quiz1"));
        colQuiz2.setCellValueFactory(new PropertyValueFactory<>("quiz2"));
        colQuiz3.setCellValueFactory(new PropertyValueFactory<>("quiz3"));
        colAssignment.setCellValueFactory(new PropertyValueFactory<>("assignment"));
        colProject.setCellValueFactory(new PropertyValueFactory<>("project"));
        colCa.setCellValueFactory(new PropertyValueFactory<>("caMarks"));
        colMid.setCellValueFactory(new PropertyValueFactory<>("midExam"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endExam"));
        colFinal.setCellValueFactory(new PropertyValueFactory<>("finalMarks"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colGpa.setCellValueFactory(new PropertyValueFactory<>("gpa"));

        marksTable.setOnMouseClicked(event -> handleTableSelection());
        handleSearch();
    }

    @FXML
    private void handleSearch() {
        try {
            var data = service.getMarksAndGpaRecords(txtSearchStudentId.getText());
            marksTable.setItems(FXCollections.observableArrayList(data));
            lblStatus.setText("Found " + data.size() + " marks/GPA record(s).");
            lblStatus.setStyle("-fx-text-fill: #10b981;");
        } catch (SQLException e) {
            lblStatus.setText("Failed to load marks and GPA records.");
            lblStatus.setStyle("-fx-text-fill: #ef4444;");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveOrUpdate() {
        try {
            Marks marks = new Marks();
            marks.setUndergraduateId(txtStudentId.getText().trim());
            marks.setCourseCode(txtCourseCode.getText().trim());
            marks.setQuiz1(parseMark(txtQuiz1));
            marks.setQuiz2(parseMark(txtQuiz2));
            marks.setQuiz3(parseMark(txtQuiz3));
            marks.setAssignment(parseMark(txtAssignment));
            marks.setProject(parseMark(txtProject));
            marks.setMidExam(parseMark(txtMid));
            marks.setEndExam(parseMark(txtEnd));

            if (marks.getUndergraduateId().isEmpty() || marks.getCourseCode().isEmpty()) {
                lblStatus.setText("Student ID and Course Code are required.");
                lblStatus.setStyle("-fx-text-fill: #ef4444;");
                return;
            }

            marksService.calculateAndSave(marks);
            lblStatus.setText("Marks saved successfully.");
            lblStatus.setStyle("-fx-text-fill: #10b981;");
            handleSearch();
            clearInputs();
        } catch (Exception e) {
            lblStatus.setText("Invalid input. Enter valid numeric marks.");
            lblStatus.setStyle("-fx-text-fill: #ef4444;");
        }
    }

    private double parseMark(TextField field) {
        String value = field.getText() == null ? "" : field.getText().trim();
        return value.isEmpty() ? 0.0 : Double.parseDouble(value);
    }

    private void handleTableSelection() {
        LecturerMarksGpa selected = marksTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        txtStudentId.setText(selected.getUndergraduateId());
        txtCourseCode.setText(selected.getCourseCode());
        txtQuiz1.setText(String.valueOf(selected.getQuiz1()));
        txtQuiz2.setText(String.valueOf(selected.getQuiz2()));
        txtQuiz3.setText(String.valueOf(selected.getQuiz3()));
        txtAssignment.setText(String.valueOf(selected.getAssignment()));
        txtProject.setText(String.valueOf(selected.getProject()));
        txtMid.setText(String.valueOf(selected.getMidExam()));
        txtEnd.setText(String.valueOf(selected.getEndExam()));
    }

    private void clearInputs() {
        txtStudentId.clear();
        txtCourseCode.clear();
        txtQuiz1.clear();
        txtQuiz2.clear();
        txtQuiz3.clear();
        txtAssignment.clear();
        txtProject.clear();
        txtMid.clear();
        txtEnd.clear();
    }
}
