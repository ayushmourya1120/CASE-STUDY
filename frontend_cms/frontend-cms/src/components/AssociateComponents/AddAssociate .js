import React, { Component } from 'react';
import AssociateService from '../../Services/AssociateService';
import Associate from './Associate';

class AddAssociate extends Component {
  constructor(props) {
    super(props);

    this.state = {
      associateName: '',
      associateAddress: '',
      associateEmailId: '',
      message: '',
      isSuccess: false,
    };
  }

  handleInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = async (e) => {
    e.preventDefault();

    const { associateName, associateAddress, associateEmailId } = this.state;

    // Validate input fields
    if (!associateName || !associateAddress || !associateEmailId) {
      this.setState({ message: 'Please fill in all fields', isSuccess: false });
      return;
    }

    // Validate email format
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(associateEmailId)) {
      this.setState({ message: 'Invalid email format', isSuccess: false });
      return;
    }

    // Create associate object
    const associate = {
      associateName,
      associateAddress,
      associateEmailId
    };

    try {
      // Call the addAssociate method of AssociateService to add the associate details to DB
      await AssociateService.addAssociate(associate);

      // Associate added successfully
      this.setState({
        message: 'Associate has been added successfully',
        isSuccess: true,
        associateName: '',
        associateAddress: '',
        associateEmailId: '',
      });

      // Remove the message after 6 seconds
      setTimeout(() => {
        this.setState({ message: '', isSuccess: false });
      }, 6000);
    } catch (error) {
      // Error occurred while adding the associate
      this.setState({ message: 'Failed to add associate', isSuccess: false });
    }
  };

  render() {
    const { associateName, associateAddress, associateEmailId, message, isSuccess } = this.state;

    return (
      <div className="addAssociate">
        <Associate />
        <div className="container">
          <h3 className="text-center">Add Associate Details</h3>
          <form onSubmit={this.handleSubmit} className="form-container">
            <div className="form-group row">
              <label htmlFor="associateName" className="col-sm-3 col-form-label mb-2">Name:</label>
              <div className="col-sm-6">
                <input
                  type="text"
                  id="associateName"
                  name="associateName"
                  className="form-control"
                  value={associateName}
                  onChange={this.handleInputChange}
                />
              </div>
            </div>
            <div className="form-group row">
              <label htmlFor="associateAddress" className="col-sm-3 col-form-label mb-2">Address:</label>
              <div className="col-sm-6">
                <input
                  type="text"
                  id="associateAddress"
                  name="associateAddress"
                  className="form-control"
                  value={associateAddress}
                  onChange={this.handleInputChange}
                />
              </div>
            </div>
            <div className="form-group row">
              <label htmlFor="associateEmailId" className="col-sm-3 col-form-label mb-2">Email ID:</label>
              <div className="col-sm-6">
                <input
                  type="text"
                  id="associateEmailId"
                  name="associateEmailId"
                  className="form-control"
                  value={associateEmailId}
                  onChange={this.handleInputChange}
                />
              </div>
            </div>
            <div className="text-center">
              <button type="submit" className="btn btn-primary">
                Add Associate
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

export default AddAssociate;
