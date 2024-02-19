import React, { useState, useEffect } from 'react';
import Navbar from '../../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../../Components/Footer/Footer.js';
import Sidebar from '../../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import CustomerVehicleBlock from '../CustomerVehicleDetails_Page/CustomerVehicleBlock.js';
import adminService from '../../../../Services/admin.service.js';
import userService from '../../../../Services/user.service.js';
import authService from '../../../../Services/auth.service.js'; 

function CustomerVehicles() {
  const { customerId } = useParams();
  const [vehicles, setVehicles] = useState([]);
  const navigate = useNavigate();

  
  // Adjusted handleCustomerClick method
  const handleCustomerClick = (customerId) => {
    const currentUser = authService.getCurrentUser();

    if (currentUser) {
      const userRoles = currentUser.roles;

      // Assuming 'ROLE_ADMIN' and 'ROLE_CUSTOMER' are your role identifiers
      if (userRoles.includes('ROLE_ADMIN')) {
        navigate(`/admin/customers/${customerId}/vehicles/newVehicle`);
      } else if (userRoles.includes('ROLE_CUSTOMER')) {
        navigate(`/user/customers/${customerId}/vehicles/newVehicle`);
      } else {
        // Handle case for unauthorized or unexpected roles
        console.error("Unauthorized or no role found");
        // Optionally navigate to an error page or display a message
      }
    } else {
      // Handle case where there is no current user logged in
      console.error("No user logged in");
      // Optionally navigate to login page or display a message
    }
  };

  const [publicContent, setPublicContent] = useState(null);
  const [message, setMessage] = useState('');


  useEffect(() => {
    const fetchContent = async () => {
      try {
        const currentUser = authService.getCurrentUser();
        // Assuming getCurrentUser returns a full user object with a roles array
        const userRole = currentUser.roles.includes('ROLE_ADMIN') ? 'admin' : 'customer';
    
        const response = userRole === 'admin' 
          ? await userService.getAdminContent() 
          : await userService.getUserBoard();
  
        if (response.status === 200) {
          setPublicContent(true);
        }
      } catch (error) {
        setPublicContent(false);
        // Improved error handling
        setMessage(error.response ? error.response.data : 'An error occurred');
        console.log(error);
      }
    };
  
    fetchContent();
  }, []);

  useEffect(() => {
    adminService.getAllCustomerVehicles(customerId)
      .then(res => {
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
      {publicContent ? (
        <div>
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
          <div className="w-full flex justify-between">
            <div className="flex justify-around w-full">
              <img src='/blue-car.svg' alt='car' className="w-1/3 h-2/3" />
              <img src='/car.svg' alt='car' className="w-1/3 h-2/3" />
              <img src='/moto.svg' alt='car' className="w-1/4 h-2/3" />
            </div>
          </div>
        </div>) : (
        <div className="flex-1 text-center">
          <Navbar />
          {publicContent === false ? (
            <h1 className='text-4xl'>{message.status} {message.error} </h1>
          ) : (
            'Error'
          )}
          {message && (
            <>
              <h3>{message.message}</h3>
            </>
          )}
        </div>)}
      <Footer />
    </div>
  );
}

export default CustomerVehicles;
