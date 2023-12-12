import React, { Component } from 'react';
import '../../App.css';
import CourseService from '../../Services/CourseService';
import Course from './Course';

class AddCourse extends Component {
  constructor(props) {
    super(props);

    this.state = {
      courseName: '',
      fees: '',
      duration: '',
      courseType: '',
      message: '',
      isSuccess: false,
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = (e) => {
    e.preventDefault();

    const { courseName, fees, duration, courseType } = this.state;

    // Validate input fields
    if (!courseName || !fees || !duration || !courseType) {
      this.setState({ message: 'Please fill in all fields', isSuccess: false });
      return;
    }

    // Create course object
    const course = {
      courseName,
      fees,
      duration,
      courseType,
    };

    // Call the addCourseDetails method of CourseService to add the course to DB
    CourseService.addCourse(course)
      .then((res) => {
        // Course added successfully
        this.setState(
          {
            courseName: '',
            fees: '',
            duration: '',
            courseType: '',
            message: 'Course added successfully',
            isSuccess: true,
          },
          () => {
            // Clear the message after 3 seconds
            setTimeout(() => {
              this.setState({ message: '', isSuccess: false });
            }, 3000);
          }
        );
      })
      .catch((error) => {
        // Error occurred while adding the course
        this.setState({ message: 'Failed to add course', isSuccess: false });
      });
  };

  render() {
    const { courseName, fees, duration, courseType, message, isSuccess } = this.state;

    return (
      <div className="addCourse">
        <Course />
        <h3 className="text-center">Add Course</h3>
        <br />
        <div className="container">
          <div className="row">
            <div className="col-md-6 offset-md-3">
              <div className="card">
                <div className="card-body">
                  <form onSubmit={this.handleSubmit}>
                    <div className="form-group row">
                      <label htmlFor="courseName" className="col-sm-3 col-form-label">
                        Course Name:
                      </label>
                      <div className="col-sm-9">
                        <input
                          type="text"
                          id="courseName"
                          name="courseName"
                          className="form-control"
                          value={courseName}
                          onChange={this.handleInputChange}
                        />
                      </div>
                    </div>

                    <div className="form-group row">
                      <label htmlFor="fees" className="col-sm-3 col-form-label">
                        Fees:
                      </label>
                      <div className="col-sm-9">
                        <input
                          type="number"
                          id="fees"
                          name="fees"
                          className="form-control"
                          min="0"
                          step="1"
                          value={fees}
                          onChange={this.handleInputChange}
                        />
                      </div>
                    </div>

                    <div className="form-group row">
                      <label htmlFor="duration" className="col-sm-3 col-form-label">
                        Duration:
                      </label>
                      <div className="col-sm-9">
                        <input
                          type="number"
                          id="duration"
                          name="duration"
                          className="form-control"
                          min="0"
                          step="1"
                          value={duration}
                          onChange={this.handleInputChange}
                        />
                      </div>
                    </div>

                    <div className="form-group row">
                      <label htmlFor="courseType" className="col-sm-3 col-form-label">
                        Course Type:
                      </label>
                      <div className="col-sm-9">
                        <input
                          type="text"
                          id="courseType"
                          name="courseType"
                          className="form-control"
                          value={courseType}
                          onChange={this.handleInputChange}
                        />
                      </div>
                    </div>

                    <button type="submit" className="btn btn-primary">
                      Add Course
                    </button>
                    <br />
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>

        {message && (
          <div className={`message ${isSuccess ? 'success' : 'error'}`}>
            {message}
          </div>
        )}
      </div>
    );
  }
}

export default AddCourse;
