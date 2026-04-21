package com.java_lms_group_20.Controller.TO;

import com.java_lms_group_20.Model.Timetable;
import com.java_lms_group_20.Service.TimetableService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class TOTimetableController {
    @FXML private TableView<Timetable> timetableTable;
    @FXML private TableColumn<Timetable, String> colDepartment, colDay, colCourse, colVenue;
    @FXML private TableColumn<Timetable, Integer> colLevel;
    @FXML private TableColumn<Timetable, java.sql.Time> colStart, colEnd;
    @FXML private Label lblStatus;

    private final TimetableService service = new TimetableService();

    @FXML
    public void initialize() {
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colVenue.setCellValueFactory(new PropertyValueFactory<>("venue"));
    }

    public void loadDepartment(String department) {
        try {
            var list = service.getByDepartment(department);
            timetableTable.setItems(FXCollections.observableArrayList(list));
            lblStatus.setText("Loaded " + list.size() + " timetable row(s) for " + department + ".");
        } catch (SQLException e) {
            lblStatus.setText("Failed to load timetable.");
            e.printStackTrace();
        }
    }
}
