import React, { useState, useEffect } from 'react';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import userService from '../../Services/user.service.js';
import authService from '../../Services/auth.service.js';

function ResetPasswordRequest() {
    const [email, setEmail] = useState("");
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

    function handleResetPasswordRequest(event) {
        event.preventDefault();
    
        authService.resetPasswordRequest(email)
            .then(response => { 
                if(response.status === 200) {
                    alert("Password reset request sent. Please check your email.");
                }
            })
            .catch(error => {
                if (error.response.data) {
                    alert("Error: " + error.response.data);
                } else {
                    alert("An unexpected error occurred.");
                }
            });
    }
    


    return (
        <div className="flex flex-col   min-h-screen">
            <NavBar />
            <main className="flex-grow flex items-center justify-center">
                {publicContent === true ? (
                    <div className="container mx-auto max-w-md mt-10 border rounded-xl p-10 border-black shadow-xl">
                        <h1 className="text-3xl  mb-6">Reset Password</h1>
                        <form onSubmit={handleResetPasswordRequest}>
                            <input
                                className="w-full px-4 py-2 mb-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none"
                                type="email"
                                placeholder="Enter your email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                            />
                            <button
                                className="w-full px-4 py-2 bg-gray-200 text-black border-none rounded hover:bg-gray-400"
                                type="submit"
                            >
                                Reset Password
                            </button>
                        </form>
                        {message && (
                            <p className="mt-4 text-red-500">{message}</p>
                        )}
                    </div>
                ) : (
                    <div className="text-center mt-10">
                        {publicContent === false ? (
                            <p className="text-2xl text-red-500">{message}</p>
                        ) : (
                            <p>Loading...</p>
                        )}
                    </div>
                )}
            </main>
            <Footer />
        </div>
    );
}

export default ResetPasswordRequest;
