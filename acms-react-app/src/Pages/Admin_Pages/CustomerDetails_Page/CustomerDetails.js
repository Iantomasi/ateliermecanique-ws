import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';
import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';



function CustomerDetails() {
  const { customerId } = useParams();
  const [customerDetails, setCustomerDetails] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: ''
  });

  const [showConfirmation, setShowConfirmation] = useState(false);
  const navigate = useNavigate()
  
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

  function confirmDelete() {
    setShowConfirmation(true);
  }

  function cancelDelete() {
    setShowConfirmation(false);
  }

  function executeDelete() {
    axios.delete(`http://localhost:8080/api/v1/customers/${customerId}`)
    .then(res => {
      if(res.status === 204) {
        alert("Customer has been deleted!")
        setShowConfirmation(false);
        navigate("/admin/customers")
      }
    })
    .catch(err =>{
      console.error("Error deleting customer:", err);
      setShowConfirmation(false);
    })
  }

  if (!customerDetails) {
    return <div>Loading...</div>;
  }

  return (
    <div className="customer-details-page">
      <Navbar />
      <div className="flex">
        <Sidebar customerId={customerId} />
        
        <main className="flex-grow p-5">
            <p className="text-4xl font-bold text-center">CUSTOMER ACCOUNT DETAILS</p>
          {customerDetails && (
            <div className="bg-gray-100 shadow-lg p-5 rounded-md mt-5 relative">
              <form onSubmit={updateCustomer}>
                <label className='font-bold'>First Name</label>
                <input className="w-full p-4 rounded border border-gray-400 mb-5" name="firstName" value={customerDetails.firstName} onChange={handleInputChange} type="text" required />
  
                <label className='font-bold'>Last Name</label>
                <input className="w-full p-4 rounded border border-gray-400 mb-5" name="lastName" value={customerDetails.lastName} onChange={handleInputChange} type="text" required />
  
                <label className='font-bold'>Email Address</label>
                <input className="w-full p-4 rounded border border-gray-400 mb-5" name="email" value={customerDetails.email} onChange={handleInputChange} type="email" required />
  
                <label className='font-bold'>Phone Number</label>
                <input className="w-full p-4 rounded border border-gray-400 mb-5" name="phoneNumber" value={customerDetails.phoneNumber} onChange={handleInputChange} type="text" required />
  
                <div className="flex justify-center space-x-10">
                  <button className="bg-yellow-400 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" type="submit">Save</button>
                  <button className="bg-red-500 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" onClick={confirmDelete} type="button">Delete</button>
                </div>
                {showConfirmation && (
                  <div className=" fixed top-0 left-0 w-full h-full bg-black bg-opacity-80 flex justify-center items-center z-50">
                      <div className="absolute bg-gray-100 border border-gray-300 rounded-md shadow-lg p-6">
                        <p className="text-xl mb-4">Are you sure you want to delete {customerDetails.firstName}?</p>
                        <div className="flex justify-center space-x-5">
                          <button onClick={executeDelete} type="button"className="px-4 py-2 mr-2 bg-red-500 text-white rounded hover:bg-red-600 focus:outline-none focus:ring focus:ring-red-200">
                          Yes
                          </button>
                          <button onClick={cancelDelete} type="button" className="px-4 py-2 bg-yellow-400  focus:outline-none focus:ring focus:ring-gray-200">
                          No
                          </button>
                        </div>
                      </div>
                  </div>
                )}
              </form>
            </div>
          )}
          <div className=" mt-5">
            <img src="/happy-young-man-and-car.png" alt="Happy Man Car" className="w-1/3 h-auto" />
          </div>
        </main>
      </div>
      <Footer />
    </div>
  );
  
}

export default CustomerDetails;