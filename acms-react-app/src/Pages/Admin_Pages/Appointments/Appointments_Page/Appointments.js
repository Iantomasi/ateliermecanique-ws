import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import adminService from '../../../../Services/admin.service.js';
import Navbar from '../../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import NavBar from '../../../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../../Components/User_Components/MechanicDisplay.js';
import AppointmentBlock from './AppointmentBlock.js';



function Appointments() {
  const [appointments, setAppointments] = useState([]);
  const [showConfirmation, setShowConfirmation] = useState(false);
  const hasCancelledAppointments = appointments.some(appointment => appointment.status === 'CANCELLED');
  const navigate = useNavigate();
  const [publicContent, setPublicContent] = useState(null);
  const [message, setMessage] = useState('');

  useEffect(() => {
    getAppointments();
  }, []);

  function getAppointments() {
    adminService.getAllAppointments()
      .then(res => {
        if (res.status === 200) {
          setAppointments(res.data);
          setPublicContent(true);
        }
      })
      .catch(error => {
        console.log(error);
        setPublicContent(false);
        setMessage(error.response.data);
      });
  }

  function confirmDelete() {
    setShowConfirmation(true);
  }

  function cancelDelete() {
    setShowConfirmation(false);
  }

  const handleAddAppointmentClick = () => {
    navigate(`/admin/appointments/newAppointment`);
  };

  function executeDeleteAllCancelledAppointments() {
    adminService.deleteAllCancelledAppointments()
      .then(res => {
        if (res.status === 204) {
          alert('Cancelled Appointments have been deleted!');
          setShowConfirmation(false);
          getAppointments(); // Fetch appointments again after deletion
        }
      })
      .catch(err => {
        console.error('Error deleting cancelled appointments:', err);
        setShowConfirmation(false);
      });
  }

  return (
    <div className="flex flex-col min-h-screen">
      {publicContent ? (
        <div>
          <Navbar />
          <div className="content">
            <div className="ml-5 mt-1">
              <MechanicDisplay />
            </div>
            <div className="w-4/5 rounded bg-gray-300 mx-auto mt-1 mb-5">
              <div className="flex p-2 bg-gray-300 w-full flex-wrap">
                {/* Responsive font size for "APPOINTMENTS" text */}
                <p className="text-xl sm:text-2xl font-bold mx-auto">APPOINTMENTS</p>
                <div className="flex items-center space-x-4 flex-wrap">
                  <div className="relative flex">
                    <input
                        className="w-full rounded border-gray-300 px-4 py-2 focus:outline-none focus:border-indigo-500"
                        type="text"
                        placeholder="Search..."
                    />
                    <span className="text-gray-400 cursor-pointer">&#128269;</span>
                  </div>
                  <button
                    className="text-white border-none px-4 py-2 rounded font-bold transition duration-300 hover:scale-110 bg-black"
                    onClick={handleAddAppointmentClick}
                  >
                    Add+
                  </button>
                  {hasCancelledAppointments && (
                      <button
                          className="bg-red-500 border-none px-2 sm:px-4 py-1 sm:py-2 rounded font-bold transform transition duration-300 hover:scale-110 ml-auto"
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
                      <th></th> {/* Don't delete for table structure */}
                      <th></th> {/* Don't delete for table structure */}
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
          </div>
          <div class="flex justify-center">
  <img src="/appointments.svg" alt="appointment's Image" />
</div>


        </div>
      ) : (
        <div className="flex-1 text-center">
          <NavBar />
          {publicContent === false ? <h1 className='text-4xl'>{message.status} {message.error} </h1> : 'Error'}
          {message && (
            <>
              <h3>{message.message}</h3>
            </>
          )}
        </div>
      )}
      <Footer />
  
      {/* Confirmation Model */}
      {showConfirmation && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded-lg">
            <p className="text-xl font-bold mb-4">Are you sure you want to delete all cancelled appointments?</p>
            <div className="flex justify-end">
              <button
                className="px-4 py-2 bg-yellow-400 rounded hover:bg-yellow-500 focus:outline-none focus:ring focus:ring-gray-200 mr-1"
                onClick={cancelDelete}
              >
                No
              </button>
              <button
                className="px-4 py-2 mr-2 bg-red-500 rounded text-white hover:bg-red-600 focus:outline-none focus:ring focus:ring-red-200 ml-1"
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
    </div>
  );
}

export default Appointments;
