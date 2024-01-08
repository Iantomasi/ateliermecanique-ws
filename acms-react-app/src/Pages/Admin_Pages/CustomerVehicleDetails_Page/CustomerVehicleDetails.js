import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import { useNavigate } from 'react-router-dom';

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
    axios
      .get(`http://localhost:8080/api/v1/customers/${customerId}/vehicles/${vehicleId}`)
      .then(res => {
        if (res.status === 200) {
          console.log(res.data)
          setVehicleDetails(res.data);
        }
      })
      .catch(error => {
        console.error('Error fetching vehicle details', error);
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
      transmission_type: formData.get('transmission_type'),
      mileage: formData.get('mileage')
    };

    axios
      .put(
        `http://localhost:8080/api/v1/customers/${customerId}/vehicles/${vehicleId}`,
        updatedCustomerVehicle
      )
      .then(res => {
        if (res.status === 200) {
          setVehicleDetails(res.data);
          alert('Customer Vehicle has been updated!');
        }
      })
      .catch(err => {
        console.error('Error updating customer vehicle:', err);
      });
  }

  function confirmDelete() {
    setShowConfirmation(true);
  }

  function cancelDelete() {
    setShowConfirmation(false);
  }

  function executeDelete() {
    axios
      .delete(`http://localhost:8080/api/v1/customers/${customerId}/vehicles/${vehicleId}`)
      .then(res => {
        if (res.status === 204) {
          alert('Customer Vehicle has been deleted!');
          setShowConfirmation(false);
          navigate(`/admin/customers/${customerId}/vehicles`);
        }
      })
      .catch(err => {
        console.error('Error deleting customer:', err);
        setShowConfirmation(false);
      });
  }

  if (!vehicleDetails) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <Navbar />
      <div className="flex flex-col md:flex-row">
        <Sidebar customerId={customerId} />
        <main className="flex-grow p-5">
          <div className="text-4xl font-bold text-center">
            <p>VEHICLE DETAILS</p>
          </div>
          {vehicleDetails && (
            <div className="bg-gray-100 shadow-lg p-5 rounded-md mt-5 relative">

              <form className="customervehicle-user-details-form" onSubmit={updateCustomerVehicle}>
                <label className="font-bold">Make</label>
                <input className="w-full p-4 rounded border border-gray-400 mb-5" name="make" value={vehicleDetails.make} onChange={handleInputChange} type="text" required />
                
                <label className="font-bold">Model</label>
                <input className="w-full p-4 rounded border border-gray-400 mb-5" name="model" value={vehicleDetails.model} onChange={handleInputChange} type="text" required />

                <label className="font-bold">Year</label>
                <input className="w-full p-4 rounded border border-gray-400 mb-5" name="year" value={vehicleDetails.year} onChange={handleInputChange} type="text" required />

                <label className="font-bold">Transmission Type</label>
                <select className="w-full p-4 rounded border border-gray-400 mb-5" name="transmission_type" value={vehicleDetails.transmission_type} onChange={handleInputChange}  required>
                  <option value="">Select Transmission Type</option>
                  <option value="AUTOMATIC">Automatic</option>
                  <option value="MANUAL">Manual</option>
                </select>
                
                <label className="font-bold">Mileage</label>
                <input className="w-full p-4 rounded border border-gray-400 mb-5" name="mileage" value={vehicleDetails.mileage} onChange={handleInputChange} type="text" required />

                <div className="flex justify-center space-x-10">
                  <button className="bg-yellow-400 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" type="submit">
                    Save
                  </button>
                  <button className="bg-red-500 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" onClick={confirmDelete} type="button">
                    Delete
                  </button>
                </div>
                
                {showConfirmation && (
                  <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-80 flex justify-center items-center z-50">
                    <div className="absolute bg-gray-100 border border-gray-300 rounded-md shadow-lg p-6">
                      <p className="text-xl mb-4">Are you sure you want to delete {vehicleDetails.model}?</p>
                      <div className="flex justify-center space-x-5">
                        <button onClick={executeDelete} type="button" className="px-4 py-2 mr-2 bg-red-500 text-white rounded hover:bg-red-600 focus:outline-none focus:ring focus:ring-red-200">
                          Yes
                        </button>
                        <button onClick={cancelDelete} type="button" className="px-4 py-2 bg-yellow-400 focus:outline-none focus:ring focus:ring-gray-200">
                          No
                        </button>
                      </div>
                    </div>
                  </div>
                )}
              </form>
            </div>
          )}
          <div className="mt-5">
            <img src="/car.svg" alt="Yellow Car Side" className="w-full md:w-1/3 h-auto" />
          </div>
        </main>
      </div>
      <Footer />
    </div>
  );
}

export default CustomerVehicleDetails;
