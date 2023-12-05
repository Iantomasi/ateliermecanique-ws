import React from 'react';
import './NavBar.css';
import { Link } from 'react-router-dom';
import {  useNavigate } from 'react-router-dom';
import UserProfile from '../../User_Components/UserProfile';
import './NavBar.css';
function Navbar() {
  

  return (

<header>
    <nav className="navbar-container">

    <div className="left-links">
        <h1>atelier mecanique</h1>
    </div>
    {/* Middle-aligned links */}
    <div className="middle-links">
        <div className="logo-container">
            <img src="/logo.svg" alt="app logo"/>
        </div>
        <div className="links-container">
            <a href="#">About</a>
            <a href="#">Services</a>
            <a href="#">Contact</a>
        </div>
    </div>


    {/* Right-aligned links */}
    <div className="right-links">
        <UserProfile/>
    </div>
    </nav>
    <div className="divider"></div>
</header>
  );
}

export default Navbar;