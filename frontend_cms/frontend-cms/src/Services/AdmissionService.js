import axios from 'axios';

const ADMISSION_API_BASE_URL = 'http://localhost:7777/admission';

class AdmissionService {
  constructor() {
    this.apiBaseUrl = ADMISSION_API_BASE_URL;
  }

  registerAssociateForCourse(associateId, courseId, admission) {
    const url = `${this.apiBaseUrl}/register/${associateId}/${courseId}`;
    return axios.post(url, admission);
  }

  calculateFees(associateId) {
    const url = `${this.apiBaseUrl}/calculateFees/${associateId}`;
    return axios.put(url);
  }

  addFeedback(regNo, feedback, feedbackRating) {
    const url = `${this.apiBaseUrl}/feedback/${encodeURIComponent(regNo)}/${encodeURIComponent(feedback)}/${feedbackRating}`;
    return axios.post(url);
  }

  getHighestFeeCourse(associateId) {
    const url = `${this.apiBaseUrl}/highestFee/${associateId}`;
    return axios.get(url);
  }

  viewFeedbackByCourseId(courseId) {
    const url = `${this.apiBaseUrl}/viewFeedbackByCourseId/${courseId}`;
    return axios.get(url);
  }

  deactivateAdmissions(courseId) {
    const url = `${this.apiBaseUrl}/deactivate/${courseId}`;
    return axios.delete(url);
  }

  makePayment(registrationId) {
    const url = `${this.apiBaseUrl}/makePayment/${registrationId}`;
    return axios.post(url);
  }

  viewAllAdmissions() {
    const url = `${this.apiBaseUrl}/viewAll`;
    return axios.get(url);
  }
}

const admissionService = new AdmissionService();
export default admissionService;
