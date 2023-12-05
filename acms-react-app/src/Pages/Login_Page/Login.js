import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faGoogle, faApple } from '@fortawesome/free-brands-svg-icons';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import './Login.css';
import { useNavigate } from 'react-router-dom';

function Login() {
    
    
    const navigate = useNavigate()

    
    const login = () => {
        navigate('/admin');
    };

    //THIS IS JUST FOR NOW
    function handleSubmit(event) {
        event.preventDefault();
            login();
    }

    return (
        <div>
            <NavBar />
            <h1>Welcome Back!</h1>
            
            <form className="login-form" onSubmit={handleSubmit}>
                <input className="emailInput" type='email' placeholder='Enter your email' name='email' /><br/>
                <input className="passwordInput" type='password' placeholder='Enter your password' name='password' /><br/>
                <a className="passwordLink" href='#'>Forgot Password?</a><br/>
                <button className="logInButton" type='submit' value="Login">Login</button>
            </form>
            <div className="or-login-with">
                <p>Or Login With</p>
                <div className="login-icons">
                    <button className="login-icon-button" style={{ color: '#1877F2' }}>
                        <FontAwesomeIcon icon={faFacebook} />
                    </button>
                    <button className="login-icon-button">
                        <img src="googleIcon.svg" alt="Google Icon" /> {/* Use the imported SVG */}
                    </button>
                    <button className="login-icon-button">
                        <FontAwesomeIcon icon={faApple} />
                    </button>
                </div>
            </div>
            
            <Footer />
        </div>
    )
}

export default Login;
