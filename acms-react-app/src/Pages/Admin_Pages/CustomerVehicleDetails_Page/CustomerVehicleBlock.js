import React from 'react';
import { Link } from 'react-router-dom';

const CustomerVehicleBlock = ({ vehicle, customerId }) => {
  return (
    <tr>
      <td>
        <Link to={`/admin/customers/${customerId}/vehicles/${vehicle.vehicleId}`}>
          {vehicle.vehicleId}
        </Link>
      </td>
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