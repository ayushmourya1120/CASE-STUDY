import React, { Component } from 'react';
import admissionService from '../../Services/AdmissionService';
import Admission from './Admission';

class AssociateRegistration extends Component {
  constructor(props) {
    super(props);

    this.state = {
      courseId: '',
      associateId: '',
      message: ''
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = async (e) => {
    e.preventDefault();

    const { associateId, courseId } = this.state;

    // Validate input fields
    if (!associateId || !courseId) {
      this.setState({ message: 'Please fill in all fields' });
      return;
    }

    try {
      // Call the registerAssociateForCourse method of AdmissionService to register the associate for the course
      const response = await admissionService.registerAssociateForCourse(associateId, courseId, {});
      const result = response.data;

      // Registration successful
      this.setState({
        message: `Registered successfully. Your registration id: ${result.registrationId}`,
        associateId: '',
        courseId: ''
      });
    } catch (error) {
      // Error occurred during registration
      if (error.response && error.response.data && error.response.data.message) {
        this.setState({ message: error.response.data.message });
      } else {
        this.setState({ message: 'Failed to register' });
      }
    }
  };

  render() {
    const { associateId, courseId, message } = this.state;

    return (
      <div className="associate-registration">
        <Admission />
        <div className="container">
          <h3 className="text-center">Associate Registration Form</h3>
          <form onSubmit={this.handleSubmit} className="form-container">
            <div className="form-group row">
              <label htmlFor="courseId" className="col-sm-3 col-form-label">Course ID:</label>
              <div className="col-sm-9">
                <input
                  type="text"
                  id="courseId"
                  name="courseId"
                  className="form-control"
                  value={courseId}
                  onChange={this.handleInputChange}
                />
              </div>
            </div>
            <div className="form-group row">
              <label htmlFor="associateId" className="col-sm-3 col-form-label">Associate ID:</label>
              <div className="col-sm-9">
                <input
                  type="text"
                  id="associateId"
                  name="associateId"
                  className="form-control"
                  value={associateId}
                  onChange={this.handleInputChange}
                />
              </div>
            </div>
            <div className="text-center">
              <button type="submit" className="btn btn-primary">
                Register Now
              </button>
            </div>
          </form>
          {message && <div className="message text-center">{message}</div>}
        </div>
      </div>
    );
  }
}

export default AssociateRegistration;
