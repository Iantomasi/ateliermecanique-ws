import React from 'react';

const Footer = () => {
    return (
        <footer className="bg-black text-white p-4 md:p-6 text-center">
            <nav className="mb-4 flex justify-center space-x-2">
                <a href="#" className="hover:text-gray-300 px-2">About Us</a>
                <a href="https://www.termsfeed.com/live/230f6f49-79b9-4846-8da7-efad79dfbcd1" className="hover:text-gray-300 px-2">Privacy Policy</a>
                <a href="#" className="hover:text-gray-300 px-2">Reviews</a>
                <a href="#" className="hover:text-gray-300 px-2">FAQ</a>
            </nav>
            <div className="text-xs md:text-sm">
                <p>Based in Montreal, Canada</p>
                <p>&copy; 2023 All rights reserved. Powered by RichNHandsome, Inc.</p>
            </div>
        </footer>
    );
}

export default Footer;
