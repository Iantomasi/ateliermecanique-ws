import React from 'react';
import { Link } from 'react-router-dom';
import UserProfile from '../../User_Components/UserProfile';

function Navbar() {
  return (
    <header className="bg-white">
      <nav className="flex justify-between items-center p-4">
        <div className="flex">
          <h1 className="text-4xl font-bold">atelier mecanique</h1>
        </div>

        <div className="flex items-center">
          <div className="mr-0">
            <img src="/logo.svg" alt="app logo"/>
          </div>
          <div className="flex items-center">
            <a href="#" className="text-black px-4">About</a>
            <a href="#" className="text-black px-4">Services</a>
            <a href="#" className="text-black px-4">Contact</a>
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
