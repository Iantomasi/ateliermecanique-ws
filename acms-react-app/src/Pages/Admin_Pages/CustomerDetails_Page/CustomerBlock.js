import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

const CustomerBlock = ({ customer }) => {
  console.log(customer); 

  const navigate = useNavigate()

  function handleClick() {
    navigate(`/admin/customers/${customer.customerId}`)
  }
  return (
    
      <tr className='hover:bg-gray-200 hover:cursor-pointer h-10' onClick={handleClick}>
      <td>
            {customer.customerId}

        </td>
        <td>{customer.firstName}</td>
        <td>{customer.lastName}</td>
        <td>{customer.email}</td>
        <td>{customer.phoneNumber}</td>
        
      </tr>
    
  );
}

export default CustomerBlock;
