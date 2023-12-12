import React, { Component } from 'react';
import CourseService from '../../Services/CourseService';
import Course from './Course';

class UpdateCourse extends Component {
  constructor(props) {
    super(props);

    this.state = {
      courseId: '',
      duration: '',
      message: '',
      isSuccess: false
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = (e) => {
    e.preventDefault();

    const { courseId, duration } = this.state;

    // Validate input fields
    if (!courseId || !duration) {
      this.setState({ message: 'Please fill in all fields', isSuccess: false });
      return;
    }

    // Call the updateCourse method of CourseService to update the course duration
    CourseService.updateCourse(courseId, duration)
      .then((res) => {
        // Course updated successfully
        this.setState({ message: 'Course updated successfully', courseId: '', duration: '', isSuccess: true });
      })
      .catch((error) => {
        // Error occurred while updating the course
        this.setState({ message: 'Failed to update course', isSuccess: false });
      });
  };

  render() {
    const { message, isSuccess } = this.state;
    const messageClass = isSuccess ? 'success' : 'failure';

    return (
      <div className="updateCourse">
        <Course />
        <h3 className="text-center">Update Course Duration</h3><br />
        <div className="container">
          <div className="row">
            <div className="col-md-6 offset-md-4">
              <div className="card">
                <div className="card-body">
                  <form onSubmit={this.handleSubmit}>
                    <div className="form-group row">
                      <label htmlFor="courseId" className="col-sm-3 col-form-label">Course ID:</label>
                      <div className="col-sm-9">
                        <input type="text" id="courseId" name="courseId" className="form-control" value={this.state.courseId} onChange={this.handleInputChange} />
                      </div>
                    </div>
                    <div className="form-group row">
                      <label htmlFor="duration" className="col-sm-3 col-form-label">Duration:</label>
                      <div className="col-sm-9">
                        <input type="text" id="duration" name="duration" className="form-control" value={this.state.duration} onChange={this.handleInputChange} />
                      </div>
                    </div>
                    <button type="submit" className="btn btn-primary">Update Course</button><br />
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
        {message && <div className={`message ${messageClass}`}>{message}</div>}
      </div>
    );
  }
}

export default UpdateCourse;
