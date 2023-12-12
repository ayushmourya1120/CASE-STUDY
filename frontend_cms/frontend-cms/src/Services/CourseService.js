import axios from 'axios';

const COURSE_API_BASE_URL = "http://localhost:7777/course"


class CourseService {
    // Get all courses
    getCourses() {
      return axios.get(`${COURSE_API_BASE_URL}/viewAll`);
    }


     // Add a new course
  addCourse(course) {
    const accessToken = localStorage.getItem('accessToken');
    console.log('toooooooooo',accessToken);
    console.log('course '+JSON.stringify(course));
    return axios.post(`${COURSE_API_BASE_URL}/addCourse`, JSON.stringify(course), {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' +accessToken,
      },
  

    });    
  }

// Update course duration
updateCourse(courseId, duration) {
  const accessToken = localStorage.getItem('accessToken');
  console.log('toooooooooo',accessToken);
  const updatedCourse = { duration };
  return axios.put(`${COURSE_API_BASE_URL}/updateDuration/${courseId}/${duration}`, 
  JSON.stringify(updatedCourse),{
   headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' +accessToken,
      },
  

    });    
  
}


 // View course by ID
 viewCourseById(courseId) {
  return axios.get(`${COURSE_API_BASE_URL}/viewCourseByCourseId/${courseId}`);
}

 // View course feedback rating
 viewFeedback(courseId) {
  return axios.get(`${COURSE_API_BASE_URL}/viewFeedback/${courseId}`);
}


 // Deactivate course
 deactivateCourse(courseId) {
  return axios.delete(`${COURSE_API_BASE_URL}/deactivate/${courseId}`);
}

  }



const courseService = new CourseService();

export default courseService;