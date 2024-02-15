import React, { useEffect, useState } from 'react';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Navbar from '../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import userService from '../../Services/user.service.js';
import authService from '../../Services/auth.service.js';
import { ReactComponent as YourSvgImage } from '../../Pages/About_Pages/AboutCar.svg';
import autoValueLogo from '../../Pages/About_Pages/AutoValue.png'; 



function About(){

  const [publicContent, setPublicContent] = useState(null);
  const [message, setMessage] = useState('');
  const [isLoggedIn, setBool] = useState(false);

  useEffect(() => {
    userService.getPulicContent()
      .then(response => {
        if(response.data){
          if(response.data === "Public Content.") {
            setPublicContent(true);
          } 
        }
      })
      .catch(error => {
        console.error('Error fetching public content:', error);
        setPublicContent(false);
        setMessage(error.response.data);
      });
  }, []); 

  useEffect(() => {
    authService.getCurrentUser() ? setBool(true) : setBool(false);
  }, []);

  return (
    <div className="flex flex-col min-h-screen">
      {isLoggedIn ? <Navbar /> : <NavBar />}
      <div className="text-center py-4">
      <h1 className="text-3xl font-bold">Atelier Mecanique CMS</h1>
      </div>
      <main className="flex-grow">
      <section className="flex flex-col items-center bg-white px-10"> 
  <div className="flex flex-col md:flex-row items-center md:space-x-8">
    <div className="mb-4 md:mb-0">
      <YourSvgImage className="w-32 md:w-48 lg:w-64 h-auto" />
    </div>
    <div className="mt-2 md:mt-0">
      <h2 className="text-2xl font-semibold mb-2">About Us</h2>
      <div> 
        <p>
          Welcome to Atelier Mécanique, your trusted garage in Saint-Leonard. 
        </p>
        <p>
          Where we pride ourselves on being more than just a car service center.
        </p>
        <p>
          We are a haven for car enthusiasts and a dedicated team of skilled mechanics.
        </p>
        <p>
          Our mission is clear: to keep your vehicles running smoothly and ensure your road safety.
        </p>
        <p className='mb-20'>
          Whether it's routine maintenance, repairs, or friendly advice, you can count on us for top-notch care.
        </p>
      </div>
  </div>
  </div>
</section>
<section className="my-10 px-10">
          <div className="flex flex-col items-center"> 
            <h3 className="text-xl font-semibold mb-4">Trusted by</h3>
            <img src={autoValueLogo} alt="Auto Value" className="w-20 h-auto" /> 
          </div>
        </section>
      </main>
      <Footer />
    </div>
  );
}

export default About;