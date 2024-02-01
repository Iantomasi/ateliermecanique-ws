import axios from "axios";
import authHeader from "./auth-header";

const API_BASE_URL = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080";
const API_CUSTOMERS_URL = `${API_BASE_URL}/api/v1/customers`;
const API_AVAILABILITY_URL = `${API_BASE_URL}/api/v1/availability/`;

class customerService {

    updateCustomer(id, customer){
        return axios.put(`${API_CUSTOMERS_URL}/${id}`, customer, { headers: authHeader() });
    }

    getAvailabilities(date){
        return axios.get(`${API_AVAILABILITY_URL}${date}`, { headers: authHeader() });
    }

}

export default new customerService();