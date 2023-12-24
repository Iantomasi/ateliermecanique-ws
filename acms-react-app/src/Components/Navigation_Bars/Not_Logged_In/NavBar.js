import React from 'react';
import { Link } from 'react-router-dom';
import {  useNavigate } from 'react-router-dom';


function Navbar() {
  const navigate = useNavigate();

  const navigateToSignup = () => {
    navigate('/signup');
  };

  return (
    <header className="bg-white">
      <nav className="flex flex-col sm:flex-row justify-between items-center p-4 sm:h-20">
        {/* Left-aligned content */}
        <div className="flex items-center">
          <img src="logo.svg" alt="app logo" className="mr-2" />
          <h1 className="text-4xl font-bold">atelier mecanique</h1>
        </div>

        {/* Middle-aligned links */}
        <div className="flex justify-center mt-4 sm:mt-0">
          <a href="#" className="text-gray-700 hover:text-gray-900 px-2 py-1">About</a>
          <a href="#" className="text-gray-700 hover:text-gray-900 px-2 py-1">Services</a>
          <a href="#" className="text-gray-700 hover:text-gray-900 px-2 py-1">Contact</a>
        </div>

        {/* Right-aligned links */}
        <div className="flex items-center mt-4 sm:mt-0">
          <Link to="/login" className="text-gray-700 hover:text-gray-900 px-2 py-1">Login</Link>
          <button className="bg-yellow-400 border-none px-4 py-2 rounded font-bold ml-2 transform transition duration-300 hover:scale-110"
            onClick={navigateToSignup}>Sign up</button>
        </div>
      </nav>
      <div className="w-full h-px bg-black mt-5"></div>
    </header>
  );
}

export default Navbar;