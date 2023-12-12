import React, { Component } from 'react';
import AdmissionService from '../../Services/AdmissionService';
import Admission from './Admission';

class TotalFees extends Component {
  constructor(props) {
    super(props);

    this.state = {
      associateId: '',
      fee: 0,
      message: ''
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = async (e) => {
    e.preventDefault();

    const { associateId } = this.state;

    // Validate input field
    if (!associateId) {
      this.setState({ message: 'Please enter an associate ID' });
      return;
    }

    try {
      // Call the calculateFees method of AdmissionService to get the total fees
      const response = await AdmissionService.calculateFees(associateId);
      const fees = response.data;

      // Total fees retrieved successfully
      this.setState({ fee: fees, message: `Total Fee: ${fees}` });
    } catch (error) {
      // Error occurred while retrieving the total fees
      if (error.response && error.response.data && error.response.data.message) {
        this.setState({ message: error.response.data.message });
      } else {
        this.setState({ message: 'Failed to retrieve total fees' });
      }
    }
  };

  render() {
    const { associateId, message } = this.state;

    return (
      <div className="container">
        <div className="totalFees">
          <Admission />
          <br />
          <h3 className="text-center">Total Fee Details</h3>
         
          <form onSubmit={this.handleSubmit} className="form-inline justify-content-center">
            <div className="form-group mx-sm-3 mb-2">
              <label htmlFor="associateId">Associate ID:</label>
              <input
                type="text"
                id="associateId"
                name="associateId"
                className="form-control"
                style={{ width: '200px' }}
                value={associateId}
                onChange={this.handleInputChange}
              />
            </div>
            <button type="submit" className="btn btn-primary mb-2">
              Get Fees
            </button>
          </form>
          {message && <div className="message text-center">{message}</div>}
        </div>
      </div>
    );
  }
}

export default TotalFees;
