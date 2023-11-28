import React from 'react';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';
import './Home.css';

function Home() {
    return (
      <div>
       <NavBar/>
        <main>
        <section class="hero">
            <div class="hero-content">
            <h1>Revitalize your ride with expert care.</h1>
            <button>Book now</button>
            </div>
            <div class="hero-image">
            <img src="car-repair.png" alt="Car Service" />
            </div>
        </section>
        <div class="reviews-container">
        .{/* REVIEWS ARE TO BE ADDED LATER ON*/}
        </div>
        </main>
       <Footer/>
      </div>
    );
  }

  export default Home;
  