// Sidebar.js
import React from 'react';
import './Sidebar.css'; // Assuming you create a separate CSS file for the sidebar

function Sidebar() {
  return (
    <aside className="sidebar">
        
      <nav className="sidebar-navigation">
        <a href="/profile-settings" className="sidebar-link">
          <i className="sidebar-icon settings-icon"></i>
          Profile Settings
        </a>
        <a href="/vehicle-list" className="sidebar-link">
          <i className="sidebar-icon vehicles-icon"></i>
          Vehicle List
        </a>
        <a href="/appointments" className="sidebar-link">
          <i className="sidebar-icon appointments-icon"></i>
          Appointments
        </a>
        <a href="/invoices" className="sidebar-link">
          <i className="sidebar-icon invoices-icon"></i>
          Invoices
        </a>
      </nav>
    </aside>
  );
}

export default Sidebar;