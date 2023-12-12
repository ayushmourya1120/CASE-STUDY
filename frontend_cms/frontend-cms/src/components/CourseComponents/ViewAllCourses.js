import React, { Component } from 'react';
import CourseService from '../../Services/CourseService';
import Course from './Course';

class ViewAllCourses extends Component {
  constructor(props) {
    super(props);

    this.state = {
      courses: []
    };
  }

  componentDidMount() {
    CourseService.getCourses().then((res) => {
      this.setState({ courses: res.data });
    });
  }

  render() {
    return (
      <div className="container">
        <Course />
        <h2 className="text-center">Courses List</h2>
        <div className="row">
          <table className="table table-striped table-bordered">
            <thead>
              <tr>
                <th>Course ID</th>
                <th>Course Name</th>
                <th>Fees</th>
                <th>Duration</th>
                <th>Course Type</th>
                <th>Course Rating</th>
              </tr>
            </thead>
            <tbody>
              {this.state.courses.map((course) => (
                <tr key={course.courseId}>
                  <td>{course.courseId}</td>
                  <td>{course.courseName}</td>
                  <td>{course.fees}</td>
                  <td>{course.duration}</td>
                  <td>{course.courseType}</td>
                  <td>{course.rating}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default ViewAllCourses;
