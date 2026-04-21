package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Notice;
import com.java_lms_group_20.Repository.NoticeRepository;

import java.sql.SQLException;
import java.util.List;

public class NoticeService {
    private final NoticeRepository repository = new NoticeRepository();

    public List<Notice> getAllNotices() throws SQLException {
        return repository.findAll();
    }

    public boolean addNotice(Notice notice) throws SQLException {
        return repository.save(notice);
    }

    public boolean updateNotice(Notice notice) throws SQLException {
        return repository.update(notice);
    }

    public boolean deleteNotice(int noticeId) throws SQLException {
        return repository.delete(noticeId);
    }
}
