<<<<<<< HEAD
import React, { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import UserProfile from '../../User_Components/UserProfile';
import { useNavigate } from 'react-router-dom';
import authService from '../../../Services/auth.service';


function Navbar() {
  const navigate = useNavigate();
  const [userRole, setUserRole] = useState(null);


  useEffect(() => {
    const currentUser = authService.getCurrentUser();

    if (currentUser) {
      setUserRole(currentUser.roles.includes('ROLE_ADMIN') ? 'admin' : 'user');
    }
  }, []);

    const handleLogoClick = () => {
    if (userRole === 'admin') {
      navigate('/admin');
    } else if (userRole === 'user') {
      navigate('/user');
    }
  };

=======
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import UserProfile from '../../User_Components/UserProfile';
import { MenuIcon, XIcon } from '@heroicons/react/outline'; // Ensure you have @heroicons/react installed

function Navbar() {
  const navigate = useNavigate();
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

>>>>>>> db09406 (progress)
  return (
      <header className="bg-white">
        <nav className="flex justify-between items-center p-4">
          <div className="flex items-center">
<<<<<<< HEAD
          <a href="/about" className="text-gray-700 hover:text-gray-900 px-4 py-1">About</a>
          <a href="/services" className="text-gray-700 hover:text-gray-900 px-4 py-1">Services</a>
          <a href="/contact" className="text-gray-700 hover:text-gray-900 px-4 py-1">Contact</a>
=======
            <Link to="/admin" className="text-4xl font-bold mr-4">
              Atelier Mecanique
            </Link>
            <img src="/logo.svg" alt="app logo" className="cursor-pointer" onClick={() => navigate('/admin')} />
>>>>>>> db09406 (progress)
          </div>

          {/* Hamburger icon for mobile */}
          <div className="sm:hidden">
            <button onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}>
              {isMobileMenuOpen ? <XIcon className="h-6 w-6" /> : <MenuIcon className="h-6 w-6" />}
            </button>
          </div>

          {/* Links for large screens */}
          <div className="hidden sm:flex items-center">
            <Link to="/about" className="text-gray-700 hover:text-gray-900 px-2 py-1">About</Link>
            <Link to="/services" className="text-gray-700 hover:text-gray-900 px-2 py-1">Services</Link>
            <Link to="/contact" className="text-gray-700 hover:text-gray-900 px-2 py-1">Contact</Link>
          </div>

          {/* User profile */}
          <div className="hidden sm:flex items-center">
            <UserProfile />
          </div>
        </nav>

        {/* Mobile menu */}
        {isMobileMenuOpen && (
            <div className="sm:hidden">
              <Link to="/about" className="block text-gray-700 hover:text-gray-900 px-2 py-1">About</Link>
              <Link to="/services" className="block text-gray-700 hover:text-gray-900 px-2 py-1">Services</Link>
              <Link to="/contact" className="block text-gray-700 hover:text-gray-900 px-2 py-1">Contact</Link>
              <UserProfile />
            </div>
        )}

        <div className="w-full h-px bg-black"></div>
      </header>
  );
}

export default Navbar;
