import React, { useEffect, useState } from 'react';
import MechanicDisplay from '../../User_Components/MechanicDisplay';
import UserDisplay from '../../User_Components/UserDisplay';
import authService from '../../../Services/auth.service';


function Sidebar({ customerId }) {

  const [userRole, setUserRole] = useState('');

  useEffect(() => {
    const currentUser = authService.getCurrentUser();
    if (currentUser && currentUser.roles.includes('ROLE_ADMIN')) {
      setUserRole('admin');
    } else {
      setUserRole('user');
    }
  }, []);


  return (
    <aside className="bg-white w-full md:w-1/4 h-auto md:h-[400px] p-5 shadow-md">
     
        <nav className="mt-5">
        {userRole === 'admin' ? (
          <>
<MechanicDisplay className="mb-10" />

            <a href={`/admin/customers/${customerId}`}>
          <div className="mb-5 hover:bg-gray-100 p-4">
              <i className="settings-icon"></i>
              Profile Settings
          </div>
        </a>
            <a href={`/admin/customers/${customerId}/vehicles`}>
              <div className="mb-5 hover:bg-gray-100 p-4">
                  <i className="vehicles-icon"></i>
                  Vehicle List
              </div>
            </a>
            <a href={`/admin/customers/${customerId}/appointments`}>
              <div className="mb-5 hover:bg-gray-100 p-4">
                  <i className="appointments-icon"></i>
                  Appointments
              </div>
            </a>
            <a href={`/admin/customers/${customerId}/invoices`}>
              <div className="mb-5 hover:bg-gray-100 p-4">
                  <i className="invoices-icon"></i>
                  Invoices
              </div>
            </a>
          </>
        ) : (
          <>
          <UserDisplay className="mb-10" />

             <a href={`/user/customers/${customerId}`}>
          <div className="mb-5 hover:bg-gray-100 p-4">
              <i className="settings-icon"></i>
              Profile Settings
          </div>
        </a>
        <a href={`/user/customers/${customerId}/vehicles`}>
              <div className="mb-5 hover:bg-gray-100 p-4">
                  <i className="vehicles-icon"></i>
                  Vehicles
              </div>
            </a>
            <a href={`/user/customers/${customerId}/appointments`}>
              <div className="mb-5 hover:bg-gray-100 p-4">
                  <i className="appointments-icon"></i>
                  Appointments
              </div>
            </a>
            <a href={`/user/customers/${customerId}/invoices`}>
              <div className="mb-5 hover:bg-gray-100 p-4">
                  <i className="invoices-icon"></i>
                  Invoices
              </div>
            </a>
          </>
        )}
      </nav>
    </aside>
  );
}

export default Sidebar;