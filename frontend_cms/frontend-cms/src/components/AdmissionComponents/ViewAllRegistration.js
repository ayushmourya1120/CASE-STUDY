import React, { Component } from 'react';
import AdmissionService from '../../Services/AdmissionService';
import Admission from './Admission';

class ViewAllAdmissions extends Component {
  constructor(props) {
    super(props);

    this.state = {
      admissions: []
    };
  }

  componentDidMount() {
    this.fetchAdmissions();
  }

  fetchAdmissions = async () => {
    try {
      const response = await AdmissionService.viewAllAdmissions();
      const admissions = response.data;
      this.setState({ admissions });
    } catch (error) {
      console.log(error);
    }
  };

  render() {
    const { admissions } = this.state;

    return (
      <div className="container">
        <Admission />
        <h2 className="text-center">Admissions List</h2>
        <div className="row justify-content-center">
          <div className="col-8">
            <table className="table table-striped table-bordered">
              <thead>
                <tr>
                  <th>registrationId</th>
                  <th>courseId</th>
                  <th>associateId</th>
                  <th>fees</th>
                  <th>feedback</th>
                  
                </tr>
              </thead>
              <tbody>
                {admissions.map((admission) => (
                  <tr key={admission.registrationId}>
                    <td>{admission.registrationId}</td>
                    <td>{admission.courseId}</td>
                    <td>{admission.associateId}</td>
                    <td>{admission.fees}</td>
                    <td>{admission.feedback}</td>

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

export default ViewAllAdmissions;
