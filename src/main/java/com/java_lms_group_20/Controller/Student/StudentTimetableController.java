package com.java_lms_group_20.Controller.Student;

import com.java_lms_group_20.Model.Timetable;
import com.java_lms_group_20.Service.TimetableService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class StudentTimetableController {
    @FXML private TableView<Timetable> timetableTable;
    @FXML private TableColumn<Timetable, String> colDay;
    @FXML private TableColumn<Timetable, java.sql.Time> colStart;
    @FXML private TableColumn<Timetable, java.sql.Time> colEnd;
    @FXML private TableColumn<Timetable, String> colCourse;
    @FXML private TableColumn<Timetable, String> colVenue;
    @FXML private Label lblStatus;

    private final TimetableService service = new TimetableService();

    public void loadByStudentId(String studentId) {
        try {
            var list = service.getByStudentId(studentId);
            timetableTable.setItems(FXCollections.observableArrayList(list));
            lblStatus.setText("Loaded " + list.size() + " timetable row(s).");
        } catch (SQLException e) {
            lblStatus.setText("Failed to load timetable.");
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        colDay.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colVenue.setCellValueFactory(new PropertyValueFactory<>("venue"));
    }
}
