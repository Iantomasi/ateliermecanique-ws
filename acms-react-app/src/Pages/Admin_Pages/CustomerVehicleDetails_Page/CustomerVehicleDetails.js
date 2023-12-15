import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import './CustomerVehicleDetails.css';
import { useNavigate} from 'react-router-dom';

function CustomerVehicleDetails() {
  const { customerId, vehicleId } = useParams();
  const [vehicleDetails, setVehicleDetails] = useState({
    make: '',
    model: '',
    year: '',
    transmission_type: '',
    mileage: ''
  });

  const [showConfirmation, setShowConfirmation] = useState(false);
  const navigate = useNavigate();

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

  function handleInputChange(event) {
    const { name, value } = event.target;
    setVehicleDetails(prevState => ({
      ...prevState,
      [name]: value
    }));
  }

  function updateCustomerVehicle(event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const updatedCustomerVehicle = {
      firstName: formData.get('firstName'),
      make: formData.get('make'),
      model: formData.get('model'),
      year: formData.get('year'),
      transmissionType: formData.get('transmissionType'),
      mileage: formData.get('mileage')
      
    };

    axios.put(`http://localhost:8080/api/v1/customers/${customerId}/vehicles/${vehicleId}`, updatedCustomerVehicle)
      .then(res => {
        if (res.status === 200) {
          console.log("Customer Vehicle has been successfully updated!");
          setVehicleDetails(res.data);
          alert("Customer Vehicle has been updated!")
        }
      })
      .catch(err => {
        console.error("Error updating customer vehicle:", err);
      });
  }

  function confirmDelete() {
    setShowConfirmation(true);
  }

  function cancelDelete() {
    setShowConfirmation(false);
  }

  function executeDelete() {
    axios.delete(`http://localhost:8080/api/v1/customers/${customerId}/vehicles/${vehicleId}`)
    .then(res => {
      if(res.status === 204) {
        alert("Customer Vehicle has been deleted!")
        setShowConfirmation(false);
        navigate(`/admin/customers/${customerId}/vehicles`)
      }
    })
    .catch(err =>{
      console.error("Error deleting customer:", err);
      setShowConfirmation(false);
    })
  }


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
          {vehicleDetails && (
          <div className="customervehicle-details-form-container">
            <form className="customervehicle-user-details-form" onSubmit={updateCustomerVehicle}>
              <label>Make</label>
              <input className="input-field" name="make" value={vehicleDetails.make} onChange={handleInputChange} type="text" required />
            
              <label>Model</label>
              <input className="input-field" name="model" value={vehicleDetails.model} onChange={handleInputChange} type="text" required />

              <label>Year</label>
              <input className="input-field" name="year" value={vehicleDetails.year} onChange={handleInputChange} type="text" required />

              <label>Transmission Type</label>
              <select className="input-field" name="transmissionType"
                      value={vehicleDetails.transmissionType} onChange={handleInputChange} required>
                <option value="">Select Transmission Type</option>
                <option value="AUTOMATIC">Automatic</option>
                <option value="MANUAL">Manual</option>
              </select>
              <label>Mileage</label>
              <input className="input-field" name="mileage" value={vehicleDetails.mileage} onChange={handleInputChange} type="text" required />

              <button className="save-button" type='submit'>Save</button>
              
              <button className="delete-button" onClick={confirmDelete} type="button">Delete</button>
              {showConfirmation && (
                  <div className="confirmation-overlay">
                    <div className="confirmation-box">
                      <p>Are you sure you want to delete {vehicleDetails.make}?</p>
                      <button onClick={executeDelete} type='button'>Yes</button>
                      <button onClick={cancelDelete} type='button'>No</button>
                    </div>
                  </div>
                )}
            </form>
          </div>
          )}
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
