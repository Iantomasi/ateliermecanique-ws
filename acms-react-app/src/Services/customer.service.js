import axios from "axios";
import authHeader from "./auth-header";

const API_BASE_URL = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080";
const API_CUSTOMERS_URL = `${API_BASE_URL}/api/v1/customers`;
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

    // reviews (good practice but maybe redundant in this case?)

    /*
    getAllReviews(){
        return axios.get(API_REVIEWS_URL, { headers: authHeader() });
    }

    getReviewById(id){
        return axios.get(API_REVIEWS_URL + /${id}, { headers: authHeader() });
    }
    deleteReviewById(id){
        return axios.delete(API_REVIEWS_URL + /${id}, { headers: authHeader() });

    }
    */

}

export default new customerService();