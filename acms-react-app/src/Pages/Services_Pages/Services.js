import React, { useEffect, useState } from 'react';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Navbar from '../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import userService from '../../Services/user.service.js';
import authService from '../../Services/auth.service.js';

function Services() {

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
    { isLoggedIn? <Navbar/> :<NavBar />}
      <main className="flex-grow">
        <section className="py-10 bg-white-500">
          <div className="container mx-auto px-4 grid grid-cols-1 lg:grid-cols-2 gap-x-64"> {/* Added horizontal gap for larger screens */}
            <div className="lg:col-start-1 lg:col-end-2 lg:text-left lg:pr-32"> {/* Added right padding for larger screens */}
          
          <h1 className="text-7xl font-bold mb-8">offered services</h1>

              <p className="text-2xl font-bold mb-8">
                At our garage, we take pride in offering a one-stop solution for all your
                automotive needs, delivering quality and reliability every time.
              </p>
              <img src="/under-car.svg" alt="Under car" className="mb-8" />
            </div>

            {/* Right-aligned elements (List of Services) */}
            <div className="lg:col-start-2 lg:col-end-3 lg:text-left lg:pl-24"> {/* Added left padding for larger screens */}
              <h2 className="text-3xl font-bold mb-2">List of services:</h2>
              <ol className=" text-2xl list-decimal mb-5">
  <li>Air conditioning</li>
  <li>Muffler</li>
  <li>Car Inspection Before Purchase</li>
  <li>End of Manufacturer’s Warranty</li>
  <li>Exhaust System</li>
  <li>Preventive Maintenance</li>
  <li>Engine and Transmission Installation</li>
  <li>Steering & Suspension</li>
  <li>Hydraulic Hoses Manufacturing</li>
  <li>Injection</li>
  <li>Precise & Detailed Evaluation</li>
  <li>Regular Maintenance and Repair</li>
  <li>Maintenance History</li>
  <li>Alignment</li>
  <li>Radiator replacement</li>
  <li>Brakes</li>
  <li>Paint & body work</li>
</ol>
            </div>
          </div>
        </section>

        {/* Promotional Text */}
        <div className="text-center py-20">
          <p className="text-6xl text-yellow-400 font-bold mb-8">
            experience the difference of exceptional service
          </p>
        </div>

        {/* Full Width Action Image */}
        <img src="/service-action.svg" alt="Service action" className="w-full h-auto" />
      </main>

      <Footer />
    </div>
  );
}

export default Services;
