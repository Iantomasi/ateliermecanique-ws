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
  const [customerDetails, setCustomerDetails] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: ''
  });

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

  function handleInputChange(event) {
    const { name, value } = event.target;
    setCustomerDetails(prevState => ({
      ...prevState,
      [name]: value
    }));
  }

  function updateCustomer(event) {
    event.preventDefault();
  
    const formData = new FormData(event.target);
    const updatedCustomer = {
      firstName: formData.get('firstName'),
      lastName: formData.get('lastName'),
      email: formData.get('email'),
      phoneNumber: formData.get('phoneNumber')
    };
  
    axios.put(`http://localhost:8080/api/v1/customers/${customerId}`, updatedCustomer)
      .then(res => {
        if (res.status === 200) {
          console.log("Customer has been successfully updated!");
          setCustomerDetails(res.data);
          alert("Customer has been updated!")
        }
      })
      .catch(err => {
        console.error("Error updating customer:", err);
      });
  }

  if (!customerDetails) {
    return <div>Loading...</div>;
  }
  return (
    <div className="customer-details-page">
      <Navbar />
      <div className="customer-details-content">
        <aside className="customer-details-mechanic-display">
          <MechanicDisplay/>
          <Sidebar customerId={customerId} />
        </aside>
        <main className="customer-details-main">
          <div className="customer-details-top-of-table">
            <p>CUSTOMER ACCOUNT DETAILS</p>
          </div>
          {customerDetails && (
            <div className="customer-details-form-container">
              <form className="user-details-form" onSubmit={updateCustomer}>
                <label>First Name</label>
                <input className="input-field" name="firstName" value={customerDetails.firstName} onChange={handleInputChange} type="text" required />
  
                <label>Last Name</label>
                <input className="input-field" name="lastName" value={customerDetails.lastName} onChange={handleInputChange} type="text" required />
  
                <label>Email Address</label>
                <input className="input-field" name="email" value={customerDetails.email} onChange={handleInputChange} type="email" required />
  
                <label>Phone Number</label>
                <input className="input-field" name="phoneNumber" value={customerDetails.phoneNumber} onChange={handleInputChange} type="text" required />
  
                <button className="save-button" type="submit">
                  Save
                </button>
              </form>
            </div>
          )}
          <div className="customer-details-car-image-container">
            <img src="/happy-young-man-and-car.png" alt="Happy Man Car" />
          </div>
        </main>
      </div>
      <Footer />
    </div>
  );
  
}

export default CustomerDetails;