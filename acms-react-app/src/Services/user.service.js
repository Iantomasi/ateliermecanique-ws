import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/v1/content/";

class userService{

    getPulicContent(){
        return axios.get(API_URL + 'all');
    }

    getUserBoard(){
        return axios.get(API_URL + 'user',{headers:authHeader()});
    }

    getAdminContent(){
        return axios.get(API_URL + 'admin',{headers:authHeader()});
    }
}

export default new userService();