import React, { useState, useEffect } from 'react';
import Navbar from '../../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import NavBar from '../../../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../../Components/User_Components/MechanicDisplay.js';
import CustomerBlock from './CustomerBlock.js';
import adminService from '../../../../Services/admin.service.js';
import userService from '../../../../Services/user.service.js';

function Customers() {  
  const [customers, setCustomers] = useState([]);
  const [publicContent, setPublicContent] = useState(null);
  const [message, setMessage] = useState('');

  useEffect(() => {
    userService.getAdminContent()
      .then(response => {
        if(response.data === "Admin Content.") {
          setPublicContent(true);
        } 
      })
      .catch(error => {
        console.error('Error fetching public content:', error);
        setPublicContent(false);
        setMessage(error.response.data);
      });
  }, []); 

  useEffect(() => {
    getCustomers();
  }, []);

  function getCustomers() {
    adminService.getAllCustomers()
      .then(res => {
        if (res.status === 200) {
          setCustomers(res.data);
        }
      })
      .catch(error => {
        console.log(error);
      })
  }


  return (
    <div className="flex flex-col min-h-screen">
      {publicContent ?(
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
                    <CustomerBlock key={customer.id} customer={customer} />
                  ))}
                </tbody>
              </table>
            </div>
          </div>
          <div className="mb-5">
            <img src="/customersImage.svg" alt="customer's Image" />
          </div>
        </div>
      </div>
      ) : (
        <div className="flex-1 text-center">
          <NavBar />
          {publicContent === false ? <h1 className='text-4xl'>{message.status} {message.error} </h1> : 'Error'}
          {message && (
            <>
              <h3>{message.message}</h3>
            </>
          )}
        </div>
      )}
      <Footer />
    </div>
  );
}

export default Customers;
