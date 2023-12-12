package com.cms;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.repository.CourseRepository;
import com.cms.service.CourseServiceImpl;
import com.cms.service.SequenceGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CourseServiceImplTest {

	@Mock
	private CourseRepository courseRepository;

	@Mock
	private SequenceGeneratorService sequenceGeneratorService;

	@InjectMocks
	private CourseServiceImpl courseService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		MockitoAnnotations.initMocks(this);
	}

	// Create a Course object for testing
	private Course getCourse(String sequence) {
		Course course = mock(Course.class);
		course.setCourseName("Mathematics");
		course.setFees(1000);
		course.setDuration(3);
		course.setCourseType("Regular");
		course.setRating(4.5f);
		course.setCourseId(sequence);
		return course;
	}

	@Test
	public void test127AddCourse() throws CourseInvalidException {

		when(sequenceGeneratorService.generateSequence("course")).thenReturn(101L);

		// Mock the behavior of the course repository
		when(courseRepository.findCourseByCourseId(anyString())).thenReturn(null);
		when(courseRepository.save(any(Course.class))).thenReturn(new Course());

		// Create a sample course object
		Course course = new Course();
		course.setCourseName("Sample Course");

		// Call the method to be tested
		Course addedCourse = courseService.addCourse(course);

		// Verify the interactions and assertions
		verify(sequenceGeneratorService, times(1)).generateSequence("course");
		verify(courseRepository, times(1)).findCourseByCourseId(anyString());
		verify(courseRepository, times(1)).save(any(Course.class));

		Assertions.assertNotNull(addedCourse);
		Assertions.assertEquals(course.getCourseId(), addedCourse.getCourseId());
		Assertions.assertEquals(course.getCourseName(), addedCourse.getCourseName());
	}

	@Test
	public void test128ViewByCourseId() throws CourseInvalidException {
		Course course = new Course();
		course.setRating(4.5f);
		when(courseRepository.findById("201")).thenReturn(Optional.of(course));

		// Call the method to be tested
		Course course1 = courseService.viewByCourseId("201");

		// Verify the interactions and assertions
		verify(courseRepository, times(1)).findById("201");
		Assertions.assertEquals(course, course1);
	}

	@Test
	public void test129FindFeedbackRatingForCourseId() throws CourseInvalidException {
		// Mock the behavior of the courseRepository
		Course course = new Course();
		course.setRating(4.5f);
		when(courseRepository.findById("201")).thenReturn(Optional.of(course));

		// Call the method to be tested
		float rating = courseService.findFeedbackRatingForCourseId("201");

		// Verify the interactions and assertions
		verify(courseRepository, times(1)).findById("201");
		Assertions.assertEquals(4.5f, rating);
	}

	@Test
	public void test130UpdateCourse() throws CourseInvalidException {
		// Mock the behavior of the courseRepository
		Course existingCourse = new Course();
		existingCourse.setCourseId("201");
		existingCourse.setFees(1000);
		when(courseRepository.findById("201")).thenReturn(Optional.of(existingCourse));
		when(courseRepository.save(any(Course.class))).thenReturn(existingCourse);

		// Call the method to be tested
		Course updatedCourse = courseService.updateCourse("201", 1500);

		// Verify the interactions and assertions
		verify(courseRepository, times(1)).findById("201");
		verify(courseRepository, times(1)).save(existingCourse);
		Assertions.assertEquals(1500, updatedCourse.getFees());
	}

	@Test
	public void test131CalculateAverageFeedbackAndUpdate() throws CourseInvalidException {
		// Mock the behavior of the courseRepository
		Course existingCourse = new Course();
		existingCourse.setCourseId("201");
		existingCourse.setRating(4.0f);
		when(courseRepository.findById("201")).thenReturn(Optional.of(existingCourse));
		when(courseRepository.save(any(Course.class))).thenReturn(existingCourse);

		// Call the method to be tested
		Course updatedCourse = courseService.calculateAverageFeedbackAndUpdate("201", 4.5f);

		// Verify the interactions and assertions
		verify(courseRepository, times(1)).findById("201");
		verify(courseRepository, times(1)).save(existingCourse);
		Assertions.assertEquals(4.25f, updatedCourse.getRating());
	}

	@Test
	public void test132DeactivateCourse() throws CourseInvalidException {
		// Mock the behavior of the courseRepository
		Course existingCourse = new Course();
		existingCourse.setCourseId("201");
		when(courseRepository.findById("201")).thenReturn(Optional.of(existingCourse));

		// Call the method to be tested
		Course deactivatedCourse = courseService.deactivateCourse("201");

		// Verify the interactions and assertions
		verify(courseRepository, times(1)).findById("201");
		verify(courseRepository, times(1)).delete(existingCourse);
		Assertions.assertEquals(existingCourse, deactivatedCourse);
	}

	@Test
	public void test133ViewByCourseIdForInvalidId() throws CourseInvalidException{
		when(courseRepository.findById("201")).thenReturn(Optional.empty());

		// Call the method to be tested and verify if the CourseInvalidException is thrown
		Assertions.assertThrows(CourseInvalidException.class, () -> {
			courseService.viewByCourseId("201");
		});

		// Verify the interactions
		verify(courseRepository, times(1)).findById("201");
	}

	@Test
	public void test135UpdateCourseInvalidId() {
		// Mock the behavior of the courseRepository
		when(courseRepository.findById("201")).thenReturn(Optional.empty());

		// Call the method to be tested and verify if the CourseInvalidException is thrown
		Assertions.assertThrows(CourseInvalidException.class, () -> {
			courseService.updateCourse("201", 1500);
		});

		// Verify the interactions
		verify(courseRepository, times(1)).findById("201");
	}

	@Test
	public void test136CalculateAverageFeedbackAndUpdateForInvalidId() {
		// Mock the behavior of the courseRepository
		when(courseRepository.findById("201")).thenReturn(Optional.empty());

		// Call the method to be tested and verify if the CourseInvalidException is thrown
		Assertions.assertThrows(CourseInvalidException.class, () -> {
			courseService.calculateAverageFeedbackAndUpdate("201", 4.5f);
		});

		// Verify the interactions
		verify(courseRepository, times(1)).findById("201");
	}

	@Test
	public void test137FindFeedbackRatingForCourseIdForInvalidId() {
		// Mock the behavior of the courseRepository
		when(courseRepository.findById("201")).thenReturn(Optional.empty());

		// Call the method to be tested and verify if the CourseInvalidException is thrown
		Assertions.assertThrows(CourseInvalidException.class, () -> {
			courseService.findFeedbackRatingForCourseId("201");
		});

		// Verify the interactions
		verify(courseRepository, times(1)).findById("201");
	}

	@Test
	public void test138DeactivateCourseForInvalidId() {
		// Mock the behavior of the courseRepository
		when(courseRepository.findById("201")).thenReturn(Optional.empty());

		// Call the method to be tested and verify if the CourseInvalidException is thrown
		Assertions.assertThrows(CourseInvalidException.class, () -> {
			courseService.deactivateCourse("201");
		});

		// Verify the interactions
		verify(courseRepository, times(1)).findById("201");
	}

	@Test
	public void test139ViewAll() {
		// Mock the behavior of the courseRepository
		List<Course> courses = new ArrayList<>();
		when(courseRepository.findAll()).thenReturn(courses);

		// Call the method to be tested
		List<Course> result = courseService.viewAll();

		// Verify the interactions and assertions
		verify(courseRepository, times(1)).findAll();
		Assertions.assertEquals(courses, result);
	}
}




//
//
////Write Unit Tests for the methods in the CourseServiceImpl
//
//public class CourseServiceImplTest {
//
////check whether the addCourse persists the course object in the database
//	public void test127AddCourse() {
//
//	}
//
////check whether viewByCourseId returns the course for the given courseId
//	public void test128ViewByCourseId() {
//
//	}
//
////check whether the findFeedbackRatingForCourseId	returns the feedback rating for the given courseId
//	public void test129FindFeedbackRatingForCourseId() {
//
//	}
//
//	//check whether updateCourse updates the fees for the given courseId in the database
//	public void test130UpdateCourse() {
//
//	}
//
////check whether the calculateAverageFeedbackAndUpdate calculates the average feedback rating with the given rating and existing rating in the database for the given courseId and updates in the database
//	public void test131CalculateAverageFeedbackAndUpdate() {
//
//	}
//
////check whether the deactivateCourse removes the course of the given courseId in the database
//	public void test132DeactivateCourse() {
//
//	}
//
////check whether viewByCourseId throws CourseInvalidException when an invalid courseid is passed
//	public void test133ViewByCourseIdForInvalidId() {
//
//	}
//
//
//	//check whether updateCourse throws CourseInvalidException for invalid course id
//	public void test135updateCourseInvalidId() {
//
//	}
//
//	//check whether calculateAverageFeedbackAndUpdate throws CourseInvalidExcception for invalid course id
//	public void test136calculateAverageFeedbackAndUpdateForInvalidId() {
//
//	}
//	//check whether findFeedbackRating throws CourseInvalidExcception for invalid course id
//	public void test137findFeedbackRatingForCourseIdForInvalidId() {
//
//	}
//
//	//check whether deactivateCourse throws CourseInvalidExcception for invalid course id
//	public void test138deactivateCourseForInvalidId() {
//
//	}
//
//
//
//}
