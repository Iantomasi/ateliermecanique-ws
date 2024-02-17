export default function authHeader(){
    
    const user = JSON.parse(sessionStorage.getItem('user'));
    const token = sessionStorage.getItem('token');

    if(user && user.token){
        return {Authorization: 'Bearer ' + user.token};
    } else if(token){
        return {Authorization: 'Bearer ' + token};
    } else{
        return {};
    }
}