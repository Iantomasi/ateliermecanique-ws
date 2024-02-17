import React from 'react';

function Footer() {
  return (
    <footer className="bg-black text-white p-4 md:p-6 text-center">
      <nav className="mb-4">
        <a href="#" className="text-white hover:text-gray-300 px-2">About Us</a>
        <a href="https://www.termsfeed.com/live/230f6f49-79b9-4846-8da7-efad79dfbcd1" className="text-white hover:text-gray-300 px-2">Privacy Policy</a>
        <a href="#" className="text-white hover:text-gray-300 px-2">Reviews</a>
        <a href="#" className="text-white hover:text-gray-300 px-2">FAQ</a>
      </nav>
      <p className="text-xs md:text-sm">Based in Montreal, Canada</p>
      <p className="text-xs md:text-sm">&copy; 2023 All rights reserved. Powered by RightHandsome, Inc. SPRINT4</p>
    </footer>
  );
}

export default Footer;
