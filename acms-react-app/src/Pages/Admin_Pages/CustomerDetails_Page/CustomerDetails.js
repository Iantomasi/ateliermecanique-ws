import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import './CustomerDetails.css';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';



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
    <div className="customer-details-page">
      <Navbar/>
      <div className="customer-details-content">
        <aside className="customer-details-mechanic-display">
          <MechanicDisplay/>
          <Sidebar/>
        </aside>
        <main className="customer-details-main">
          <div className="customer-details-top-of-table">
            <p>CUSTOMER ACCOUNT DETAILS</p>
          </div>
          <div className="customer-details-form-container">
            <form className="user-details-form">
              <label>First Name</label>
              <input className="input-field" value={customerDetails.firstName} readOnly />

              <label>Last Name</label>
              <input className="input-field" value={customerDetails.lastName} readOnly />

              <label>Email Address</label>
              <input className="input-field" value={customerDetails.email} readOnly />

              <label>Phone Number</label>
              <input className="input-field" value={customerDetails.phoneNumber} readOnly />

              <button className="save-button">Save</button>
            </form>
          </div>
          <div className="customer-details-car-image-container">
            <img src="/happy-young-man-and-car.png" alt="Happy Man Car" />
          </div>
        </main>
      </div>
      <Footer/>
    </div>
  );
}

export default CustomerDetails;