import React, { useEffect, useState } from 'react';
import NavBar from '../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import HomeOption from '../../Components/General_Components/HomeOption.js';
import axios from 'axios';
import UserForm from './UserFormForMissingFields.js';

function User() {
  const [username, setUsername] = useState('');
  const [customer, setCustomer] = useState({});
  const [showUserForm, setShowUserForm] = useState(false);

  useEffect(() => {
    getCustomer();
  }, []);

  function getCustomer() {
    const token = sessionStorage.getItem('userToken');

    axios
      .get(`http://localhost:8080/api/v1/auth/${token}`)
      .then((res) => {
        console.log(res.data);
        setCustomer(res.data);
        setUsername(`${res.data.firstName} ${res.data.lastName}`);

        // Check if any field in customer object is null after fetching data
        const hasNullField = Object.values(res.data).some((value) => value === null);
        setShowUserForm(hasNullField);
      })
      .catch((error) => {
        console.error('Error fetching customer', error);
      });
  }

  const hideForm = () => {
    getCustomer();
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
          <h2 className="text-7xl mt-2 mb-5">Welcome {username}!</h2>
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
