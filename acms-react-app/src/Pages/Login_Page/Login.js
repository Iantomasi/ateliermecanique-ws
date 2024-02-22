import React, { useEffect, useRef, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook} from '@fortawesome/free-brands-svg-icons';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import { useNavigate } from 'react-router-dom';
import { LoginSocialFacebook} from 'reactjs-social-login';
import userService from '../../Services/user.service.js';
import authService from '../../Services/auth.service.js';
import sha256 from 'crypto-js/sha256';

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const google = window.google;
    const navigate = useNavigate();
    const googleButton = useRef(null);


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

    useEffect(() => {
        google.accounts.id.initialize({
            client_id: process.env.REACT_APP_GOOGLE_CLIENT_ID || '',
            callback: handleGoogleLogin
        });
    }, []);


    const handleGoogleLogin = (response) => {
        const jwtToken = response.credential;
    
        authService.googleLogin(jwtToken)
            .then(res => {
                const userRoles = res.roles;
    
                if (userRoles.includes('ROLE_CUSTOMER')){
                    navigate('/user');
                }
                else {
                    alert("User doesn't have required roles.");
                }
            })
            .catch(error => {
                console.error('Error logging in', error);
            });
    };

    const handleGoogleButtonClick = () => {
        google.accounts.id.prompt();
    };

    const handleFacebookLogin = (response) => {
        const token = response.data.accessToken;

        authService.facebookLogin(token)
        .then(res => {
                const userRoles = res.roles;
    
                if (userRoles.includes('ROLE_CUSTOMER')){
                    navigate('/user');
                }
                else {
                    alert("User doesn't have required roles.");
                }
            })
            .catch(error => {
                console.error('Error logging in', error);
            });
    }


    function handleLogin(event) {
        event.preventDefault();
        
        const hashedPassword = sha256(password).toString();

        authService.login(email, hashedPassword).then(
            (res) => {
                const userRoles = res.roles;
    
                if (userRoles.includes('ROLE_CUSTOMER')){
                    navigate('/user');
                } else if(userRoles.includes('ROLE_ADMIN')){
                    navigate('/admin');
                }
                else {
                    alert("User doesn't have required roles.");
                }
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
            {publicContent === true ? (
              <div>
                <h1 className="text-5xl font-bold m-10 text-center">Welcome Back!</h1>
                <form className="text-center max-w-md mx-auto pb-16" onSubmit={handleLogin}>
                  <input className="w-full h-16 px-4 my-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="email" placeholder="Enter your email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                  <input className="w-full h-16 px-4 my-4 rounded border bg-gray-100 focus:border-gray-500 focus:outline-none" type="password" placeholder="Enter your password"  value={password} onChange={(e) => setPassword(e.target.value)} required />
                  <a className="text-blue-500" href='/reset-password-request'>Forgot Password?</a>
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
      
                  </div>
                </div>
              </div>
            ) : (
              <div className="flex flex-col items-center justify-center h-full">
                {publicContent === false ? (
                  <div>
                    <h1 className="text-4xl">{message.status} {message.error} </h1>
                    {message && <h3>{message.message}</h3>}
                  </div>
                ) : (
                  'Error'
                )}
              </div>
            )}
          </main>
          <Footer />
        </div>
      );
      
}

export default Login;