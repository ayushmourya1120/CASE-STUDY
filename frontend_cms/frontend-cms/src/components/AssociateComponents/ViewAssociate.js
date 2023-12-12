import React, { Component } from 'react';
import AssociateService from '../../Services/AssociateService';
import Associate from './Associate';

class ViewAssociate extends Component {
  constructor(props) {
    super(props);

    this.state = {
      associateId: '',
      record: {},
      errorMessage: ''
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = async (e) => {
    e.preventDefault();

    const { associateId } = this.state;

    // Validate input fields
    if (!associateId) {
      this.setState({ errorMessage: 'Please enter an associate ID' });
      return;
    }

    try {
      // Call the viewAssociateById method of AssociateService to retrieve associate information
      const response = await AssociateService.viewAssociateById(associateId);
      const record = response.data;

      // Associate details retrieved successfully
      this.setState({ record, errorMessage: '' });
    } catch (error) {
      // Error occurred while retrieving associate details
      if (error.response && error.response.data) {
        const errorMessage = error.response.data;
        this.setState({ record: {}, errorMessage });
      } else {
        this.setState({ record: {}, errorMessage: 'Failed to retrieve associate details' });
      }
    }
  };

  render() {
    const { associateId, record, errorMessage } = this.state;

    return (
      <div className="viewAssociate">
        <Associate />
        <br />
        <div className="container text-center">
          <h3>View Associate by Associate ID</h3>
          <br />
          <form onSubmit={this.handleSubmit} className="form-container">
            <div className="form-inline justify-content-center">
              <label htmlFor="associateId" className="mr-2">Associate ID:</label>
              <input
                type="text"
                id="associateId"
                name="associateId"
                className="form-control form-control-sm mr-2" // Use form-control-sm class for smaller size
                value={associateId}
                onChange={this.handleInputChange}
              />
              <button type="submit" className="btn btn-primary">Get Details</button>
            </div>
          </form>

          {record.associateId && (
            <div className="associate-details">
              <h4>Associate Details</h4>
              <table className="table table-bordered">
                <thead>
                  <tr>
                    <th>Associate ID</th>
                    <th>Name</th>
                    <th>Address</th>
                    <th>Email ID</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>{record.associateId}</td>
                    <td>{record.associateName}</td>
                    <td>{record.associateAddress}</td>
                    <td>{record.associateEmailId}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          )}

          {errorMessage && <div className="message error">{errorMessage}</div>}
        </div>
      </div>
    );
  }
}

export default ViewAssociate;
