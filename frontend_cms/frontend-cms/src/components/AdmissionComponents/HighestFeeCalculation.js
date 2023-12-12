import React, { Component } from 'react';
import AdmissionService from '../../Services/AdmissionService';
import Admission from './Admission';

class HighestFeeCalculation extends Component {
  constructor(props) {
    super(props);

    this.state = {
      associateId: '',
      message: '',
      highestFeeArray: [],
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = (e) => {
    e.preventDefault();

    const { associateId } = this.state;

    // Validate input field
    if (!associateId) {
      this.setState({ message: 'Please enter Associate Id' });
      return;
    }

    // Call the highestFee method of AdmissionService to retrieve the highest fee course id
    AdmissionService.getHighestFeeCourse(associateId)
      .then((res) => {
        // Highest fee retrieved successfully
        this.setState({ highestFeeArray: res.data, message: '' });
      })
      .catch((error) => {
        // Error occurred while retrieving highest fee
        this.setState({ highestFeeArray: [], message: 'Failed to retrieve highest fee' });
      });
  };

  render() {
    const { associateId, message, highestFeeArray } = this.state;

    return (
      <div className="container">
        <div className="highestFeeCalculation">
          <Admission />
          <h3 className="text-center">Associate Highest Fee</h3>
          <form onSubmit={this.handleSubmit} className="form-container">
            <div className="form-group d-flex justify-content-center align-items-center">
              <label htmlFor="associateId" className="mr-2">Associate Id:</label>
              <input
                type="text"
                id="associateId"
                name="associateId"
                className="form-control"
                style={{ width: '200px' }}
                value={associateId}
                onChange={this.handleInputChange}
              />
              <button type="submit" className="btn btn-primary ml-2">
                Submit
              </button>
            </div>
          </form>

          {message && <div className="message error">{message}</div>}

          {highestFeeArray.length > 0 && (
            <div>
              <h5 className="mt-3">CourseId of course having Highest Fee:</h5>
              {highestFeeArray.map((courseId) => (
                <div key={courseId}>{courseId}</div>
              ))}
            </div>
          )}
        </div>
      </div>
    );
  }
}

export default HighestFeeCalculation;
