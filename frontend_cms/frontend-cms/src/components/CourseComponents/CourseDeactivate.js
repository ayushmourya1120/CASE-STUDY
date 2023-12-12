import React, { Component } from 'react';
import CourseService from '../../Services/CourseService';
import Course from './Course';

class CourseDeactivate extends Component {
  constructor(props) {
    super(props);

    this.state = {
      courseId: '',
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

    // Call the deactivate method of CourseService to deactivate the course
    CourseService.deactivateCourse(courseId)
      .then((res) => {
        // Course deactivated successfully
        this.setState({ message: `Course ${courseId} is deactivated successfully` });
      })
      .catch((error) => {
        // Error occurred while deactivating the course
        this.setState({ message: 'Failed to deactivate course' });
      });
  };

  render() {
    return (
      <div className="courseDeactivate">
        <Course />
        <h3 className="text-center">Course Deactivation</h3>
        <br />
        <div className="container">
          <div className="row">
            <div className="col-md-6 offset-md-3">
              <form onSubmit={this.handleSubmit}>
                <div className="form-inline justify-content-center">
                  <div className="form-group">
                    <label htmlFor="courseId">Course ID:</label>
                    <input
                      type="text"
                      id="courseId"
                      name="courseId"
                      className="form-control"
                      onChange={this.handleInputChange}
                    />
                  </div>
                  <button type="submit" className="btn btn-primary ml-2">Deactivate Course</button>
                </div>
              </form>
              {this.state.message && <div className="message">{this.state.message}</div>}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default CourseDeactivate;
