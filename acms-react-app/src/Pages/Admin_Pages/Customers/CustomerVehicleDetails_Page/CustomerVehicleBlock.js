import React, { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import authService from '../../../../Services/auth.service';

const CustomerVehicleBlock = ({ vehicle, customerId }) => {

  const [userRole, setUserRole] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const currentUser = authService.getCurrentUser();
    if (currentUser) {
      setUserRole(currentUser.roles);
    }
  }, []);

  function handleClick() {
    if (userRole) {
      let basePath = '/user'; // Default path for customers
      if (userRole.includes('ROLE_ADMIN')) {
        basePath = '/admin';
      }
      navigate(`${basePath}/customers/${customerId}/vehicles/${vehicle.vehicleId}`);
    }
  }

  return (
    <tr className='hover:bg-gray-200 hover:cursor-pointer' onClick={handleClick}>
      <td>{vehicle.vehicleId}</td>
      <td>{customerId}</td>
      <td>{vehicle.make}</td>
      <td>{vehicle.model}</td>
      <td>{vehicle.year}</td>
      <td>{vehicle.transmission_type}</td>
      <td>{vehicle.mileage}</td>
    </tr>
  );
};

export default CustomerVehicleBlock;