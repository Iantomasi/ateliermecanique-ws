import React from 'react';
import './NavBar.css';

function Navbar() {
  return (
    <header>
        <nav>
            {/* Left-aligned links */}
            <div className="left-links">
                <a href="#">About</a>
                <a href="#">Services</a>
                <a href="#">Contact</a>
            </div>

            {/* Right-aligned links */}
            <div className="right-links">
                <a href="#">Login</a>
                <button className="signup-button">Sign up</button>
            </div>
        </nav>
    </header>
  );
}

export default Navbar;