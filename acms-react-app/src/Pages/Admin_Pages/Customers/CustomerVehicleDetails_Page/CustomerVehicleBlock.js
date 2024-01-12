import React from 'react';
import { useNavigate } from 'react-router-dom';

const CustomerVehicleBlock = ({ vehicle, customerId }) => {

  const navigate = useNavigate()

  function handleClick () {
    navigate(`/admin/customers/${customerId}/vehicles/${vehicle.vehicleId}`)
  }
  return (
    <tr className='hover:bg-gray-200 hover:cursor-pointer' onClick={handleClick}>
      <td>{vehicle.vehicleId}</td>
      <td>{vehicle.customerId}</td>
      <td>{vehicle.make}</td>
      <td>{vehicle.model}</td>
      <td>{vehicle.year}</td>
      <td>{vehicle.transmission_type}</td>
      <td>{vehicle.mileage}</td>
    </tr>
  );
};

export default CustomerVehicleBlock;