import React from 'react';
import './NavBar.css';
import { Link } from 'react-router-dom';
import {  useNavigate } from 'react-router-dom';


function Navbar() {
    const navigate = useNavigate();

    const navigateToSignup = () => {
        navigate('/signup');
    };


  return (
    <header>
      <nav className="navbar-container">

        <div className="left-links">
        <img src="logo.svg" alt="app logo"/>
          <h1>atelier mecanique</h1>
        </div>
        {/* Middle-aligned links */}
        <div className="middle-links">
          <a href="#">About</a>
          <a href="#">Services</a>
          <a href="#">Contact</a>
        </div>

        {/* Right-aligned links */}
        <div className="right-links">
          <Link to="/login">Login</Link>
          <button className="signup-button" onClick={navigateToSignup}>Sign up</button>
        </div>
      </nav>
      <div className="divider"></div>
    </header>
  );
}

export default Navbar;