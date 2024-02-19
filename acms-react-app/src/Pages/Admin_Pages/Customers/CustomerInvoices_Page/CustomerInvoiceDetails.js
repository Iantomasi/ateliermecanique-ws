import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import adminService from '../../../../Services/admin.service.js';
import Navbar from '../../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import NavBar from '../../../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../../../Components/Footer/Footer.js';
import Sidebar from '../../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import MechanicDisplay from '../../../../Components/User_Components/MechanicDisplay.js';
import customerService from '../../../../Services/customer.service.js';

function CustomerInvoiceDetails() {
    const { customerId, invoiceId } = useParams();
    const navigate = useNavigate();
    const [invoiceDetails, setInvoiceDetails] = useState({
        invoiceId: '',
        customerId: '',
        appointmentId: '',
        invoiceDate: '',
        mechanicNotes: '',
        sumOfServices: 0.0
    });

    const [publicContent, setPublicContent] = useState(true); // Define publicContent and its setter
    const [message, setMessage] = useState(''); // Define message and its setter
    const [showConfirmation, setShowConfirmation] = useState(false); // State to manage delete confirmation

    useEffect(() => {
        customerService.getCustomerInvoieById(customerId, invoiceId)
            .then(res => {
                if (res.status === 200) {
                    setPublicContent(true);
                    setInvoiceDetails(res.data);
                }
            })
            .catch(error => {
                setPublicContent(false);
                setMessage(error.response.data);
                console.error('Error fetching invoice details', error);
            });
    }, [invoiceId]);


    // Render the invoice details form
    return (
        <div className="flex flex-col min-h-screen">
            {publicContent ? (
                <div>
                    <Navbar/>
                    <div className="flex">
                    <div className="ml-5 mt-1">
              <MechanicDisplay />
        </div>
                        <main className="flex-grow p-5">
                            <div className="text-4xl font-bold text-center">
                                <p>INVOICE DETAILS</p>
                            </div>
                        <div className="bg-gray-100 shadow-lg p-5 rounded-md mt-5 relative">
                    <form className="customervehicle-user-details-form" >
                        <label className="font-bold">Invoice Id</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="invoiceId" value={invoiceDetails.invoiceId} type="text" required />

                        <label className="font-bold">Customer Id</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="customerId" value={invoiceDetails.customerId}  type="text" required />

                        <label className="font-bold">Appointment Id</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="appointment" value={invoiceDetails.appointmentId} type="text" required />

                        <label className="font-bold">Invoice Date</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="invoiceDate" value={invoiceDetails.invoiceDate}  type="text" required />

                        <label className="font-bold">Mechanic Notes</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="mechanicNotes" value={invoiceDetails.mechanicNotes}  placeholder='No mechanic notes'/>

                        <label className="font-bold">Sum Of Service</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="sumOfServices" value={invoiceDetails.sumOfServices} type="text" required />
                    </form>
                        </div>
                        </main>
                    </div>
                </div>
            ) : (
                <div className="flex-1 text-center">
                </div>
            )}
            <Footer />
        </div>
    );
}

export default CustomerInvoiceDetails;
