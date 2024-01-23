import axios from "axios";
import authHeader from "./auth-header";

const API_BASE_URL = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080";
const API_CUSTOMERS_URL = `${API_BASE_URL}/api/v1/customers`;

class customerService {

    updateCustomer(id, customer){
        return axios.put(`${API_CUSTOMERS_URL}/${id}`, customer, { headers: authHeader() });
    }

}

export default new customerService();