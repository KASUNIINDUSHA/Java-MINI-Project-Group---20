package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.*;
import com.java_lms_group_20.Repository.AdminRepository;
import java.util.List;

public class AdminService {
    private final AdminRepository adminRepo = new AdminRepository();


    public List<Lecturer> getLecturerList() { return adminRepo.getAllLecturers(); }
    public List<TechnicalOfficer> getTOList() {
        return adminRepo.getAllTechnicalOfficers();
    }
    public List<Undergraduate> getStudentList() { return adminRepo.getAllUndergraduates(); }
    public List<Course> getCourseList() { return adminRepo.getAllCourses(); }
}