import axios from "axios";
import authHeader from "./auth-header";

const API_BASE_URL = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080";
const API_CUSTOMERS_URL = `${API_BASE_URL}/api/v1/customers`;
const API_CUSTOMERS_URL_2 = `${API_BASE_URL}/api/v1/reviews/customer`;
const API_AVAILABILITY_URL = `${API_BASE_URL}/api/v1/availability/`;
const API_REVIEWS_URL = `${API_BASE_URL}/api/v1/reviews`;
class customerService {

    updateCustomer(id, customer){
        return axios.put(`${API_CUSTOMERS_URL}/${id}`, customer, { headers: authHeader() });
    }

    getAvailabilities(date){
        return axios.get(`${API_AVAILABILITY_URL}${date}`, { headers: authHeader() });
    }

    addReview(review){
        return axios.post(API_REVIEWS_URL , review, { headers: authHeader() });
    }
    getCustomerAppointmentById(customerId, appointmentId){
        return axios.get(API_CUSTOMERS_URL + `/${customerId}/appointments/${appointmentId}`, { headers: authHeader() });
    }
    updateCustomerAppointmentStatus(customerId, appointmentId, isConfirmed) {
        return axios.put(API_CUSTOMERS_URL + `/${customerId}/appointments/${appointmentId}/updateStatus?isConfirm=${isConfirmed}`,null,{ headers: authHeader() });
    }
    updateCustomerAppointment(customerId, appointmentId, appointment){
        return axios.put(API_CUSTOMERS_URL + `/${customerId}/appointments/${appointmentId}`, appointment, { headers: authHeader() });
    }
    deleteCustomerAppointment(customerId, appointmentId){
        return axios.delete(API_CUSTOMERS_URL + `/${customerId}/appointments/${appointmentId}`, { headers: authHeader() });
    }

    getCustomerInvoieById(customerId, invoiceId){
        return axios.get(API_CUSTOMERS_URL + `/${customerId}/invoices/${invoiceId}`, { headers: authHeader() });
    }
    getCustomerReviews(customerId){
        return axios.get(API_CUSTOMERS_URL_2 + `/${customerId}`, { headers: authHeader() });
    }
    

}

export default new customerService();