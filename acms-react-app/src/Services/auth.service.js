import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/auth/';

class AuthService {

    googleLogin(token){
        return axios.post(API_URL + 'google-login/' + token)
            .then(response => {
                if(response.data.token){
                    sessionStorage.setItem('user',JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    login(email,password){
        return axios
            .post(API_URL + 'signin',{
                email,
                password
            })
            .then(response => {
                if(response.data.token){
                    sessionStorage.setItem('user',JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    logout(){
        sessionStorage.removeItem('user');
        console.log("user logged out");
    }

    register(firstName,lastName,phoneNumber,email,password){
        return axios.post(API_URL + 'signup',{
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

}

export default new AuthService();