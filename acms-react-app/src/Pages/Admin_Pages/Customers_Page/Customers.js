import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';
import CustomerBlock from '../CustomerDetails_Page/CustomerBlock.js';
import axios from 'axios';

function Customers() {
  const [customers, setCustomers] = useState([]);

  useEffect(() => {
    getCustomers();
  }, []);

  function getCustomers() {
    axios.get("http://localhost:8080/api/v1/customers")
      .then(res => {
        console.log('API response:', res); 
        if (res.status === 200) {
          setCustomers(res.data);
        }
      })
      .catch(error => {
        console.log(error);
      })
  }

  return (
    <div>
      <Navbar />
      <div className="content">
        <div className="ml-5 mt-1">
          <MechanicDisplay />
        </div>
        <div className="w-4/5 rounded bg-gray-300 mx-auto mt-1 mb-5">
          <div className="flex p-2 bg-gray-300 w-full">
            <p className="text-2xl font-bold mx-auto">CUSTOMER ACCOUNTS</p>
            <div className="flex items-center space-x-4">
              <div className="relative flex">
                <input className="w-full rounded border-gray-300 px-4 py-2 focus:outline-none focus:border-indigo-500" type="text" placeholder="Search..." />
                <span className="text-gray-400 cursor-pointer">&#128269;</span>
              </div>
              <button className="text-white border-none px-4 py-2 rounded font-bold transition duration-300 hover:scale-110 bg-black">
                Add+
              </button>
            </div>
          </div>
          <div className="w-full overflow-y-scroll max-h-[230px]">
            <table className="w-full table-auto">
              <thead className="bg-white sticky top-0">
                <tr>
                  <th>ID</th>
                  <th>FIRST NAME</th>
                  <th>LAST NAME</th>
                  <th>EMAIL</th>
                  <th>PHONE NUMBER</th>
                </tr>
              </thead>
              <tbody className="text-center">
                {customers.map((customer) => (
                  <CustomerBlock key={customer.customerId} customer={customer} />
                ))}
              </tbody>
            </table>
          </div>
        </div>
        <div className="mb-5">
          <img src="/customersImage.svg" alt="customer's Image" />
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Customers;
