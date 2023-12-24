import React from 'react';
import NavBar from '../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../Components/Footer/Footer.js';

function Home() {
  return (
    <div className="flex flex-col min-h-screen">
      <NavBar />

      <main className="flex-grow flex flex-col justify-center">
        <section className="flex flex-col md:flex-row hero bg-white p-4 md:p-8">
          <div className="flex flex-col justify-center md:w-1/2 lg:w-2/5">
            <h1 className="text-3xl md:text-4xl lg:text-5xl">
              Revitalize your ride with expert care.
            </h1>
            <button className="text-base md:text-lg bg-yellow-400 py-2 px-6 font-bold rounded mt-4 md:mt-6 lg:mt-8 transform transition duration-300 hover:scale-110">
              Book now
            </button>
          </div>
          <div className="flex items-center justify-center md:w-1/2 lg:w-4/5">
            <img src="car-repair.png" alt="Car Service"/>
          </div>
        </section>
        <div className="reviews-container">
          {/* REVIEWS ARE TO BE ADDED LATER ON */}
        </div>
      </main>

      <Footer />
    </div>
  );
}

export default Home;
