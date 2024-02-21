import React, { useEffect, useState } from 'react';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Navbar from '../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import userService from '../../Services/user.service.js';
import authService from '../../Services/auth.service.js';
import { ReactComponent as YourSvgImage } from '../../Pages/About_Pages/AboutCar.svg';
import autoValueLogo from '../../Pages/About_Pages/AutoValue.png'; 
import { useTranslation } from 'react-i18next';

function About(){

  const { t } = useTranslation();

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
      <h1 className="text-3xl font-bold">{t('about.atelierMecaniqueCMS')}</h1>
      </div>
      <main className="flex-grow">
      {publicContent === true ? ( 
      <div>
        <section className="flex flex-col items-center bg-white px-10"> 
          <div className="flex flex-col md:flex-row items-center md:space-x-8">
            <div className="mb-4 md:mb-0">
              <YourSvgImage className="w-32 md:w-48 lg:w-64 h-auto" />
            </div>
            <div className="mt-2 md:mt-0">
              <h2 className="text-2xl font-semibold mb-2">{t('about.aboutUs')}</h2>
              <div> 
                <p>
                  {t('about.welcome')}
                </p>
                <p>
                  {t('about.moreThanJust')}
                </p>
                <p>
                  {t('about.carEnthusiasts')}
                </p>
                <p>
                  {t('about.mission')}
                </p>
                <p className='mb-20'>
                  {t('about.countOnUs')}
                </p>
              </div>
            </div>
          </div>
        </section>
        <section className="my-10 px-10">
            <div className="flex flex-col items-center"> 
              <h3 className="text-xl font-semibold mb-4">{t('about.trustedBy')}</h3>
              <img src={autoValueLogo} alt="Auto Value" className="w-20 h-auto" /> 
            </div>
        </section>
      </div>
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

export default About;
