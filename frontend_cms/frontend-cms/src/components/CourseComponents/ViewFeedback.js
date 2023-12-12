import React, { Component } from 'react';
import CourseService from '../../Services/CourseService';
import Course from './Course';

class CourseRating extends Component {
  constructor(props) {
    super(props);

    this.state = {
      courseId: '',
      rating: null,
      message: ''
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = async (e) => {
    e.preventDefault();

    const { courseId } = this.state;

    // Validate input field
    if (!courseId) {
      this.setState({ message: 'Please enter a course ID', rating: null });
      return;
    }

    try {
      // Call the viewFeedback method of CourseService to get the course feedback rating
      const response = await CourseService.viewFeedback(courseId);
      
      // Extract the feedback rating from the response
      const rating = response.data;
      
      // Update the state with the rating
      this.setState({ rating, message: '' });
    } catch (error) {
      // Error occurred while fetching course feedback
      this.setState({ rating: null, message: 'Course ID does not exist' });
    }
  };

  render() {
    const { rating, message } = this.state;

    return (
      <div className="container">
        <Course />
        <h3 className="text-center">Course Feedback Rating</h3>
        <form onSubmit={this.handleSubmit} className="form-container">
          <div className="form-inline justify-content-center">
            <div className="form-group">
              <label htmlFor="courseId">Course ID:</label>
              <input type="text" id="courseId" name="courseId" className="form-control form-control-sm" onChange={this.handleInputChange} />
            </div>
            <button type="submit" className="btn btn-primary ml-2">View Feedback Rating</button>
          </div>
        </form>

        {message && <div className="message">{message}</div>}

        {typeof rating === 'number' && (
          <div className="courseRating">
            <h4>Course Feedback Rating</h4>
            <p>Course ID: {this.state.courseId}</p>
            <p>Feedback Rating: {rating}</p>
          </div>
        )}
      </div>
    );
  }
}

export default CourseRating;
