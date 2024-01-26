import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import adminService from '../../../../Services/admin.service.js';
import Navbar from '../../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import NavBar from '../../../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../../Components/User_Components/MechanicDisplay.js';
import InvoiceBlock from './InvoiceBlock.js';



function Invoices() {

  const navigate = useNavigate();
  const [invoices, setInvoices] = useState([]);
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [publicContent, setPublicContent] = useState(null);
  const [message, setMessage] = useState('');

  useEffect(() => {
    getInvoices();
  }, []);

  function getInvoices() {
    adminService.getAllInvoices()
      .then(res => {
        if (res.status === 200) {
          setInvoices(res.data);
          setPublicContent(true);
        }
      })
      .catch(error => {
        console.log(error);
        setPublicContent(false);
        setMessage(error.response.data);
      });
  }

  function confirmDelete() {
    setShowConfirmation(true);
  }

  function cancelDelete() {
    setShowConfirmation(false);
  }

  
  const handlInvoiceClick = () => {
    navigate(`/admin/invoices/newInvoice`);
  };
  

  return (
    <div className="flex flex-col min-h-screen">
      {publicContent ? (
        <div>
          <Navbar />
          <div className="content">
            <div className="ml-5 mt-1">
              <MechanicDisplay />
            </div>
            <div className="w-4/5 rounded bg-gray-300 mx-auto mt-1 mb-5">
              <div className="flex p-2 bg-gray-300 w-full">
                <p className="text-2xl font-bold mx-auto">INVOICES</p>
                <div className="flex items-center space-x-4">
                  <div className="relative flex">
                    <input
                      className="w-full rounded border-gray-300 px-4 py-2 focus:outline-none focus:border-indigo-500"
                      type="text"
                      placeholder="Search..."
                    />
                    <span className="text-gray-400 cursor-pointer">&#128269;</span>
                  </div>
                  <button
                    className="text-white border-none px-4 py-2 rounded font-bold transition duration-300 hover:scale-110 bg-black"
                    onClick={handlInvoiceClick}
                  >
                    Add+
                  </button>
                </div>
              </div>
  
              <div className="w-full overflow-y-scroll max-h-[230px]">
                <table className="w-full table-auto">
                  <thead className="bg-white sticky top-0">
                    <tr>
                      <th>ID</th>
                      <th>CUSTOMER</th>
                      <th>APPOINTMENT</th>
                      <th>DATE & TIME</th>
                      <th>MECHANIC NOTES</th>
                      <th>SUM OF SERVICES (CAD)</th>
                    </tr>
                  </thead>
                  <tbody className="text-center">
                    {invoices.map((invoice) => (
                      <InvoiceBlock
                        key={invoice.invoiceId}
                        invoice={invoice}
                        refreshInvoices={getInvoices}
                      />
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <div className="mb-5">
            <img src="/invoices.svg" alt="invoice's Image" />
          </div>
        </div>
      ) : (
        <div className="flex-1 text-center">
          <NavBar />
          {publicContent === false ? <h1 className='text-4xl'>{message.status} {message.error} </h1> : 'Error'}
          {message && (
            <>
              <h3>{message.message}</h3>
            </>
          )}
        </div>
      )}
      <Footer />
    </div>
  );
}

export default Invoices;
