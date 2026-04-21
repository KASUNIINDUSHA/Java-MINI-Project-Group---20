package com.java_lms_group_20.Controller.Admin;

import com.java_lms_group_20.Model.Timetable;
import com.java_lms_group_20.Repository.TimetableRepository;
import com.java_lms_group_20.Service.TimetableService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.sql.Time;

public class AdminTimetableController {
    @FXML private TableView<Timetable> timetableTable;
    @FXML private TableColumn<Timetable, Integer> colId;
    @FXML private TableColumn<Timetable, String> colDepartment;
    @FXML private TableColumn<Timetable, Integer> colLevel;
    @FXML private TableColumn<Timetable, String> colDay;
    @FXML private TableColumn<Timetable, Time> colStart;
    @FXML private TableColumn<Timetable, Time> colEnd;
    @FXML private TableColumn<Timetable, String> colCourse;
    @FXML private TableColumn<Timetable, String> colVenue;

    @FXML private TextField txtDepartment;
    @FXML private TextField txtLevel;
    @FXML private TextField txtDay;
    @FXML private TextField txtStart;
    @FXML private TextField txtEnd;
    @FXML private TextField txtCourse;
    @FXML private TextField txtVenue;
    @FXML private Label lblStatus;

    private final TimetableService service = new TimetableService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("timetableID"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colVenue.setCellValueFactory(new PropertyValueFactory<>("venue"));
        timetableTable.setOnMouseClicked(e -> handleSelect());
        loadData();
    }

    @FXML
    private void handleAdd() {
        try {
            service.add(fromInputs(new Timetable()));
            setStatus("Timetable row added.", true);
            clearInputs();
            loadData();
        } catch (Exception e) {
            setStatus("Invalid input or add failed.", false);
        }
    }

    @FXML
    private void handleUpdate() {
        Timetable selected = timetableTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setStatus("Select a row to update.", false);
            return;
        }
        try {
            service.update(fromInputs(selected));
            setStatus("Timetable row updated.", true);
            loadData();
        } catch (Exception e) {
            setStatus("Invalid input or update failed.", false);
        }
    }

    @FXML
    private void handleDelete() {
        Timetable selected = timetableTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setStatus("Select a row to delete.", false);
            return;
        }
        try {
            service.delete(selected.getTimetableID());
            setStatus("Timetable row deleted.", true);
            clearInputs();
            loadData();
        } catch (SQLException e) {
            setStatus("Delete failed.", false);
            e.printStackTrace();
        }
    }

    private Timetable fromInputs(Timetable t) {
        t.setDepartment(txtDepartment.getText().trim());
        t.setLevel(Integer.parseInt(txtLevel.getText().trim()));
        t.setDayOfWeek(txtDay.getText().trim());
        t.setStartTime(TimetableRepository.parseTime(txtStart.getText().trim()));
        t.setEndTime(TimetableRepository.parseTime(txtEnd.getText().trim()));
        t.setCourseCode(txtCourse.getText().trim());
        t.setVenue(txtVenue.getText().trim());
        return t;
    }

    private void handleSelect() {
        Timetable t = timetableTable.getSelectionModel().getSelectedItem();
        if (t == null) return;
        txtDepartment.setText(t.getDepartment());
        txtLevel.setText(String.valueOf(t.getLevel()));
        txtDay.setText(t.getDayOfWeek());
        txtStart.setText(t.getStartTime().toString());
        txtEnd.setText(t.getEndTime().toString());
        txtCourse.setText(t.getCourseCode());
        txtVenue.setText(t.getVenue());
    }

    private void loadData() {
        try {
            timetableTable.setItems(FXCollections.observableArrayList(service.getAll()));
        } catch (SQLException e) {
            setStatus("Failed to load timetable.", false);
            e.printStackTrace();
        }
    }

    private void clearInputs() {
        txtDepartment.clear();
        txtLevel.clear();
        txtDay.clear();
        txtStart.clear();
        txtEnd.clear();
        txtCourse.clear();
        txtVenue.clear();
    }

    private void setStatus(String msg, boolean ok) {
        lblStatus.setText(msg);
        lblStatus.setStyle(ok ? "-fx-text-fill: #10b981;" : "-fx-text-fill: #ef4444;");
    }
}
