import React from 'react';
import {useNavigate } from 'react-router-dom';
import adminService from '../../../../Services/admin.service';

const InvoiceBlock = ({ invoice, refreshInvoices }) => {
    const navigate = useNavigate();


    const handleInvoiceClick = () => {
        navigate(`/admin/invoices/${invoice.invoiceId}`);
    };

    const handleCustomerClick = () => {
        navigate(`/admin/customers/${invoice.customerId}`);
    };

    const handleAppointmentClick = () => {
        navigate(`/admin/appointments/${invoice.appointmentId}`);
    };


  return (
    <>
      <tr className='hover:bg-gray-200 hover:cursor-pointer h-10'>
          <td className='hover:cursor-pointer' onClick={handleInvoiceClick}>
              {invoice.invoiceId}
          </td>
          <td className='hover:cursor-pointer' onClick={handleCustomerClick}>
              {invoice.customerId}
          </td>
          <td className='hover:cursor-pointer' onClick={handleAppointmentClick}>
              {invoice.appointmentId}
          </td>

        <td>{invoice.invoiceDate}</td>
        <td>{invoice.mechanicNotes}</td>
        <td>{invoice.sumOfServices}</td>       
      </tr>
    </>
  );
};
export default InvoiceBlock;
