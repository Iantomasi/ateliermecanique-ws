import React, { useEffect, useState } from 'react';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import userService from '../../Services/user.service.js';

function Home() {
  const [publicContent, setPublicContent] = useState(null);
  const [message, setMessage] = useState('');
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

  return (
    <div className="flex flex-col min-h-screen">
      <NavBar />
      <main className="flex-grow flex flex-col justify-center">
        {publicContent === true ? (
          <section className="flex flex-col md:flex-row hero bg-white p-4 md:p-8">
            <div className="flex flex-col justify-center md:w-1/2 lg:w-2/5">
              <h1 className="text-3xl md:text-4xl lg:text-5xl">
                Revitalize your ride with expert care.
              </h1>
              <button className="text-base md:text-lg bg-yellow-400 py-2 px-6 font-bold rounded mt-4 md:mt-6 lg:mt-8 transform transition duration-300 hover:scale-110">
                Book now
              </button>
            </div>
            <div className="flex items-center justify-center md:w-1/2 lg:w-4/5">
              <img src="car-repair.png" alt="Car Service" />
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

        <div className="reviews-container">
          {/* REVIEWS ARE TO BE ADDED LATER ON */}
        </div>
      </main>

      <Footer />
    </div>
  );
}

export default Home;
