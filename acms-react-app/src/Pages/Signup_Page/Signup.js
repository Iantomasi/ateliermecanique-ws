import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faGoogle, faApple } from '@fortawesome/free-brands-svg-icons';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import axios from 'axios';

function Signup() {
    function signup(user) {
        axios.post("http://localhost:8080/api/v1/registration", user)
            .then(res => {
                if (res.status === 201) {
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

    return (
        <div className="flex flex-col min-h-screen">
            <NavBar />
            <main className="flex-grow">
                <h1 className="text-5xl font-bold m-10 text-center">Sign Up</h1>
                <form className="max-w-md mx-auto grid gap-4 grid-cols-1 sm:grid-cols-2" onSubmit={handleSubmit}>
                    <input className="h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="text" name="firstName" placeholder="First Name" required />
                    <input className="h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="text" name="lastName" placeholder="Last Name" required />
                    <input className="col-span-2 h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="text" name="username" placeholder="Username" required autoFocus />
                    <input className="col-span-2 h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="email" name="email" placeholder="Email" required />
                    <input className="col-span-2 h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="text" name="phoneNumber" placeholder="Phone Number" required />
                    <input className="col-span-1 h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="password" name="password" placeholder="Password" required />
                    <input className="col-span-1 h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="password" name="confirmPassword" placeholder="Confirm password" required />
                    <button className="col-span-2 h-16 px-4 bg-gray bg-gray-200 text-black border-none cursor-pointer transition duration-300 hover:bg-gray-400" type="submit">Register</button>
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
export default Signup;
