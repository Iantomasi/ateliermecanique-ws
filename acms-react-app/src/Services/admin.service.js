import axios from "axios";
import authHeader from "./auth-header";

const API_CUSTOMERS_URL = "http://localhost:8080/api/v1/customers";

class adminService{

    getAllCustomers(){
        return axios.get(API_CUSTOMERS_URL, { headers: authHeader() });
    }

    getCustomerById(id){
        return axios.get(API_CUSTOMERS_URL + `/${id}`, { headers: authHeader() });
    }

    updateCustomer(id, customer){
        return axios.put(API_CUSTOMERS_URL + `/${id}`, customer, { headers: authHeader() });
    }

    deleteCustomer(id){
        return axios.delete(API_CUSTOMERS_URL + `/${id}`, { headers: authHeader() });
    }
}

export default new adminService();