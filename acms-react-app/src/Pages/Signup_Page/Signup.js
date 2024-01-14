import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faGoogle, faApple } from '@fortawesome/free-brands-svg-icons';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import { useNavigate } from 'react-router-dom';
import authService from '../../Services/auth.service.js';
import userService from '../../Services/user.service.js';

function Signup() {
    
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName,setLastName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [publicContent, setPublicContent] = useState(null);
    const [message, setMessage] = useState('');

    useEffect(() => {
      userService.getPulicContent()
        .then(response => {
          if(response.data === "Public Content.") {
            setPublicContent(true);
          } 
        })
        .catch(error => {
          console.error('Error fetching public content:', error);
          setPublicContent(false);
          setMessage(error.response.data);
        });
    }, []); 

    useEffect(() => {
      
        const currentUser = authService.getCurrentUser();
    
        if (currentUser) {
          const userRoles = currentUser.roles;
    
          if (userRoles.includes('ROLE_CUSTOMER')) {
            navigate('/user');
          } else if (userRoles.includes('ROLE_ADMIN')) {
            navigate('/admin');
          }
         
        }
      }, []);

    function handleSignup(event) {
        event.preventDefault();

        if (password !== confirmPassword) {
            alert("Passwords do not match");
            return;
        }

        authService.register(firstName,lastName,phoneNumber,email,password).then(
            (response) => {
                const message =response.data.message;
                alert(message);
                navigate("/login");
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
            <main className="flex-grow">
            {publicContent === true ? (
                <div>
                    <h1 className="text-5xl font-bold m-10 text-center">Sign Up</h1>
                    <form className="max-w-md mx-auto grid gap-4 grid-cols-1 sm:grid-cols-2" onSubmit={handleSignup}>
                        <input className="h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="text" name="firstName" placeholder="First Name" required value={firstName} onChange={(e) => setFirstName(e.target.value)}/>
                        <input className="h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="text" name="lastName" placeholder="Last Name" required value={lastName} onChange={(e) => setLastName(e.target.value)}/>
                        <input className="col-span-2 h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="email" name="email" placeholder="Email" required value={email} onChange={(e) => setEmail(e.target.value)} />
                        <input className="col-span-2 h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="text" name="phoneNumber" placeholder="Phone Number" required  value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)}/>
                        <input className="col-span-1 h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="password" name="password" placeholder="Password" required value={password} onChange={(e) => setPassword(e.target.value)}/>
                        <input className="col-span-1 h-16 px-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="password" name="confirmPassword" placeholder="Confirm password" required value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} />
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
                </div>) : (
                    <div className="text-center">
                    {publicContent === false ? <h1 className='text-4xl'>{message.status} {message.error} </h1> : 'Error'}
                    {message && (
                      <>
                        <h3>{message.message}</h3>
                      </>
                    )}
                  </div>
                )}
            </main> 
            <Footer />
        </div>
    )
}
export default Signup;
