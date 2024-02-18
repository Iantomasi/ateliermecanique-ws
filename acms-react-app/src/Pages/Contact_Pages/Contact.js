import React, { useEffect, useState } from 'react';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Navbar from '../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import userService from '../../Services/user.service.js';
import authService from '../../Services/auth.service.js';

function Contact() {

    const hours = [
        "Sunday: Closed",
        "Monday: 8:00 - 12:00 & 13:00 - 17:00",
        "Tuesday: 8:00 - 12:00 & 13:00 - 17:00",
        "Wednesday: 8:00 - 12:00 & 13:00 - 17:00",
        "Thursday: 8:00 - 12:00 & 13:00 - 17:00",
        "Friday: 8:00 - 12:00 & 13:00 - 17:00",
        "Saturday: Closed"]

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
      <main className="flex-grow flex flex-col justify-center">
        {publicContent === true ? ( 
        <section className="flex flex-col hero bg-white">
            <div className="flex flex-col md:flex-row md:ml-10 md:mr-10 lg:ml-32 lg:mr-32 md:-mt-10">
                <div className="flex flex-col justify-center md:w-1/2 lg:w-2/5">
                    <h1 className="text-4xl md:text-6xl lg:text-7xl font-bold">
                        contact us
                    </h1>
                    <p className='sm:text-lg md:text-2xl lg:text-2xl mt-10'>Your satisfaction is our priority. If you face any issues with your vehicle, contact us today, and we'll provide the solutions you need.</p>
                    <div className="flex items-center">
                        <img src="/phone.svg" alt="phone" className="w-1/2 h-1/2" />
                        <p className="text-xl font-bold">(514) 321-3502</p>
                    </div>
                </div>
                <div className="flex flex-col md:items-center md:justify-center lg:items-end lg:justify-end md:w-1/2 lg:w-4/5 sm:ml-0 sm:mr-0 float-end md:mr-0 sm:mt-20">
                    <div>
                        <h1 className='text-4xl font-bold'>business hours</h1>
                        {hours.map((hour, index) => (
                            <div key={index} className="flex items-center">
                                <p className='text-lg mb-5'>{hour}</p>
                                {hour === "Sunday: Closed" && <img src="/clock.svg" alt="clock" className="w-1/6 h-1/6 ml-60" />}
                            </div>
                        ))}
                    </div>
                    <div className='flex mt-4'>
                        <p className='underline font-bold text-2xl mt-10'>cmsautovalue@bellnet.ca</p>
                        <img src='/email.svg' alt='email' className='sm:w-1/4 sm:h-1/4   xl:w-1/2 xl:h-1/2 ' />
                    </div>
                </div>
            </div>
            <div className="flex items-center justify-center -mt-28">
                <div className="text-center">
                    <img src='/location.svg' alt='location' className='w-1/6 h-1/6 mb-2 mx-auto' />
                    <p className='text-2xl font-bold'>9180 Boul Langelier, Saint-Léonard, QC H1P 2E1</p>
                </div>
            </div>
        </section>
                
         ) : (
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
  );
}

export default Contact;
