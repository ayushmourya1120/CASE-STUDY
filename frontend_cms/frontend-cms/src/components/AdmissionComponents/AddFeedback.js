import React, { Component } from 'react';
import AdmissionService from '../../Services/AdmissionService';
import Admission from './Admission';

class AddFeedback extends Component {
  constructor(props) {
    super(props);

    this.state = {
      regNo: '',
      feedback: '',
      feedbackRating: '',
      message: '',
      isSuccess: false,
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = (e) => {
    e.preventDefault();

    const { regNo, feedback, feedbackRating } = this.state;

    // Validate input fields
    if (!regNo || !feedback || !feedbackRating) {
      this.setState({ message: 'Please fill in all fields', isSuccess: false });
      return;
    }

    // Call the addFeedback method of AdmissionService to add the feedback
    AdmissionService.addFeedback(regNo, feedback, feedbackRating)
      .then((res) => {
        // Feedback added successfully
        this.setState(
          {
            regNo: '',
            feedback: '',
            feedbackRating: '',
            message: 'Feedback added successfully',
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
        // Error occurred while adding the feedback
        this.setState({ message: 'Failed to add feedback', isSuccess: false });
      });
  };

  render() {
    const { regNo, feedback, feedbackRating, message, isSuccess } = this.state;

    return (
      <div className="addFeedback">
        <Admission />

        <div className="container">
          <h3 className="text-center">Add Feedback and Average Course Rating</h3>
          <form onSubmit={this.handleSubmit} className="form-container">
            <div className="form-group row">
              <label htmlFor="regNo" className="col-sm-3 col-form-label">
                Register Number:
              </label>
              <div className="col-sm-9">
                <input
                  type="text"
                  id="regNo"
                  name="regNo"
                  className="form-control"
                  value={regNo}
                  onChange={this.handleInputChange}
                />
              </div>
            </div>
            <div className="form-group row">
              <label htmlFor="feedback" className="col-sm-3 col-form-label">
                Feedback Comments:
              </label>
              <div className="col-sm-9">
                <input
                  type="text"
                  id="feedback"
                  name="feedback"
                  className="form-control"
                  value={feedback}
                  onChange={this.handleInputChange}
                />
              </div>
            </div>
            <div className="form-group row">
              <label
                htmlFor="feedbackRating"
                className="col-sm-3 col-form-label"
              >
                Feedback Rating (Rate from 1 to 5):
              </label>
              <div className="col-sm-9">
                <input
                  type="number"
                  id="feedbackRating"
                  name="feedbackRating"
                  className="form-control"
                  min="1"
                  max="5"
                  value={feedbackRating}
                  onChange={this.handleInputChange}
                />
              </div>
            </div>
            <div className="text-center">
              <button type="submit" className="btn btn-primary">
                Submit
              </button>
            </div>
          </form>
          {message && (
            <div className={`message ${isSuccess ? 'success' : 'error'}`}>
              {message}
            </div>
          )}
        </div>
      </div>
    );
  }
}

export default AddFeedback;
