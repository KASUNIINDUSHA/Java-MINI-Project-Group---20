package com.java_lms_group_20.Controller.Admin;

import com.java_lms_group_20.Model.Notice;
import com.java_lms_group_20.Service.NoticeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class AdminNoticeController {
    @FXML private TableView<Notice> noticeTable;
    @FXML private TableColumn<Notice, Integer> colNoticeId;
    @FXML private TableColumn<Notice, String> colTitle;
    @FXML private TableColumn<Notice, String> colPostedBy;
    @FXML private TableColumn<Notice, java.sql.Timestamp> colPostedAt;
    @FXML private TextField txtTitle;
    @FXML private TextField txtPostedBy;
    @FXML private TextArea txtMessage;
    @FXML private Label lblStatus;

    private final NoticeService service = new NoticeService();

    @FXML
    public void initialize() {
        colNoticeId.setCellValueFactory(new PropertyValueFactory<>("noticeID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colPostedBy.setCellValueFactory(new PropertyValueFactory<>("postedBy"));
        colPostedAt.setCellValueFactory(new PropertyValueFactory<>("postedAt"));

        noticeTable.setOnMouseClicked(e -> handleSelect());
        loadData();
    }

    @FXML
    private void handleAdd() {
        try {
            Notice notice = new Notice();
            notice.setTitle(txtTitle.getText().trim());
            notice.setMessage(txtMessage.getText().trim());
            notice.setPostedBy(txtPostedBy.getText().trim());
            if (notice.getTitle().isEmpty() || notice.getMessage().isEmpty()) {
                setStatus("Title and message are required.", false);
                return;
            }
            service.addNotice(notice);
            setStatus("Notice added.", true);
            clearInputs();
            loadData();
        } catch (SQLException e) {
            setStatus("Failed to add notice.", false);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate() {
        Notice selected = noticeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setStatus("Select a notice to update.", false);
            return;
        }
        try {
            selected.setTitle(txtTitle.getText().trim());
            selected.setMessage(txtMessage.getText().trim());
            selected.setPostedBy(txtPostedBy.getText().trim());
            service.updateNotice(selected);
            setStatus("Notice updated.", true);
            loadData();
        } catch (SQLException e) {
            setStatus("Failed to update notice.", false);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        Notice selected = noticeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setStatus("Select a notice to delete.", false);
            return;
        }
        try {
            service.deleteNotice(selected.getNoticeID());
            setStatus("Notice deleted.", true);
            clearInputs();
            loadData();
        } catch (SQLException e) {
            setStatus("Failed to delete notice.", false);
            e.printStackTrace();
        }
    }

    private void handleSelect() {
        Notice selected = noticeTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        txtTitle.setText(selected.getTitle());
        txtMessage.setText(selected.getMessage());
        txtPostedBy.setText(selected.getPostedBy());
    }

    private void loadData() {
        try {
            noticeTable.setItems(FXCollections.observableArrayList(service.getAllNotices()));
        } catch (SQLException e) {
            setStatus("Failed to load notices.", false);
            e.printStackTrace();
        }
    }

    private void clearInputs() {
        txtTitle.clear();
        txtMessage.clear();
        txtPostedBy.clear();
    }

    private void setStatus(String msg, boolean ok) {
        lblStatus.setText(msg);
        lblStatus.setStyle(ok ? "-fx-text-fill: #10b981;" : "-fx-text-fill: #ef4444;");
    }
}
