package com.java_lms_group_20.Controller;

import com.java_lms_group_20.Model.*;
import com.java_lms_group_20.Service.AdminService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class AdminDashboardHomeController {

    @FXML private Label lblTotalStudents, lblTotalCourses, lblTotalFaculty;

    // Technical Officer Table
    @FXML private TableView<TechnicalOfficer> toTable;
    @FXML private TableColumn<TechnicalOfficer, String> colTOID, colTOName, colTODept, colTOSkills;

    // Student Table
    @FXML private TableView<Undergraduate> studentTable;
    @FXML private TableColumn<Undergraduate, String> colStuID, colStuName, colStuDegree;

    // Lecturer Table
    @FXML private TableView<Lecturer> lecturerTable;
    @FXML private TableColumn<Lecturer, String> colLecID, colLecName, colLecDept;

    // Course Table
    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, String> colCourseCode, colCourseName;
    @FXML private TableColumn<Course, Integer> colCourseCredits;

    private final AdminService adminService = new AdminService();

    @FXML
    public void initialize() {
        setupTables();
        loadAllData();
    }

    private void setupTables() {
        // TO Table Setup
        colTOID.setCellValueFactory(new PropertyValueFactory<>("techOfficerID"));
        colTODept.setCellValueFactory(new PropertyValueFactory<>("department"));
        colTOSkills.setCellValueFactory(new PropertyValueFactory<>("technicalSkills"));
        colTOName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));

        // Student Table Setup
        colStuID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colStuDegree.setCellValueFactory(new PropertyValueFactory<>("degreeProgram"));
        colStuName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));

        // Lecturer Table Setup
        colLecID.setCellValueFactory(new PropertyValueFactory<>("lecturerID"));
        colLecDept.setCellValueFactory(new PropertyValueFactory<>("department"));
        colLecName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));

        // Course Table Setup
        colCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colCourseCredits.setCellValueFactory(new PropertyValueFactory<>("credits"));
    }

    private void loadAllData() {
        // Load TOs
        List<TechnicalOfficer> tos = adminService.getTOList();
        toTable.setItems(FXCollections.observableArrayList(tos));

        // Load Students
        List<Undergraduate> students = adminService.getStudentList();
        studentTable.setItems(FXCollections.observableArrayList(students));

        // Load Lecturers
        List<Lecturer> lecturers = adminService.getLecturerList();
        lecturerTable.setItems(FXCollections.observableArrayList(lecturers));

        // Load Courses
        List<Course> courses = adminService.getCourseList();
        courseTable.setItems(FXCollections.observableArrayList(courses));

        // Update Stat Labels
        lblTotalStudents.setText(String.valueOf(students.size()));
        lblTotalCourses.setText(String.valueOf(courses.size()));
        lblTotalFaculty.setText(String.valueOf(lecturers.size() + tos.size()));

        System.out.println("DEBUG: Loaded " + tos.size() + " Technical Officers into Home Dashboard.");
    }
}