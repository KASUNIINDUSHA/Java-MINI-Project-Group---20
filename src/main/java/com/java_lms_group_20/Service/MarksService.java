package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Marks;
import com.java_lms_group_20.Repository.MarksRepository;
import java.sql.SQLException;
import java.util.List;

public class MarksService {
    private final MarksRepository repository = new MarksRepository();

    public List<Marks> getMarksByStudentId(String studentId) throws SQLException {
        return repository.findByStudentId(studentId == null ? "" : studentId.trim());
    }

    public void calculateAndSave(Marks m) throws SQLException {
        double total = m.getCaMarks() + m.getMidExam() + m.getEndExam();
        m.setFinalMarks(total);

        String grade;
        if (total >= 85) grade = "A+";
        else if (total >= 75) grade = "A";
        else if (total >= 65) grade = "B";
        else if (total >= 55) grade = "C";
        else if (total >= 45) grade = "D";
        else grade = "F";

        m.setGrade(grade);
        repository.updateMarks(m);
    }
}