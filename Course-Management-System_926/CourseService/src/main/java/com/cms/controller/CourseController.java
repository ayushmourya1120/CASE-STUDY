package com.cms.controller;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    ICourseService courseService;

    @PostMapping("/addCourse")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) throws CourseInvalidException {

        try {
            return new ResponseEntity(courseService.addCourse(course), HttpStatus.OK);
        } catch (CourseInvalidException e) {
            return new ResponseEntity("CourseId already exists", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }

    }

    @PutMapping("/update/{courseId}/{courseFees}")
    public ResponseEntity<Course> updateCourse(@PathVariable String courseId, @PathVariable Integer courseFees) {

        try {
            return new ResponseEntity(courseService.updateCourse(courseId, courseFees), HttpStatus.OK);
        } catch (CourseInvalidException e) {
            return new ResponseEntity("CourseId does not exists", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/viewByCourseId/{courseId}")
    public ResponseEntity<Course> viewByCourseId(@PathVariable String courseId) {
        try {
            return new ResponseEntity(courseService.viewByCourseId(courseId), HttpStatus.OK);
        } catch (CourseInvalidException e) {
            return new ResponseEntity("Invalid Course Id", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/viewFeedback/{courseId}")
    public ResponseEntity<Course> findFeedbackRatingForCourseId(@PathVariable String courseId) {
        try {
            return new ResponseEntity(courseService.findFeedbackRatingForCourseId(courseId), HttpStatus.OK);
        } catch (CourseInvalidException e) {
            return new ResponseEntity("CourseId does not exists", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/deactivate/{courseId}")
    public ResponseEntity<Course> deactivateCourse(@PathVariable String courseId) {
        try {
            return new ResponseEntity(courseService.deactivateCourse(courseId), HttpStatus.OK);
        } catch (CourseInvalidException e) {
            return new ResponseEntity("CourseId does not exists", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Course>> viewAll() {
        return new ResponseEntity(courseService.viewAll(), HttpStatus.OK);
    }

//    Below method is not done in front end
    @PutMapping("/calculateAverageFeedback/{courseId}/{rating}")
    public ResponseEntity<Course> calculateAverageFeedbackAndUpdate(@PathVariable String courseId, @PathVariable float rating) {
        try {
            return new ResponseEntity(courseService.calculateAverageFeedbackAndUpdate(courseId, rating), HttpStatus.OK);
        } catch (CourseInvalidException e) {
            return new ResponseEntity("CourseId does not exists", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }
    }

}
