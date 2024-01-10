import React, { useEffect, useRef, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faInstagram } from '@fortawesome/free-brands-svg-icons';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import { useNavigate } from 'react-router-dom';
import { LoginSocialFacebook, LoginSocialInstagram} from 'reactjs-social-login';
import { jwtDecode } from "jwt-decode";
import axios from 'axios';
import authService from '../../Services/auth.service.js';

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const google = window.google;
    const navigate = useNavigate();
    const googleButton = useRef(null);
    const REDIRECT_URI = window.location.href;

    const [validation, setValidation] = useState("");

    useEffect(() => {
        google.accounts.id.initialize({
            client_id: process.env.REACT_APP_GOOGLE_CLIENT_ID || '',
            callback: handleGoogleLogin
        });
    }, []);

    useEffect(() => {
        const token = sessionStorage.getItem('userToken');
        const provider = sessionStorage.getItem('provider');
    
        if (token) {
            if (provider === 'google') {
                axios.get(`http://localhost:8080/api/v1/auth/google-token-verification/${token}`)
                    .then(res => {
                        setValidation(res.data);
                    })
                    .catch(error => {
                        console.error('Error logging in', error);
                    });
            } else if (provider === 'facebook') {
                axios.get(`http://localhost:8080/api/v1/auth/facebook-token-verification/${token}`)
                    .then(res => {
                        setValidation(res.data); 
                    })
                    .catch(error => {
                        console.error('Error logging in', error);
                    });
            } else if (provider === 'instagram') {
                axios.get(`http://localhost:8080/api/v1/auth/instagram-token-verification/${token}`)
                    .then(res => {
                        setValidation(res.data); 
                    })
                    .catch(error => {
                        console.error('Error logging in', error);
                    });
            }
        }
    }, [sessionStorage.getItem('userToken'), sessionStorage.getItem('provider')]);
    
    useEffect(() => {
        if (validation === "Token is valid and not expired.") {
            const token = sessionStorage.getItem('userToken');
    
            axios.get(`http://localhost:8080/api/v1/auth/${token}`)
                .then(res => {
                    res.data.role === "CUSTOMER" ? navigate('/user') : navigate('/admin');
                })
                .catch(error => {
                    console.error('Error logging in', error);
                });
        }
    }, [validation, navigate]);
    

    const handleGoogleLogin = (response) => {

        sessionStorage.setItem('userToken', response.credential);
        sessionStorage.setItem('provider', 'google');
        const jwtToken = response.credential;

        //token has 3 parts, the kid is in the first part, each part is separated by a dot
        // const tokenParts = jwtToken.split('.');
        // const tokenHeader = JSON.parse(atob(tokenParts[0])); // Decoding base64url-encoded header --- This is the header
        // console.log(tokenHeader)

    
        axios.post(`http://localhost:8080/api/v1/auth/google-login/${jwtToken}`)
        .then(res => {
            res.data.role === "CUSTOMER" ? navigate('/user') : navigate('/admin');
        })
        .catch(error => {
            console.error('Error loging in', error);
        })
          
        const userObject = jwtDecode(response.credential);
        sessionStorage.setItem('user', JSON.stringify(userObject));

        
    };

    const handleGoogleButtonClick = () => {
        google.accounts.id.prompt();
    };

    const handleFacebookLogin = (response) => {
        sessionStorage.setItem('userToken', response.data.accessToken);
        sessionStorage.setItem('provider', 'facebook');
        sessionStorage.setItem('user', JSON.stringify(response.data));

        const userAccess = {
            firstName: response.data.first_name,
            lastName: response.data.last_name,
            email: response.data.email,
            token: response.data.accessToken
          }
        axios.post('http://localhost:8080/api/v1/auth/facebook-login', userAccess)
        .then(res => {
            res.data.role === "CUSTOMER" ? navigate('/user') : navigate('/admin');
        })
        .catch(error => {
            console.error('Error loging in', error);
        })

        navigate('/admin');
    }

    const handleInstagramLogin = (response) => {

        sessionStorage.setItem('userToken', response.data.access_token);
        sessionStorage.setItem('provider', 'instagram');
        sessionStorage.setItem('user', JSON.stringify(response.data));
        
        //This is just for now, we need to get our app verified by instagram in order to request for the user's email, first name and last name.
        const userAccess = {
            token: response.data.access_token
          }

         axios.post('http://localhost:8080/api/v1/auth/instagram-login', userAccess)
        .then(res => {
            res.data.role === "CUSTOMER" ? navigate('/user') : navigate('/admin');
        })
        .catch(error => {
            console.error('Error loging in', error);
        })
    }

    function handleLogin(event) {
        event.preventDefault();
        authService.login(email, password).then(
            () => {
                navigate("/admin");
            },
            (error) => {
                const resMessage =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                alert(resMessage);
            }
        );
    }

    return (
        <div className="flex flex-col min-h-screen">
            <NavBar />
            <main className="flex-grow flex flex-col justify-center">
                <h1 className="text-5xl font-bold m-10 text-center">Welcome Back!</h1>
                <form className="text-center max-w-md mx-auto pb-16" onSubmit={handleLogin}>
                    <input className="w-full h-16 px-4 my-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="email" placeholder="Enter your email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                    <input className="w-full h-16 px-4 my-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="password" placeholder="Enter your password"  value={password} onChange={(e) => setPassword(e.target.value)} required />
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
