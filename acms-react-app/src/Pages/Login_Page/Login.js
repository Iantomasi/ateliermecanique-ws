import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faGoogle, faApple } from '@fortawesome/free-brands-svg-icons';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import { useNavigate } from 'react-router-dom';

function Login() {
    const navigate = useNavigate();

    const login = () => {
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
                <form className="text-center max-w-md mx-auto pb-16">
                    <input className="w-full h-16 px-4 my-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="email" placeholder="Enter your email" name="email" />
                    <input className="w-full h-16 px-4 my-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="password" placeholder="Enter your password" name="password" />
                    <a className="text-blue-500" href='#'>Forgot Password?</a>
                    <button className="w-full h-16 px-4 my-4 bg-gray-200 text-black border-none cursor-pointer transition duration-300 hover:bg-gray-400 rounded" type='submit'>Login</button>
                </form>
                <div className="text-center mb-8 mt-5">
                        <p>Or Login With</p>
                        <div className="flex justify-center mt-5">
                            <button className="border border-black bg-white p-4 pt-6 text-5xl mr-4 rounded w-60">
                                <FontAwesomeIcon icon={faFacebook} style={{ color: '#1877F2' }} />
                            </button>

                            <button className="border border-black bg-white p-4 pt-6 text-center text-5xl rounded w-60 flex items-center justify-center mr-4">
                                <img src="googleIcon.svg" alt="Google Icon" />
                            </button>

                            <button className="border border-black bg-white p-4 pt-6 text-5xl rounded w-60">
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
