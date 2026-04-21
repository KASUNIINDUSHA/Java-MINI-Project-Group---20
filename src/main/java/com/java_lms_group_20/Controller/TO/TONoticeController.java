package com.java_lms_group_20.Controller.TO;

import com.java_lms_group_20.Model.Notice;
import com.java_lms_group_20.Service.NoticeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class TONoticeController {
    @FXML private TableView<Notice> noticeTable;
    @FXML private TableColumn<Notice, String> colTitle, colMessage, colPostedBy;
    @FXML private TableColumn<Notice, java.sql.Timestamp> colPostedAt;
    @FXML private Label lblStatus;

    private final NoticeService service = new NoticeService();

    @FXML
    public void initialize() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        colPostedBy.setCellValueFactory(new PropertyValueFactory<>("postedBy"));
        colPostedAt.setCellValueFactory(new PropertyValueFactory<>("postedAt"));
        try {
            var list = service.getAllNotices();
            noticeTable.setItems(FXCollections.observableArrayList(list));
            lblStatus.setText("Loaded " + list.size() + " notice(s).");
        } catch (SQLException e) {
            lblStatus.setText("Failed to load notices.");
        }
    }
}
