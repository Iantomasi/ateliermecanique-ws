import React from 'react';
import {useNavigate } from 'react-router-dom';
import adminService from '../../../../Services/admin.service';

const CustomerInvoiceBlock = ({ invoice, refreshCustomerInvoices }) => {
  const navigate = useNavigate();

  
  return (
    <tr className='hover:bg-gray-200 hover:cursor-pointer h-10'>
    <td>{invoice.invoiceId}</td>
    <td>{invoice.appointmentId}</td>
    <td>{invoice.invoiceDate}</td>
    <td>{invoice.mechanicNotes}</td>
    <td>{invoice.sumOfServices}</td>       
  </tr>
  );
};

export default CustomerInvoiceBlock;
