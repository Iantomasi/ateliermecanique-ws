import React from 'react';
import { Link } from 'react-router-dom';
import UserProfile from '../../User_Components/UserProfile';
import { useNavigate } from 'react-router-dom';


function Navbar() {
  const navigate = useNavigate();

  const handleLogoClick = () => {
    navigate('/admin');
  };
  return (
    <header className="bg-white">
      <nav className="flex justify-between items-center p-4">
        <div className="flex">
          <h1 className="text-4xl font-bold">Atelier Mecanique</h1>
        </div>

        <div className="flex items-center">
        <div className="mr-0" onClick={handleLogoClick} style={{ cursor: 'pointer' }}>
        <img src="/logo.svg" alt="app logo"/>
        </div>

          <div className="flex items-center">
            <a href="/about" className="text-black px-4">About</a>
            <a href="/services" className="text-black px-4">Services</a>
            <a href="/contact" className="text-black px-4">Contact</a>
          </div>
        </div>

        <div className="flex items-center">
          <UserProfile />
        </div>
      </nav>
      <div className="w-full h-px bg-black"></div>
    </header>
  );
}

export default Navbar;
