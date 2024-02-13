import axios from "axios";
import authHeader from "./auth-header";

const API_BASE_URL = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080";

const API_CUSTOMERS_URL = `${API_BASE_URL}/api/v1/customers`;
const API_APPOINTMENTS_URL = `${API_BASE_URL}/api/v1/appointments`;
const API_INVOICES_URL = `${API_BASE_URL}/api/v1/invoices`
const API_REVIEWS_URL = `${API_BASE_URL}/api/v1/reviews`

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

    addAppointment(customerId,appointment){
        return axios.post(API_CUSTOMERS_URL + `/${customerId}/appointments`, appointment, { headers: authHeader() });
    }
    getAppointmentById(id){
        return axios.get(API_APPOINTMENTS_URL + `/${id}`, { headers: authHeader() });
    }

    updateAppointmentStatus(id, isConfirmed) {
        return axios.put(
            `${API_APPOINTMENTS_URL}/${id}/updateStatus?isConfirm=${isConfirmed}`,null,{ headers: authHeader() });
    }

    updateAppointment(id, appointment){
        return axios.put(API_APPOINTMENTS_URL + `/${id}`, appointment, { headers: authHeader() });
    }

    deleteAllCancelledAppointments(){
        return axios.delete(API_APPOINTMENTS_URL + `/cancelled`, { headers: authHeader() });
    }

    deleteAppointment(id){
        return axios.delete(API_APPOINTMENTS_URL + `/${id}`, { headers: authHeader() });
    }


    // invoices

    getAllInvoices(){
        return axios.get(API_INVOICES_URL, { headers: authHeader() });
    }
    updateInvoice(id, invoice){
        return axios.put(API_INVOICES_URL + `/${id}`, invoice, { headers: authHeader() });
    }
    getAllCustomerInvoices(id){
        return axios.get(API_CUSTOMERS_URL + `/${id}/invoices`, { headers: authHeader() });
    }
    addInvoice(customerId,invoice){
        return axios.post(API_CUSTOMERS_URL + `/${customerId}/invoices`, invoice, { headers: authHeader() });
    }
    getInvoiceById(id){
        return axios.get(API_INVOICES_URL + `/${id}`, { headers: authHeader() });
    }
    deleteInvoice(id){
        return axios.delete(API_INVOICES_URL + `/${id}`, { headers: authHeader() });
    }


    //reviews
    getAllReviews(){
        return axios.get(API_REVIEWS_URL, { headers: authHeader() });
    }

    getReviewById(id){
        return axios.get(API_REVIEWS_URL + `/${id}`, { headers: authHeader() });
    }

    updateReview(id, review){
        return axios.put(API_REVIEWS_URL + `/${id}`, review, { headers: authHeader() });
    }

}

export default new adminService();