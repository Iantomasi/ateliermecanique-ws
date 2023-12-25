import React, { useEffect, useRef } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faGoogle, faApple } from '@fortawesome/free-brands-svg-icons';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import { useNavigate } from 'react-router-dom';
import { LoginSocialFacebook } from 'reactjs-social-login';
import { jwtDecode } from "jwt-decode";

function Login() {

    const google = window.google;
    const navigate = useNavigate();
    const googleButton = useRef(null);

    useEffect(() => {
        google.accounts.id.initialize({
            client_id: "539616712207-be30bib3kb2fgvahdol0l6uvv26ra2u6.apps.googleusercontent.com",
            callback: handleGoogleLogin,
            scope: "https://www.googleapis.com/auth/userinfo.profile"
        });
    }, []);

    useEffect(() => {
    
        const token = localStorage.getItem('userToken');

        if (token) {
            navigate('/admin');
        }
    }, [navigate]);

    const handleGoogleLogin = (response) => {
        console.log(response);
        alert("Encoded JWT ID Token: " + response.credential);
        localStorage.setItem('userToken', response.credential);
        localStorage.setItem('provider', 'google');
        const userObject = jwtDecode(response.credential);
        localStorage.setItem('user', JSON.stringify(userObject));
        console.log(localStorage.getItem('user'))
        navigate('/admin');
    };

    const handleGoogleButtonClick = () => {
        google.accounts.id.prompt();
    };

    const handleFacebookLogin = (response) => {
        console.log(response);
        localStorage.setItem('userToken', response.data.accessToken);
        localStorage.setItem('provider', 'facebook');
        localStorage.setItem('user', JSON.stringify(response.data));
        console.log(localStorage.getItem('user'))
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
                            appId='323237383960670'
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

                            <button className="border border-black bg-white p-4 pt-6 text-5xl rounded w-60 hover:scale-110 hover:cursor-pointer">
                                <FontAwesomeIcon icon={faApple} />
                            </button>
                        </div>
                </div>
            </main>
            <Footer />
        </div>
    )
}

export default Login;
