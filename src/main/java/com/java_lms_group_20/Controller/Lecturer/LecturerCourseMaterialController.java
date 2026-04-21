package com.java_lms_group_20.Controller.Lecturer;

import com.java_lms_group_20.Model.CourseMaterial;
import com.java_lms_group_20.Model.User;
import com.java_lms_group_20.Service.CourseMaterialService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class LecturerCourseMaterialController {
    @FXML private TableView<CourseMaterial> materialTable;
    @FXML private TableColumn<CourseMaterial, Integer> colId;
    @FXML private TableColumn<CourseMaterial, String> colCourse, colTitle, colLink, colUploadedBy;
    @FXML private TextField txtSearchCourse;
    @FXML private TextField txtCourseCode;
    @FXML private TextField txtTitle;
    @FXML private TextField txtLink;
    @FXML private TextArea txtDescription;
    @FXML private Label lblStatus;

    private final CourseMaterialService service = new CourseMaterialService();
    private String uploaderName = "Lecturer";

    public void initUser(User user) {
        if (user != null) {
            uploaderName = user.getFirstName() + " " + user.getLastName();
        }
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("materialID"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colLink.setCellValueFactory(new PropertyValueFactory<>("resourceLink"));
        colUploadedBy.setCellValueFactory(new PropertyValueFactory<>("uploadedBy"));
        materialTable.setOnMouseClicked(e -> handleSelect());
        handleSearch();
    }

    @FXML
    private void handleSearch() {
        try {
            var list = service.getByCourseCode(txtSearchCourse.getText());
            materialTable.setItems(FXCollections.observableArrayList(list));
            lblStatus.setText("Loaded " + list.size() + " material(s).");
        } catch (SQLException e) {
            lblStatus.setText("Failed to load materials.");
        }
    }

    @FXML
    private void handleAdd() {
        try {
            CourseMaterial material = new CourseMaterial();
            material.setCourseCode(txtCourseCode.getText().trim());
            material.setTitle(txtTitle.getText().trim());
            material.setDescription(txtDescription.getText().trim());
            material.setResourceLink(txtLink.getText().trim());
            material.setUploadedBy(uploaderName);
            service.add(material);
            lblStatus.setText("Material added.");
            handleSearch();
        } catch (SQLException e) {
            lblStatus.setText("Add failed.");
        }
    }

    @FXML
    private void handleUpdate() {
        CourseMaterial selected = materialTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("Select a row to update.");
            return;
        }
        try {
            selected.setCourseCode(txtCourseCode.getText().trim());
            selected.setTitle(txtTitle.getText().trim());
            selected.setDescription(txtDescription.getText().trim());
            selected.setResourceLink(txtLink.getText().trim());
            service.update(selected);
            lblStatus.setText("Material updated.");
            handleSearch();
        } catch (SQLException e) {
            lblStatus.setText("Update failed.");
        }
    }

    @FXML
    private void handleDelete() {
        CourseMaterial selected = materialTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblStatus.setText("Select a row to delete.");
            return;
        }
        try {
            service.delete(selected.getMaterialID());
            lblStatus.setText("Material deleted.");
            handleSearch();
        } catch (SQLException e) {
            lblStatus.setText("Delete failed.");
        }
    }

    private void handleSelect() {
        CourseMaterial selected = materialTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        txtCourseCode.setText(selected.getCourseCode());
        txtTitle.setText(selected.getTitle());
        txtDescription.setText(selected.getDescription());
        txtLink.setText(selected.getResourceLink());
    }
}
