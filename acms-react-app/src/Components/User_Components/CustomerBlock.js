import React from 'react';


const CustomerBlock = ({customer}) => {
  return (
    <tr>
        <td>{customer.id}</td>
        <td>{customer.firstName}</td>
        <td>{customer.lastName}</td>
        <td>{customer.email}</td>
        <td>{customer.phoneNumber}</td>
    </tr>
  );
};

export default CustomerBlock;
