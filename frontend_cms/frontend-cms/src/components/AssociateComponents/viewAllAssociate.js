import React, { Component } from 'react';
import AssociateService from '../../Services/AssociateService';
import Associate from './Associate';

class ViewAllAssociates extends Component {
  constructor(props) {
    super(props);

    this.state = {
      associates: []
    };
  }

  componentDidMount() {
    this.fetchAssociates();
  }

  fetchAssociates = async () => {
    try {
      const response = await AssociateService.viewAllAssociates();
      const associates = response.data;
      this.setState({ associates });
    } catch (error) {
      console.log(error);
    }
  };

  render() {
    const { associates } = this.state;

    return (
      <div className="container">
        <Associate />
        <h2 className="text-center">Associates List</h2>
        <div className="row justify-content-center">
          <div className="col-8">
            <table className="table table-striped table-bordered">
              <thead>
                <tr>
                  <th>Associate ID</th>
                  <th>Name</th>
                  <th>Address</th>
                  <th>Email ID</th>
                </tr>
              </thead>
              <tbody>
                {associates.map((associate) => (
                  <tr key={associate.associateId}>
                    <td>{associate.associateId}</td>
                    <td>{associate.associateName}</td>
                    <td>{associate.associateAddress}</td>
                    <td>{associate.associateEmailId}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    );
  }
}

export default ViewAllAssociates;
