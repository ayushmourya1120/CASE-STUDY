package com.cms.service;

import java.util.List;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CourseServiceImpl implements ICourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private final SequenceGeneratorService sequenceGenerator;

	public CourseServiceImpl(SequenceGeneratorService sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public Course addCourse(Course cObj) throws CourseInvalidException {
		try {
			log.info("Entering addCourse method");

			if(cObj == null) {
				throw new CourseInvalidException("Course Object is NULL");
			}

			// Generate the next unique ID
			String courseId = String.valueOf(sequenceGenerator.generateSequence("course"));
			Course course = new Course();

			// Create a new Course object
			course.setCourseId(courseId);
			course.setCourseName(cObj.getCourseName());
			course.setFees(cObj.getFees());
			course.setDuration(cObj.getDuration());
			course.setCourseType(cObj.getCourseType());
			course.setRating(cObj.getRating());

			// Check if the generated ID already exists
			Course existingCourse = courseRepository.findCourseByCourseId(courseId);
			if (existingCourse != null) {
				throw new CourseInvalidException("CourseID already exists");
			}

			// Save the course to the database or perform other operations
			Course savedCourse = courseRepository.save(course);

			log.info("The method addCourse has completed successfully");

			return savedCourse;
		} catch (Exception e) {
			log.error("CourseId already exists");
			throw e;
		}
	}

	@Override
	public Course updateCourse(String courseId, Integer fees) throws CourseInvalidException {
		try {
			log.info("Entering updateCourse method");

			if(courseId == null || courseId.isEmpty()) {
				throw new CourseInvalidException("Invalid Course Id");
			}

			// Retrieve the course form the repository
			Course course = courseRepository.findById(courseId)
					.orElseThrow(() -> new CourseInvalidException("CourseId does not exists"));

			// Update the course's fees
			course.setFees(fees);

			// Save the updated course
			Course updatedCourse = courseRepository.save(course);

			log.info("The method updateCourse has completed successfully");

			return updatedCourse;
		} catch (Exception e) {
			log.error("CourseId does not exists");
			throw e;
		}
	}

	@Override
	public Course viewByCourseId(String courseId) throws CourseInvalidException {
		try {

			log.info("Entering viewByCourseId method");

			if(courseId == null || courseId.isEmpty()) {
				throw new CourseInvalidException("Invalid Course Id");
			}

			Course course = courseRepository.findCourseByCourseId(courseId);
			if (course == null) {
				throw new CourseInvalidException("Invalid Course Id");
			}

			log.info("The method viewByCourseId has completed successfully");

			return course;
		} catch (Exception e) {
			log.error("Invalid Course Id");
			throw e;
		}
	}

	@Override
	public float findFeedbackRatingForCourseId(String courseId) throws CourseInvalidException {
		try {
			log.info("Entering findFeedbackRatingForCourseId method");

			if(courseId == null || courseId.isEmpty()) {
				throw new CourseInvalidException("Invalid Course Id");
			}

			Course course = courseRepository.findById(courseId)
					.orElseThrow(() -> new CourseInvalidException("CourseId does not exists"));

			log.info("The method findFeedbackRatingForCourseId has completed successfully");

			return course.getRating();
		} catch (Exception e) {
			log.error("CourseId does not exists");
			throw e;
		}
	}

	@Override
	public Course calculateAverageFeedbackAndUpdate(String courseId, float rating) throws CourseInvalidException {
		try {
			log.info("Entering calculateAverageFeedbackAndUpdate method");

			if(courseId == null || courseId.isEmpty()) {
				throw new CourseInvalidException("Invalid Course Id");
			}

			Course course = courseRepository.findById(courseId)
					.orElseThrow(() -> new CourseInvalidException("CourseId does not exists"));

			float oldRating = course.getRating();
			float averageRating = (oldRating + rating) / 2;
			course.setRating(averageRating);

			// Save the updated course
			Course updatedCourse = courseRepository.save(course);

			log.info("The method calculateAverageFeedbackAndUpdate has completed successfully");

			return updatedCourse;
		} catch (Exception e) {
			log.error("CourseId does not exists");
			throw e;
		}
	}

	@Override
	public Course deactivateCourse(String courseId) throws CourseInvalidException {
		try {
			log.info("Entering deactivateCourse method");

			if(courseId == null || courseId.isEmpty()) {
				throw new CourseInvalidException("Invalid Course Id");
			}

			Course course = courseRepository.findById(courseId)
					.orElseThrow(() -> new CourseInvalidException("CourseId does not exists"));

			// Delete the course
			courseRepository.delete(course);

			log.info("The method deactivateCourse has completed successfully");

			return course;
		} catch (Exception e) {
			log.error("CourseId does not exists");
			throw e;
		}
	}

	@Override
	public List<Course> viewAll() {
		log.info("Entering viewAll method");

		List<Course> allCourses = courseRepository.findAll();

		log.info("The method viewAll has completed successfully");

		return allCourses;
	}
}
