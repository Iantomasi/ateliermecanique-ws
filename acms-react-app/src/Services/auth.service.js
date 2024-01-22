import axios from 'axios';

const API_URL = `${process.env.REACT_APP_BACKEND_URL || 'http://localhost:8080'}/api/v1/auth/`;

class AuthService {
    googleLogin(token){
        return axios.post(`${API_URL}google-login/${token}`)
            .then(this.handleResponse);
    }

    facebookLogin(token){
        return axios.post(`${API_URL}facebook-login/${token}`)
            .then(this.handleResponse);
    }

    login(email, password){
        return axios
            .post(`${API_URL}signin`, { email, password })
            .then(this.handleResponse);
    }

    logout(){
        sessionStorage.clear();
    }

    register(firstName, lastName, phoneNumber, email, password){
        return axios.post(`${API_URL}signup`, {
            firstName,
            lastName,
            phoneNumber,
            email,
            password
        });
    }

    getCurrentUser(){
        return JSON.parse(sessionStorage.getItem('user'));
    }

    handleResponse(response) {
        if (response.data.token) {
            sessionStorage.setItem('user', JSON.stringify(response.data));
        }
        return response.data;
    }
}

export default new AuthService();