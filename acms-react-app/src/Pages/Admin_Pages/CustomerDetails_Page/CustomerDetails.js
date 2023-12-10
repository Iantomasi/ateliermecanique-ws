import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';


import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';


function CustomerDetails() {
  const { customerId } = useParams();
  const [customerDetails, setCustomerDetails] = useState(null);

  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/customers/${customerId}`)
      .then(res => {
        if (res.status === 200) {
          setCustomerDetails(res.data);
        }
      })
      .catch(error => {
        console.error("Error fetching customer details", error);
      });
  }, [customerId]);

  if (!customerDetails) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <Navbar/>
      <div className="content">
        <aside className="mechanic-display">
          <MechanicDisplay/>
          {/* Add sidebar content here */}
        </aside>
        <div className="gear-icon-container">
          <img src="gear-machine-isolated-icon-vector.jpg" alt="Gear machine icon" />
        </div>        <main className="main-content">
          <form className="user-details-form">
            {/* Replace these with actual form inputs */}
            <input className="input-field" value={customerDetails.firstName} readOnly />
            <input className="input-field" value={customerDetails.lastName} readOnly />
            <input className="input-field" value={customerDetails.email} readOnly />
            <input className="input-field" value={customerDetails.phoneNumber} readOnly />
            <button className="save-button">Save</button>
          </form>
        </main>
      </div>
      <img src="happy-young-man-and-car - Copy.png" alt="Happy Man Car" />
      <Footer/>
    </div>
  );
}

export default CustomerDetails;
