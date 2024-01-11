import React, { useState, useEffect } from 'react';
import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';
import axios from 'axios';
import AppointmentBlock from '../AppointmentDetails_Page/AppointmentBlock.js';

function Appointments() {
  const [appointments, setAppointments] = useState([]);
  const [showConfirmation, setShowConfirmation] = useState(false);
  const hasCancelledAppointments = appointments.some(appointment => appointment.status === 'CANCELLED');
<<<<<<< HEAD

=======
>>>>>>> f213da3 (Implemented front end functionailty)

  useEffect(() => {
    getAppointments();
  }, []);

  function getAppointments() {
    axios.get("http://localhost:8080/api/v1/appointments")
      .then(res => {
        console.log('API response:', res);
        if (res.status === 200) {
          setAppointments(res.data);
        }
      })
      .catch(error => {
        console.log(error);
<<<<<<< HEAD
      })      
=======
      });
>>>>>>> f213da3 (Implemented front end functionailty)
  }

  function confirmDelete() {
    setShowConfirmation(true);
  }

  function cancelDelete() {
    setShowConfirmation(false);
  }

  function executeDeleteAllCancelledAppointments() {
    axios.delete("http://localhost:8080/api/v1/appointments/cancelled")
      .then(res => {
        if (res.status === 204) {
          alert('Cancelled Appointments have been deleted!');
          setShowConfirmation(false);
          getAppointments(); // Fetch appointments again after deletion
          //navigate(/admin/appointments);
        }
      })
      .catch(err => {
        console.error('Error deleting cancelled appointments:', err);
        setShowConfirmation(false);
      });
  }

  return (
    <div>
      <Navbar />
      <div className="content">
        <div className="ml-5 mt-1">
          <MechanicDisplay />
        </div>
        <div className="w-4/5 rounded bg-gray-300 mx-auto mt-1 mb-5">
          <div className="flex p-2 bg-gray-300 w-full">
            <p className="text-2xl font-bold mx-auto">APPOINTMENTS</p>
            <div className="flex items-center space-x-4">
              <div className="relative flex">
                <input className="w-full rounded border-gray-300 px-4 py-2 focus:outline-none focus:border-indigo-500" type="text" placeholder="Search..." />
                <span className="text-gray-400 cursor-pointer">&#128269;</span>
              </div>
              <button className="text-white border-none px-4 py-2 rounded font-bold transition duration-300 hover:scale-110 bg-black">
                Add+
              </button>
              {hasCancelledAppointments && (
                <button
                  className="bg-red-500 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110 ml-auto"
                  onClick={confirmDelete}
                  type="button"
                >
                  Delete All Cancelled
                </button>
              )}
            </div>
          </div>

          <div className="w-full overflow-y-scroll max-h-[230px]">
            <table className="w-full table-auto">
              <thead className="bg-white sticky top-0">
                <tr>
                  <th>ID</th>
                  <th>DATE & TIME</th>
                  <th>SERVICES</th>
                  <th>VEHICLE</th>
                  <th>CUSTOMER</th>
                  <th>STATUS</th>
                  <th></th> {/* Don't del for table structure*/}
                  <th></th> {/* Don't del for table structure*/}
                </tr>
              </thead>
              <tbody className="text-center">
                {appointments.map((appointment) => (
                  <AppointmentBlock
                    key={appointment.appointmentId}
                    appointment={appointment}
                    refreshAppointments={getAppointments}
                  />
                ))}
              </tbody>
            </table>
          </div>
        </div>
        <div className="mb-5">
          <img src="/appointments.svg" alt="appointment's Image" />
        </div>
      </div>
<<<<<<< HEAD
=======

>>>>>>> f213da3 (Implemented front end functionailty)
      {/* Confirmation Modal */}
      {showConfirmation && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded-lg">
            <p className="text-xl font-bold mb-4">Are you sure you want to delete all cancelled appointments?</p>
            <div className="flex justify-end">
              <button
                className="text-white bg-red-500 border-none px-4 py-2 rounded font-bold mr-2"
                onClick={cancelDelete}
              >
                No
              </button>
              <button
                className="text-white bg-green-500 border-none px-4 py-2 rounded font-bold"
                onClick={() => {
                  executeDeleteAllCancelledAppointments();
                }}
              >
                Yes
              </button>
            </div>
          </div>
        </div>
      )}
      <Footer />
    </div>
  );
}

export default Appointments;