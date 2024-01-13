import axios from "axios";
import authHeader from "./auth-header";

const API_CUSTOMERS_URL = "http://localhost:8080/api/v1/customers";
const API_APPOINTMENTS_URL = "http://localhost:8080/api/v1/appointments";

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

    //customer vehicles

    getAllCustomerVehicles(id){
        return axios.get(API_CUSTOMERS_URL + `/${id}/vehicles`, { headers: authHeader() });
    }

    getCustomerVehicleById(customerId, vehicleId){
        return axios.get(API_CUSTOMERS_URL + `/${customerId}/vehicles/${vehicleId}`, { headers: authHeader() });
    }

    addCustomerVehicle(customerId, vehicle){
        return axios.post(API_CUSTOMERS_URL + `/${customerId}/vehicles`, vehicle, { headers: authHeader() });
    }
    
    updateCustomerVehicle(customerId, vehicleId, vehicle){
        return axios.put(API_CUSTOMERS_URL + `/${customerId}/vehicles/${vehicleId}`, vehicle, { headers: authHeader() });
    }

    deleteCustomerVehicle(customerId, vehicleId){
        return axios.delete(API_CUSTOMERS_URL + `/${customerId}/vehicles/${vehicleId}`, { headers: authHeader() });
    }

    //customer appointments

    getAllCustomerAppointments(id){
        return axios.get(API_CUSTOMERS_URL + `/${id}/appointments`, { headers: authHeader() });
    }
    // Appointments

    getAllAppointments(){
        return axios.get(API_APPOINTMENTS_URL, { headers: authHeader() });
    }

    getAppointmentById(id){
        return axios.get(API_APPOINTMENTS_URL + `/${id}`, { headers: authHeader() });
    }

    updateAppointmentStatus(id, isConfirmed) {
        return axios.put(
            `${API_APPOINTMENTS_URL}/${id}/updateStatus?isConfirm=${isConfirmed}`,null,{ headers: authHeader() });
    }

    deleteAllCancelledAppointments(){
        return axios.delete(API_APPOINTMENTS_URL + `/cancelled`, { headers: authHeader() });
    }

}

export default new adminService();