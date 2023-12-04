import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faGoogle, faApple } from '@fortawesome/free-brands-svg-icons';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import './signup.css';
import axios from 'axios';


function Signup(){

    function signup(user){
        axios.post("http://localhost:8080/registration",user)
        .then(res=>{
            if(res.status === 201){
                console.log("User has successfully signed up!")
            }
        })
        .catch(err => console.log(err))
    }

    function handleSubmit(event) {
        event.preventDefault();

        const { firstName, lastName, username, email, phoneNumber, password, confirmPassword } = event.target;

        if (password.value === confirmPassword.value) {
            const user = {
                firstName: firstName.value,
                lastName: lastName.value,
                username: username.value,
                email: email.value,
                phoneNumber: phoneNumber.value,
                password: password.value
            };

            console.log(user);
            signup(user);
        } else {
            alert("Passwords don't match!");
        }
    }

    return(
        <div>
            <NavBar />
            <h1>Sign Up</h1>
            <form className="login-form" 
            onSubmit={handleSubmit}>
                <input id="firstName" type="text" name="firstName" placeholder="First Name"  required />
                <input id="lastName" type="text" name="lastName" placeholder="Last Name"  required />
                <input id="username" type="text" name="username" placeholder="Username"  required autoFocus="autofocus" />
                <input type="email" name="email" placeholder="Email"  required />
                <input type="text" name="phoneNumber" placeholder="Phone Number"  required />
                <input type="password" name="password" placeholder="Password"  required />
                <input type="password" name="confirmPassword" placeholder="Confirm password" required />
                <button className="subButton" type="submit">Register</button>
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
export default Signup;
