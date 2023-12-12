import React, { Component } from 'react';
import CourseService from '../../Services/CourseService';
import Course from './Course';

class ViewCourse extends Component {
  constructor(props) {
    super(props);

    this.state = {
      courseId: '',
      courseDetails: null,
      message: ''
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = (e) => {
    e.preventDefault();

    const { courseId } = this.state;

    // Validate input field
    if (!courseId) {
      this.setState({ message: 'Please enter a course ID' });
      return;
    }

    // Call the viewCourseById method of CourseService to get course details
    CourseService.viewCourseById(courseId)
      .then((res) => {
        // Course details fetched successfully
        this.setState({ courseDetails: res.data, message: '' });
      })
      .catch((error) => {
        // Error occurred while fetching course details
        this.setState({ courseDetails: null, message: 'Failed to fetch course details' });
      });
  };

  render() {
    const { courseDetails, message } = this.state;

    return (
      <div className="container text-center">
        <Course />
        <h3>View Course By ID</h3>
        <form onSubmit={this.handleSubmit} className="form-inline justify-content-center">
          <div className="form-group mb-2">
            <label htmlFor="courseId" className="sr-only">Course ID:</label>
            <input type="text" id="courseId" name="courseId" className="form-control form-control-sm mr-2" onChange={this.handleInputChange} />
          </div>
          <button type="submit" className="btn btn-primary mb-2">View Course</button>
        </form>

        {message && <div className="message">{message}</div>}

        {courseDetails && (
          <div className="courseDetails">
            <h4>Course Details</h4>
            <table className="table">
              <thead>
                <tr>
                  <th>Course ID</th>
                  <th>Course Name</th>
                  <th>Fees</th>
                  <th>Duration</th>
                  <th>Course Type</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{courseDetails.courseId}</td>
                  <td>{courseDetails.courseName}</td>
                  <td>{courseDetails.fees}</td>
                  <td>{courseDetails.duration}</td>
                  <td>{courseDetails.courseType}</td>
                </tr>
              </tbody>
            </table>
          </div>
        )}
      </div>
    );
  }
}

export default ViewCourse;
