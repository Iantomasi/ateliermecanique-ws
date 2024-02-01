import axios from "axios";
import authHeader from "./auth-header";

const API_BASE_URL = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080";
const API_CONTENT_URL = `${API_BASE_URL}/api/v1/content/`;

class userService {

    getPulicContent(){
        return axios.get(`${API_CONTENT_URL}all`);
    }

    getUserBoard(){
        return axios.get(`${API_CONTENT_URL}user`, { headers: authHeader() });
    }

    getAdminContent(){
        return axios.get(`${API_CONTENT_URL}admin`, { headers: authHeader() });
    }

}

export default new userService();