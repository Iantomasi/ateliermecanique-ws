import React, { useEffect, useState } from 'react';
import NavBar from '../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import HomeOption from '../../Components/General_Components/HomeOption.js';
import axios from 'axios';
import UserForm from './UserFormForMissingFields.js';
import AuthService from '../../Services/auth.service.js';

function User() {
  const [customer, setCustomer] = useState({});
  const [showUserForm, setShowUserForm] = useState(false);

  useEffect(() => {
    const fetchCustomer = async () => {
      const fetchedCustomer = AuthService.getCurrentUser();
      setCustomer(fetchedCustomer);
      
      const hasNullField = Object.entries(fetchedCustomer)
        .filter(([key]) => key !== 'picture') // Ignore picture field
        .some(([key, value]) => value === null);
  
      if (hasNullField) {
        setShowUserForm(true);
      }
    };
  
    fetchCustomer();
  }, [showUserForm]);

  const hideForm = () => {
    setShowUserForm(false);
  };


  return (
    <div className={`bg-white ${showUserForm ? 'bg-gray-900' : ''}`}>
      <NavBar />
      {showUserForm ? (
        <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-80 flex justify-center items-center z-50">
          <UserForm customer={customer} hideUserForm={hideForm} />
        </div>
      ) : <div></div>}
        <div className="text-center">
          <div className="flex justify-center mt-5">
            <img src="waveHand.svg" alt="hand" className="max-w-full" />
          </div>
          <h2 className="text-7xl mt-2 mb-5">Welcome Back {customer.firstName}!</h2>
          <div className="flex flex-col">
            <div className="flex">
              <div className="flex-1 text-center">
                <HomeOption src="/car.svg" label="vehicles" />
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
          <Footer />
        </div>
    </div>
  );
}

export default User;
