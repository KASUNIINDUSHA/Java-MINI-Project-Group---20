package com.java_lms_group_20.Service;

import com.java_lms_group_20.Model.Course;
import com.java_lms_group_20.Repository.CourseRepository;

public class CourseService {
    private final CourseRepository repository = new CourseRepository();

    public void registerCourse(Course course) throws Exception {
        validateCourse(course);
        if (!repository.save(course)) throw new Exception("Could not save course.");
    }

    public void updateCourse(Course course) throws Exception {
        validateCourse(course);
        if (!repository.update(course)) throw new Exception("Course code not found.");
    }

    public void deleteCourse(String courseCode) throws Exception {
        if (courseCode == null || courseCode.isEmpty()) throw new Exception("Enter Course Code to delete.");
        if (!repository.delete(courseCode)) throw new Exception("Course not found.");
    }

    private void validateCourse(Course course) throws Exception {
        if (course.getCourseCode() == null || course.getCourseCode().isEmpty()) throw new Exception("Course code required.");
        if (course.getCourseName() == null || course.getCourseName().isEmpty()) throw new Exception("Course name required.");
        if (course.getCredits() <= 0) throw new Exception("Credits must be greater than 0.");
    }
}