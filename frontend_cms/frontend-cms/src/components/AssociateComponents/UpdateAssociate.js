import React, { Component } from 'react';
import AssociateService from '../../Services/AssociateService';
import Associate from './Associate';

class UpdateAssociate extends Component {
  constructor(props) {
    super(props);

    this.state = {
      associateId: '',
      associateAddress: '',
      message: ''
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = async (e) => {
    e.preventDefault();

    const { associateId, associateAddress } = this.state;

    // Validate input fields
    if (!associateId || !associateAddress) {
      this.setState({ message: 'Please fill in all fields' });
      return;
    }

    try {
      // Call the updateAssociate method of AssociateService to update the associate address
      await AssociateService.updateAssociate(associateId, associateAddress);

      // Associate address updated successfully
      this.setState({
        message: 'Associate address updated successfully',
        associateId: '',
        associateAddress: ''
      });
    } catch (error) {
      // Error occurred while updating the associate address
      if (error.response && error.response.data) {
        const errorMessage = error.response.data;
        this.setState({ message: errorMessage });
      } else {
        this.setState({ message: 'Failed to update associate address' });
      }
    }
  };

  render() {
    const { associateId, associateAddress, message } = this.state;

    return (
      <div className="updateAssociate">
        <Associate />
        <div className="container">
          <h3 className="text-center">Update Associate Information</h3>
          <form onSubmit={this.handleSubmit} className="form-container">
            <div className="form-group">
              <label htmlFor="associateId">Associate ID:</label>
              <input
                type="text"
                id="associateId"
                name="associateId"
                className="form-control"
                value={associateId}
                onChange={this.handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="associateAddress">Update Address:</label>
              <textarea
                id="associateAddress"
                name="associateAddress"
                className="form-control"
                rows="3"
                maxLength="50"
                value={associateAddress}
                onChange={this.handleInputChange}
              />
            </div>
            <button type="submit" className="btn btn-primary">Update Associate</button>
          </form>
          {message && <div className="message error">{message}</div>}
        </div>
      </div>
    );
  }
}

export default UpdateAssociate;
