import React from 'react';
import MechanicDisplay from '../../User_Components/MechanicDisplay';

function Sidebar({ customerId }) {
  return (
    <aside className="bg-white w-full md:w-1/4 h-auto md:h-[400px] p-5 shadow-md">
      <MechanicDisplay className="mb-10" />

      <nav className="mt-5">
        <a href="/profile-settings">
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
        <a href="/invoices">
          <div className="mb-5 hover:bg-gray-100 p-4">
              <i className="invoices-icon"></i>
              Invoices
          </div>
        </a>
      </nav>
    </aside>
  );
}

export default Sidebar;
