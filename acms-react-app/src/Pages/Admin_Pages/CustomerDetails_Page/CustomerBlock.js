import React from 'react';
import { Link } from 'react-router-dom';

const CustomerBlock = ({ customer }) => {
  console.log(customer); 
  return (
    <tr>
     <td>
        <Link to={`/admin/customers/${customer.customerId}`}>
          {customer.customerId}
        </Link>
      </td>
      <td>{customer.firstName}</td>
      <td>{customer.lastName}</td>
      <td>{customer.email}</td>
      <td>{customer.phoneNumber}</td>
    </tr>
  );
}

export default CustomerBlock;