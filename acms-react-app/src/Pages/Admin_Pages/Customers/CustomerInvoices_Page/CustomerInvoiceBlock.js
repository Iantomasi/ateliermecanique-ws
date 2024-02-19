import React, { useEffect, useState } from 'react';
import {useNavigate } from 'react-router-dom';
import adminService from '../../../../Services/admin.service';
import customerService from '../../../../Services/customer.service';
import authService from '../../../../Services/auth.service';

const CustomerInvoiceBlock = ({ invoice, customerId, refreshCustomerInvoices }) => {
  const navigate = useNavigate();
  const [userRole, setUserRole] = useState(null);

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
      navigate(`${basePath}/customers/${invoice.customerId}/invoices/${invoice.invoiceId}`);
    }
  }

  
  return (
    <tr className='hover:bg-gray-200 hover:cursor-pointer h-10'>
 <td className='hover:cursor-pointer' onClick={handleClick}>
              {invoice.invoiceId}
      </td>    
      <td>{invoice.appointmentId}</td>
    <td>{invoice.invoiceDate}</td>
    <td>{invoice.mechanicNotes}</td>
    <td>{invoice.sumOfServices}</td>       
  </tr>
  );
};

export default CustomerInvoiceBlock;
