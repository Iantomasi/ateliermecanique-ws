import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faGoogle, faApple } from '@fortawesome/free-brands-svg-icons';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import './signup.css';



function Signup(){

    return(
        <div>
            <NavBar />
            <h1>Sign Up</h1>
            <form className="login-form">
                <input id="firstName" type="text" name="firstName" placeholder="First Name"  required />
                <input id="lastName" type="text" name="lastName" placeholder="Last Name"  required />
                <input id="username" type="text" name="username" placeholder="Username"  required autoFocus="autofocus" />
                <input type="email" name="email" placeholder="Email"  required />
                <input type="text" name="phoneNumber" placeholder="Phone Number"  required />
                <input type="password" name="password" placeholder="Password"  required />
                <input type="password" name="confirm-password" placeholder="Confirm password" required />

            </form>
            <div className="or-login-with">
                <p>Or Sign Up With</p>
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
export default Signup;
