import React, { useEffect, useRef } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faInstagram } from '@fortawesome/free-brands-svg-icons';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import { useNavigate } from 'react-router-dom';
import { LoginSocialFacebook, LoginSocialInstagram} from 'reactjs-social-login';
import { jwtDecode } from "jwt-decode";
import axios from 'axios';

function Login() {

    const google = window.google;
    const navigate = useNavigate();
    const googleButton = useRef(null);
    const REDIRECT_URI = window.location.href;

    useEffect(() => {
        google.accounts.id.initialize({
            client_id: process.env.REACT_APP_GOOGLE_CLIENT_ID || '',
            callback: handleGoogleLogin
        });
    }, []);

    useEffect(() => {
    
        const token = localStorage.getItem('userToken');

        if (token) {
            navigate('/admin');
        }
    }, [navigate]);

    const handleGoogleLogin = (response) => {

        localStorage.setItem('userToken', response.credential);
        localStorage.setItem('provider', 'google');
        const jwtToken = response.credential;

        //token has 3 parts, the kid is in the first part, each part is separated by a dot
        // const tokenParts = jwtToken.split('.');
        // const tokenHeader = JSON.parse(atob(tokenParts[1])); // Decoding base64url-encoded header --- This is the header
        // console.log(tokenHeader)

    
        axios.post(`http://localhost:8080/api/v1/auth/google-login/${jwtToken}`)
        .then(res => {
            console.log(res.data)
            res.data.roles === "CUSTOMER" ? navigate('/user') : navigate('/admin');
        })
        .catch(error => {
            console.error('Error loging in', error);
        })
          
        const userObject = jwtDecode(response.credential);
        console.log(userObject)
        localStorage.setItem('user', JSON.stringify(userObject));

        
    };

    const handleGoogleButtonClick = () => {
        google.accounts.id.prompt();
    };

    const handleFacebookLogin = (response) => {
        console.log(response)
        localStorage.setItem('userToken', response.data.accessToken);
        localStorage.setItem('provider', 'facebook');
        localStorage.setItem('user', JSON.stringify(response.data));

        axios.post(`http://localhost:8080/api/v1/auth/facebook-token-verification/${response.data.accessToken}`)
        .then(res => {
            console.log(res.data)
        })
        .catch(error => {
            console.error('Error loging in', error);
        })

        navigate('/admin');
    }

    const handleInstagramLogin = (response) => {
        console.log(response)
        localStorage.setItem('userToken', response.data.access_token);
        localStorage.setItem('provider', 'instagram');
        localStorage.setItem('user', JSON.stringify(response.data));
        

        navigate('/admin');
    }

    const login = (response) => {
        navigate('/admin');
    };

    function handleSubmit(event) {
        event.preventDefault();
        login();
    }

    return (
        <div className="flex flex-col min-h-screen">
            <NavBar />
            <main className="flex-grow flex flex-col justify-center">
                <h1 className="text-5xl font-bold m-10 text-center">Welcome Back!</h1>
                <form className="text-center max-w-md mx-auto pb-16" onSubmit={handleSubmit}>
                    <input className="w-full h-16 px-4 my-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="email" placeholder="Enter your email" name="email" />
                    <input className="w-full h-16 px-4 my-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="password" placeholder="Enter your password" name="password" />
                    <a className="text-blue-500" href='#'>Forgot Password?</a>
                    <button className="w-full h-16 px-4 my-4 bg-gray-200 text-black border-none cursor-pointer transition duration-300 hover:bg-gray-400 rounded" type='submit'>Login</button>
                </form>
                <div className="text-center mb-8 mt-5">
                        <p>Or Login With</p>
                        <div className="flex justify-center mt-5">

                            <LoginSocialFacebook
                            appId={process.env.REACT_APP_FACEBOOK_APP_ID || ''}
                            onResolve={handleFacebookLogin}
                            onReject={(error) => console.log(error)}
                            >
                            <button className="border border-black bg-white p-4 pt-6 text-5xl mr-4 rounded w-60 hover:scale-110 hover:cursor-pointer">
                                <FontAwesomeIcon icon={faFacebook} style={{ color: '#1877F2' }} />
                            </button>
                            </LoginSocialFacebook>

                            <div ref={googleButton} className=" hover:scale-110 hover:cursor-pointer border border-black bg-white p-4 pt-6 text-center text-5xl rounded w-60 flex items-center justify-center mr-4" onClick={handleGoogleButtonClick}>
                            <img src="/googleIcon.svg" alt="Google Icon"/>
                            </div>

                            <LoginSocialInstagram
                            appId={process.env.REACT_APP_INSTAGRAM_APP_ID || ''}
                            client_id={process.env.REACT_APP_INSTAGRAM_CLIENT_ID || ''}
                            client_secret={process.env.REACT_APP_INSTAGRAM_APP_SECRET || ''}
                            redirect_uri={REDIRECT_URI}
                            onResolve={handleInstagramLogin}
                            onReject={(error) => console.log(error)}>
                            <button className="border border-black bg-white p-4 pt-6 text-5xl rounded w-60 hover:scale-110 hover:cursor-pointer">
                                <FontAwesomeIcon icon={faInstagram} />
                            </button>
                            </LoginSocialInstagram>
                        </div>
                </div>
            </main>
            <Footer />
        </div>
    )
}

export default Login;
