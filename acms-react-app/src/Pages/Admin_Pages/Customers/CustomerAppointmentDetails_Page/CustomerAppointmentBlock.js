import React from 'react';
import {useNavigate } from 'react-router-dom';
import adminService from '../../../../Services/admin.service';

const CustomerAppointmentBlock = ({ appointment, refreshCustomerAppointments }) => {
  const navigate = useNavigate();
  const handleStatusChange = (isConfirm) => {
    adminService.updateAppointmentStatus(appointment.appointmentId, isConfirm)
      .then(response => {
        refreshCustomerAppointments();
      })
      .catch(error => {
        console.error('Error updating the appointment status:', error);
      });
  };

  const handleCustomerAppointmentClick = () => {
    navigate(`/admin/customers/${appointment.customerId}/appointments/${appointment.appointmentId}`);
  };

  return (
    <tr className='hover:bg-gray-200 hover:cursor-pointer h-10'>
     <td className='hover:cursor-pointer' onClick={handleCustomerAppointmentClick}>
              {appointment.appointmentId}
      </td>
      <td>{appointment.appointmentDate}</td>
      <td>{appointment.services}</td>
      <td>{appointment.vehicleId}</td>
      <td>{appointment.status}</td>
      {appointment.status === 'PENDING' && (
        <>
          <td>
            <button
              onClick={() => handleStatusChange(true)}
              className="px-4 py-2 bg-yellow-400 rounded hover:bg-yellow-500 focus:outline-none focus:ring focus:ring-gray-200"
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
      )}
    </tr>
  );
};

export default CustomerAppointmentBlock;
