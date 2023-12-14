import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import './CustomerVehicleDetails.css';

function CustomerVehicleDetails() {
  const { customerId, vehicleId } = useParams();
  const [vehicleDetails, setVehicleDetails] = useState(null);

  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/customers/${customerId}/vehicles/${vehicleId}`)
      .then(res => {
        if (res.status === 200) {
          setVehicleDetails(res.data);
        }
      })
      .catch(error => {
        console.error("Error fetching vehicle details", error);
      });
  }, [customerId, vehicleId]);

  if (!vehicleDetails) {
    return <div>Loading...</div>;
  }

  return (
    <div className="customervehicle-details-page">
      <Navbar />
      <div className="customervehicle-details-content">
        <aside className="customervehicle-details-mechanic-display">
          <MechanicDisplay />
          <Sidebar />
        </aside>
        <main className="customervehicle-details-main">
          <div className="customervehicle-details-top-of-table">
            <p>VEHICLE DETAILS</p>
          </div>
          <div className="customervehicle-details-form-container">
            <form className="customervehicle-user-details-form">
              <label>Make</label>
              <input className="input-field" value={vehicleDetails.make} readOnly />

              <label>Model</label>
              <input className="input-field" value={vehicleDetails.model} readOnly />

              <label>Year</label>
              <input className="input-field" value={vehicleDetails.year} readOnly />
              
              <label>Transmission</label>
              <input className="input-field" value={vehicleDetails.transmission_type} readOnly />

              <label>Mileage</label>
              <input className="input-field" value={vehicleDetails.mileage} readOnly />

              {/* Save button can be removed if not updating vehicle details */}
              <button className="save-button">Save</button>
            </form>
          </div>
          <div className="customervehicle-details-car-image-container">
            <img src="/yellow-car-sideview-free-vector.jpg" alt="Yellow Car Side" />
          </div>
        </main>
      </div>
      <Footer />
    </div>
  );
}

export default CustomerVehicleDetails;
