import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

const AppointmentBlock = ({ appointment }) => {
  console.log(appointment); 

  const navigate = useNavigate()

  function handleClick() {
    navigate(`/admin/appointment/${appointment.appointmentId}`)
    navigate(`/admin/customers/${appointment.customerId}`)
    navigate(`/admin/vehicles/${appointment.vehicleId}`)
  }
  return (
    
      <tr className='hover:bg-gray-200 hover:cursor-pointer h-10' onClick={handleClick}>
      
      <td>{appointment.appointmentId}</td>

      <td>{appointment.appointmentDate}</td>
      <td>{appointment.services}</td>
      <td>{appointment.vehicleId}</td>
      <td>{appointment.customerId}</td>
      <td>{appointment.status}</td>
      </tr>
    
  );
}

export default AppointmentBlock;
