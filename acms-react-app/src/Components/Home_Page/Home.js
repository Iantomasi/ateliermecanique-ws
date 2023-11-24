import React from 'react';
import NavBar from '../Navigation_Bar/NavBar.js'
import Footer from '../Footer/Footer.js'
import './Home.css';

function Home() {
    return (
      <div className="Home">
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
  