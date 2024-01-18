import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/v1/content/";
const API_AVAILABILITY_URL = "http://localhost:8080/api/v1/availability/";
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

    getAvailabilities(date){
        return axios.get(API_AVAILABILITY_URL + `${date}`,{headers:authHeader()});
    }
}

export default new userService();