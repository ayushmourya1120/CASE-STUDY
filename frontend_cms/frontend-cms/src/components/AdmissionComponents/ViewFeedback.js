import React, { Component } from 'react';
import AdmissionService from '../../Services/AdmissionService';
import Admission from './Admission';

class ViewFeedback extends Component {
  constructor(props) {
    super(props);

    this.state = {
      courseId: '',
      feedback: [],
      message: '',
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
      this.setState({ message: 'Please enter Course Id' });
      return;
    }

    // Call the viewFeedbackByCourseId method of AdmissionService
    // to retrieve the feedback for the entered course id
    AdmissionService.viewFeedbackByCourseId(courseId)
      .then((res) => {
        // Feedback retrieved successfully
        this.setState({ feedback: res.data, message: '' });
      })
      .catch((error) => {
        // Error occurred while retrieving feedback
        this.setState({ feedback: [], message: 'Failed to retrieve feedback' });
      });
  };

  render() {
    const { courseId, feedback, message } = this.state;

    return (
      <div className="container">
        <div className="viewFeedback">
          <Admission />
          <h3 className="text-center">View Course Feedback</h3>
          <form onSubmit={this.handleSubmit} className="form-container">
            <div className="form-group d-flex justify-content-center align-items-center">
              <label htmlFor="courseId" className="mr-2">Course Id:</label>
              <input
                type="text"
                id="courseId"
                name="courseId"
                className="form-control"
                style={{ width: '200px' }}
                value={courseId}
                onChange={this.handleInputChange}
              />
              <button type="submit" className="btn btn-primary ml-2">
                Get Feedback
              </button>
            </div>
          </form>

          {message && <div className="message error">{message}</div>}

          {feedback.length > 0 && (
            <div>
              <h5>Course Id: {courseId}</h5>
              <h5>Feedback:</h5>
              <ul>
                {feedback.map((item, index) => (
                  <li key={index}>{item}</li>
                ))}
              </ul>
            </div>
          )}
        </div>
      </div>
    );
  }
}

export default ViewFeedback;
