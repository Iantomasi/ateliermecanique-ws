import React, { useState, useEffect } from 'react';
import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import CustomerVehicleBlock from '../CustomerVehicleDetails_Page/CustomerVehicleBlock.js';

function CustomerVehicles() {
  const { customerId } = useParams();
  const [vehicles, setVehicles] = useState([]);
  const navigate = useNavigate();

  const handleCustomerClick = (customerId) => {
    navigate(`/admin/customers/${customerId}/vehicles/newVehicle`);
    console.log(customerId);
  };

  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/customers/${customerId}/vehicles`)
      .then(res => {
        console.log('API response:', res);
        if (res.status === 200) {
          setVehicles(res.data);
        }
      })
      .catch(error => {
        console.log(error);
      });
  }, [customerId]);

  return (
    <div className="flex flex-col min-h-screen">
      <Navbar />
      <div className="flex flex-1 mt-5">
        <Sidebar customerId={customerId} />
        <div className="flex-1 ml-5 mt-10">
          <div className="w-5/6 rounded bg-gray-300 mx-auto mt-1 mb-5">
            <div className="flex p-2 bg-gray-300 w-full">
              <p className="text-2xl font-bold mx-auto">VEHICLES</p>
              <div className="flex items-center space-x-4">
                <div className="relative flex">
                  <input className="w-full rounded border-gray-300 px-4 py-2 focus:outline-none focus:border-indigo-500" type="text" placeholder="Search..." />
                  <span className="text-gray-400 cursor-pointer">&#128269;</span>
                </div>
                <button className="text-white border-none px-4 py-2 rounded font-bold transition duration-300 hover:scale-110 bg-black" onClick={() =>{handleCustomerClick(customerId)}}>
                  Add+
                </button>
              </div>
            </div>
            <div className="w-full overflow-y-scroll sm:h-96">
              <table className="w-full table-auto">
                <thead className="bg-white sticky top-0">
                  <tr>
                    <th>VEHICLE ID</th>
                    <th>CUSTOMER ID</th>
                    <th>MAKE</th>
                    <th>MODEL</th>
                    <th>YEAR</th>
                    <th>TRANSMISSION</th>
                    <th>MILEAGE</th>
                  </tr>
                </thead>
                <tbody className="text-center">
                  {vehicles.map((vehicle) => (
                    <CustomerVehicleBlock key={vehicle.vehicleId} vehicle={vehicle} customerId={customerId} />
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default CustomerVehicles;
