import axios from "axios";
import authHeader from "./auth-header";

const API_CUSTOMERS_URL = "http://localhost:8080/api/v1/customers";

class customerService{

    updateCustomer(id, customer){
        return axios.put(API_CUSTOMERS_URL + `/${id}`, customer, { headers: authHeader() });
    }

}

export default new customerService();