import React, { useEffect , useState} from 'react';
import NavBar from '../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Navbar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import HomeOption from '../../Components/General_Components/HomeOption.js';
import userService from '../../Services/user.service.js';

function Admin() {

  const [publicContent, setPublicContent] = useState(null);
  const [message, setMessage] = useState('');

  useEffect(() => {
    userService.getAdminContent()
      .then(response => {
        if(response.data === "Admin Content.") {
          setPublicContent(true);
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
      {publicContent ?(
        <div className="flex-1">
          <NavBar />
          <div className="text-center">
            <div className="flex justify-center mt-5">
              <img src="waveHand.svg" alt="hand" className="max-w-full" />
            </div>
    
            <h2 className="text-7xl mt-2 mb-5">Welcome Admin!</h2>
    
            <div className="flex flex-col">
              <div className="flex">
                <div className="flex-1 text-center">
                  <HomeOption src="customersImage.svg" label="customers" />
                </div>
                <div className="flex-1 text-center">
                  <HomeOption src="appointments.svg" label="appointments" />
                </div>
              </div>
              <div className="flex mt-10">
                <div className="flex-1 text-center">
                  <HomeOption src="invoices.svg" label="invoices" />
                </div>
                <div className="flex-1 text-center">
                  <HomeOption src="reviews.svg" label="reviews" />
                </div>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div className="flex-1 text-center">
          <Navbar />
          {publicContent === false ? <h1 className='text-4xl'>{message.status} {message.error} </h1> : 'Error'}
          {message && (
            <>
              <h3>{message.message}</h3>
            </>
          )}
        </div>
      )}
      <Footer />
    </div>
  );
}

export default Admin;
