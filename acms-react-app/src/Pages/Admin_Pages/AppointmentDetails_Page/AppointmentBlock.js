import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';




const AppointmentBlock = ({ appointment, refreshAppointments }) => {
  const handleStatusChange = (isConfirm) => {
    axios.put(`http://localhost:8080/api/v1/appointments/${appointment.appointmentId}/updateStatus?isConfirm=${isConfirm}`)
      .then(response => {
        refreshAppointments();
      })
      .catch(error => {
        console.error('Error updating the appointment status:', error);
      });
  };

  return (
    <>
      <tr className='hover:bg-gray-200 hover:cursor-pointer h-10'>
        <td>{appointment.appointmentId}</td>
        <td>{appointment.appointmentDate}</td>
        <td>{appointment.services}</td>
        <td>{appointment.vehicleId}</td>
        <td>{appointment.customerId}</td>
        <td>{appointment.status}</td>
        {/* Separate cells for buttons if status is PENDING, otherwise an empty cell */}
        {appointment.status === 'PENDING' ? (
          <>
            <td>
              <button
                onClick={() => handleStatusChange(true)}
                className="px-4 py-2 bg-yellow-400 rounded  hover:bg-yellow-500 focus:outline-none focus:ring focus:ring-gray-200"
              >
                Confirm
              </button>
            </td>
            <td>
              <button
                onClick={() => handleStatusChange(false)}
                className="px-4 py-2 mr-2 bg-red-500 rounded text-white hover:bg-red-600 focus:outline-none focus:ring focus:ring-red-200"
              >
                Cancel
              </button>
            </td>
          </>
        ) : (
          <td colSpan="2"></td> // Empty cells to maintain the table structure
        )}
      </tr>
    </>
  );
};

export default AppointmentBlock;
