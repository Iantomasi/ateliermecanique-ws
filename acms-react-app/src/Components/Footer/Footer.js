import React from 'react';

function Footer() {
  return (
    <footer className="bg-black text-white p-4 md:p-6 text-center">
      <nav className="mb-4">
        <a href="#" className="text-white hover:text-gray-300 px-2">About Us</a>
        <a href="#" className="text-white hover:text-gray-300 px-2">Privacy Policy</a>
        <a href="#" className="text-white hover:text-gray-300 px-2">Reviews</a>
        <a href="#" className="text-white hover:text-gray-300 px-2">FAQ</a>
      </nav>
      <p className="text-xs md:text-sm">Based in Montreal, Canada</p>
      <p className="text-xs md:text-sm">&copy; 2023 All rights reserved. Powered by RightHandsome, Inc.</p>
    </footer>
  );
}

export default Footer;
