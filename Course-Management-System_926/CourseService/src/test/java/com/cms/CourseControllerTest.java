package com.cms;

import com.cms.controller.CourseController;
import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.service.ICourseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class )
public class CourseControllerTest {

	@Mock
	private ICourseService courseService;

	@InjectMocks
	private CourseController courseController;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test122RestApiCallForViewByCourseId() throws CourseInvalidException {
		Course course = new Course();
		course.setCourseId("201");
		course.setCourseName("English");

		when(courseService.viewByCourseId(anyString())).thenReturn(course);

		ResponseEntity<Course> response = courseController.viewByCourseId("201");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(course, response.getBody());
	}

	@Test
	public void test123RestApiCallForUpdateCourse() throws CourseInvalidException {
		Course course = new Course();
		course.setCourseId("201");
		course.setCourseName("English");

		when(courseService.updateCourse(anyString(), anyInt())).thenReturn(course);

		ResponseEntity<Course> response = courseController.updateCourse("201", 1000);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(course, response.getBody());
	}

	@Test
	public void test124RestApiCallForViewFeedbackRating() throws CourseInvalidException {
		Course course = new Course();
		course.setCourseId("201");
		course.setCourseName("English");
		course.setRating(7);
		when(courseService.findFeedbackRatingForCourseId(anyString())).thenReturn(course.getRating());

		ResponseEntity<Course> response = courseController.findFeedbackRatingForCourseId("201");

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void test125RestApiCallForCalculateAverageFeedback() throws CourseInvalidException {
		Course course = new Course();
		course.setCourseId("201");
		course.setCourseName("English");

		when(courseService.calculateAverageFeedbackAndUpdate(anyString(), anyFloat())).thenReturn(course);

		ResponseEntity<Course> response = courseController.calculateAverageFeedbackAndUpdate("201", 4.5f);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(course, response.getBody());
	}

	@Test
	public void test126RestApiCallForAddCourse() throws CourseInvalidException {
		Course course = new Course();
		course.setCourseId("201");
		course.setCourseName("English");

		when(courseService.addCourse(any(Course.class))).thenReturn(course);

		ResponseEntity<Course> response = courseController.addCourse(course);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(course, response.getBody());
	}

	@Test
	public void test122RestApiCallForViewByCourseIdForInvalidId() throws CourseInvalidException {
		when(courseService.viewByCourseId(anyString())).thenThrow(new CourseInvalidException("Invalid Course Id"));

		ResponseEntity<Course> response = courseController.viewByCourseId("InvalidId");

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Invalid Course Id", response.getBody());
	}

	@Test
	public void test122RestApiCallForViewByCourseIdForInvalidToken() throws CourseInvalidException {
		when(courseService.viewByCourseId(anyString())).thenThrow(new SecurityException());

		ResponseEntity<Course> response = courseController.viewByCourseId("201");

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Unauthorized Access", response.getBody());
	}

	@Test
	public void test123RestApiCallForUpdateCourseForInvalidId() throws CourseInvalidException {
		when(courseService.updateCourse(anyString(), anyInt())).thenThrow(new CourseInvalidException("Invalid Course Id"));

		ResponseEntity<Course> response = courseController.updateCourse("InvalidId", 1000);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("CourseId does not exists", response.getBody());
	}

	@Test
	public void test123RestApiCallForUpdateCourseForInvalidToken() throws CourseInvalidException {
		when(courseService.updateCourse(anyString(), anyInt())).thenThrow(new SecurityException());

		ResponseEntity<Course> response = courseController.updateCourse("201", 1000);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Unauthorized Access", response.getBody());
	}

	@Test
	public void test124RestApiCallForViewFeedbackRatingForInvalidId() throws CourseInvalidException {
		when(courseService.findFeedbackRatingForCourseId(anyString())).thenThrow(new CourseInvalidException("Invalid Course Id"));

		ResponseEntity<Course> response = courseController.findFeedbackRatingForCourseId("InvalidId");

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("CourseId does not exists", response.getBody());
	}

	@Test
	public void test124RestApiCallForViewFeedbackRatingForInvalidToken() throws CourseInvalidException {
		when(courseService.findFeedbackRatingForCourseId(anyString())).thenThrow(new SecurityException());

		ResponseEntity<Course> response = courseController.findFeedbackRatingForCourseId("201");

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Unauthorized Access", response.getBody());
	}

	@Test
	public void test125RestApiCallForCalculateAverageFeedbackForInvalidId() throws CourseInvalidException {
		when(courseService.calculateAverageFeedbackAndUpdate(anyString(), anyFloat())).thenThrow(new CourseInvalidException("Invalid Course Id"));

		ResponseEntity<Course> response = courseController.calculateAverageFeedbackAndUpdate("InvalidId", 4.5f);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("CourseId does not exists", response.getBody());
	}

	@Test
	public void test125RestApiCallForCalculateAverageFeedbackForInvalidToken() throws CourseInvalidException {
		when(courseService.calculateAverageFeedbackAndUpdate(anyString(), anyFloat())).thenThrow(new SecurityException());

		ResponseEntity<Course> response = courseController.calculateAverageFeedbackAndUpdate("201", 4.5f);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Unauthorized Access", response.getBody());
	}

	@Test
	public void test126RestApiCallForAddCourseForInvalidToken() throws CourseInvalidException {
		when(courseService.addCourse(any(Course.class))).thenThrow(new SecurityException());

		ResponseEntity<Course> response = courseController.addCourse(new Course());

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Unauthorized Access", response.getBody());
	}

//	@Test
//	public void testRestApiCallForDeactivateCourse() throws CourseInvalidException {
//		Course course = new Course();
//		course.setCourseId("201");
//		course.setCourseName("English");
//
//		when(courseService.deactivateCourse(anyString())).thenReturn(course);
//
//		ResponseEntity<Course> response = courseController.deactivateCourse("201");
//
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals(course, response.getBody());
//	}
//
//	@Test
//	public void testRestApiCallForDeactivateCourseForInvalidId() throws CourseInvalidException {
//		when(courseService.deactivateCourse(anyString())).thenThrow(new CourseInvalidException("Invalid Course Id"));
//
//		ResponseEntity<Course> response = courseController.deactivateCourse("InvalidId");
//
//		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//		assertEquals("CourseId does not exists", response.getBody());
//	}
//
//	@Test
//	public void testRestApiCallForDeactivateCourseForInvalidToken() throws CourseInvalidException {
//		when(courseService.deactivateCourse(anyString())).thenThrow(new SecurityException());
//
//		ResponseEntity<Course> response = courseController.deactivateCourse("201");
//
//		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//		assertEquals("Unauthorized Access", response.getBody());
//	}
//
//	@Test
//	public void testRestApiCallForViewAll() {
//		List<Course> courses = new ArrayList<>();
//		Course course1 = new Course();
//		course1.setCourseId("201");
//		course1.setCourseName("English");
//		courses.add(course1);
//
//		Course course2 = new Course();
//		course2.setCourseId("201");
//		course2.setCourseName("Mathematics");
//		courses.add(course2);
//
//		when(courseService.viewAll()).thenReturn(courses);
//
//		ResponseEntity<List<Course>> response = courseController.viewAll();
//
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals(courses, response.getBody());
//	}
}



//
////Write mockito tests for the endpoints in the CourseController
//
//public class CourseControllerTest {
//
//	//Test whether the endpoint /viewByCourseId/{courseId} is successful
//	public void test122RestApiCallForViewByCourseId() {
//
//	}
//
//	// Test whether the end point /update/{courseId}/{fee} is successfull
//	public void test123RestApiCallForUpdateCourse() {
//
//	}
//
//	//Test whether the endpoint /viewByCourseId/{courseId} is successfull
//	public void test124RestApiCallForViewFeedbackRating() {
//
//	}
//
//    // Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is successfull
//	public void test125RestApiCallForCalculateAverageFeedback() {
//
//	}
//
//	//Test whether the endpoint /addCourse is successfull
//	public void test126RestApiCallForAddCourse() {
//
//	}
//
//	//Test whether the endpoint /viewByCourseId/{courseId} is successful for invalid id
//	public void test122RestApiCallForViewByCourseIdForInvalidId() {
//
//	}
//
//	//Test whether the endpoint /viewByCourseId/{courseId} is successful for invalid token
//	public void test122RestApiCallForViewByCourseIdForInvalidToken() {
//
//	}
//
//	// Test whether the end point /update/{courseId}/{fee} is successfull for invalid id
//	public void test123RestApiCallForUpdateCourseForInvalidId() {
//
//	}
//
//	// Test whether the end point /update/{courseId}/{fee} is successfull for invalid token
//	public void test123RestApiCallForUpdateCourseForInvalidToken() {
//
//	}
//
//
//	//Test whether the endpoint /viewByCourseId/{courseId} is successfull for invalid id
//	public void test124RestApiCallForViewFeedbackRatingForInvalidId() {
//
//	}
//
//	//Test whether the endpoint /viewByCourseId/{courseId} is successfull for invalid token
//	public void test124RestApiCallForViewFeedbackRatingForInvalidToken() {
//
//	}
//
//	// Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is successfull for invalid id
//	public void test125RestApiCallForCalculateAverageFeedbackForInvalidId() {
//
//	}
//
//	// Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is successfull for invalid token
//	public void test125RestApiCallForCalculateAverageFeedbackForInvalidToken() {
//
//	}
//
//	//Test whether the endpoint /addCourse is successfull for invalid token
//	public void test126RestApiCallForAddCourseForInvalidToken() {
//
//	}
//
//}
//
