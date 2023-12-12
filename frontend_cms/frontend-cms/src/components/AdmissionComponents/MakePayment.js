import React, { Component } from 'react';
import AdmissionService from '../../Services/AdmissionService';
import Admission from '../AdmissionComponents/Admission';

class MakePayment extends Component {
  constructor(props) {
    super(props);

    this.state = {
      registrationId: '',
      message: '',
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = (e) => {
    e.preventDefault();

    const { registrationId } = this.state;

    // Validate input field
    if (!registrationId) {
      this.setState({ message: 'Please enter Registration Id' });
      return;
    }
 // Call the makePayment method of AdmissionService
  // to make payment for the entered registration id
  AdmissionService.makePayment(registrationId)
    .then((res) => {
      // Payment successful
      const { data: redirectUrl } = res;
      if (redirectUrl) {
        window.location.href = redirectUrl; // Redirect the user to the payment gateway page
      } else {
        this.setState({ message: 'Failed to get redirect URL' });
      }
    })
    .catch((error) => {
      // Error occurred while making payment
      this.setState({ message: 'Failed to make payment' });
    });
};


  render() {
    const { registrationId, message } = this.state;

    return (
      <div className="container">
        <Admission />
        <div className="makePayment text-center">
          <h3>Make Payment for Registered Course</h3>
          <br />
          <form onSubmit={this.handleSubmit}>
            <div className="form-group row align-items-center">
              <label htmlFor="registrationId" className="col-sm-2 col-form-label">
                Registration Id:
              </label>
              <div className="col-sm-10">
                <input
                  type="text"
                  id="registrationId"
                  name="registrationId"
                  className="form-control form-control-sm"
                  value={registrationId}
                  onChange={this.handleInputChange}
                />
              </div>
            </div>
            <div className="text-center">
              <button type="submit" className="btn btn-primary">
                Pay Now
              </button>
            </div>
          </form>

          {message && (
            <div className="message text-center">
              {message}
            </div>
          )}
        </div>
      </div>
    );
  }
}

export default MakePayment;
