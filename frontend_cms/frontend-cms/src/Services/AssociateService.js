import axios from 'axios';

const ASSOCIATE_API_BASE_URL = 'http://localhost:7777/associate';

class AssociateService {
  constructor() {
    this.apiBaseUrl = ASSOCIATE_API_BASE_URL;
  }

  addAssociate(associate) {
    const url = `${this.apiBaseUrl}/addAssociate`;
    return axios.post(url, associate);
  }

  updateAssociate(associateId, address) {
    const url = `${this.apiBaseUrl}/updateAssociate/${associateId}/${address}`;
    return axios.put(url);
  }
  

  viewAssociateById(associateId) {
    const url = `${this.apiBaseUrl}/viewByAssociateId/${associateId}`;
    return axios.get(url);
  }

  viewAllAssociates() {
    const url = `${this.apiBaseUrl}/viewAll`;
    return axios.get(url);
  }

}

const associateService = new AssociateService();
export default associateService;
