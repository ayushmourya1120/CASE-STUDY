package com.cms.repository;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    public Course findCourseByCourseId(String courseId) throws CourseInvalidException;
}
