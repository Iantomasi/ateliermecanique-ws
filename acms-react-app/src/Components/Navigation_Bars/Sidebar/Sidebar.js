import React from 'react';
import MechanicDisplay from '../../User_Components/MechanicDisplay';

function Sidebar({ customerId }) {
  return (
    <aside className="bg-white w-full md:w-1/4 h-auto md:h-[400px] p-5 shadow-md">
      <MechanicDisplay className="mb-10" />

      <nav className="mt-5">
        <div className="mb-5 hover:bg-gray-100 p-4">
          <a href="/profile-settings">
            <i className="settings-icon"></i>
            Profile Settings
          </a>
        </div>
        <div className="mb-5 hover:bg-gray-100 p-4">
          <a href={`/admin/customers/${customerId}/vehicles`}>
            <i className="vehicles-icon"></i>
            Vehicle List
          </a>
        </div>
        <div className="mb-5 hover:bg-gray-100 p-4">
        <a href={`/admin/customers/${customerId}/appointments`}>
            <i className="appointments-icon"></i>
            Appointments
          </a>
        </div>
        <div className="mb-5 hover:bg-gray-100 p-4">
          <a href="/invoices">
            <i className="invoices-icon"></i>
            Invoices
          </a>
        </div>
      </nav>
    </aside>
  );
}

export default Sidebar;
