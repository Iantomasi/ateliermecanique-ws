import React from 'react';
import NavBar from '../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import HomeOption from '../../Components/General_Components/HomeOption.js';

import './Admin.css';


function Admin() {
    return (
      <div>
       <NavBar/>
        <div className="content">
            <div className="hand">
                <img src="waveHand.svg" alt="hand"/>
            </div>
            
            <h2 className="header">Welcome Admin!</h2>
            
            <div className="options-section">
                <div className="top">
                    <div className="left">
                        <HomeOption src="customersImage.svg" label="customers"/>
                    </div>
                    <div className="right">
                        <HomeOption src="appointments.svg" label="appointments"/>
                    </div>
                </div>
                <div className="bottom">
                    <div className="left">
                        <HomeOption src="invoices.svg" label="invoices"/>
                    </div>
                    <div className="right">
                        <HomeOption src="reviews.svg" label="reviews"/>
                    </div>
                </div>
            </div>
        </div>
       <Footer/>
      </div>
    );
  }

  export default Admin;
  