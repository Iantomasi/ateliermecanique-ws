import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import adminService from '../../../../Services/admin.service.js';
import Navbar from '../../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import NavBar from '../../../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import Footer from '../../../../Components/Footer/Footer.js';
import Sidebar from '../../../../Components/Navigation_Bars/Sidebar/Sidebar.js';

function InvoiceDetails() {
    const { invoiceId } = useParams();
    const navigate = useNavigate();
    const [invoiceDetails, setInvoiceDetails] = useState({
        invoiceId: '',
        customerId: '',
        appointmentId: '',
        invoiceDate: '',
        mechanicNotes: '',
        sumOfServices: 0.0
    });

    const [showConfirmation, setShowConfirmation] = useState(false); // If needed for delete confirmation
    const [publicContent, setPublicContent] = useState(null); // Define publicContent and its setter
    const [message, setMessage] = useState(''); // Define message and its setter

    // ... other state variables like showConfirmation, publicContent, message ...

    useEffect(() => {
        adminService.getInvoiceById(invoiceId)
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

    function handleInputChange(event) {
        const { name, value } = event.target;
        setInvoiceDetails(prevState => ({
            ...prevState,
            [name]: value
        }));
    }

    function updateInvoice(event) {
        event.preventDefault();

        // Update the invoice with the new details
        // Similar to the updateCustomerVehicle function but for invoices
    }

    function deleteInvoice() {
        // Function to delete the invoice
        // Similar to the executeDelete function but for invoices
    }

    // Render the invoice details form
    return (
        <div className="flex flex-col min-h-screen">
            {publicContent ? (
                <div>
                    <Navbar />
                    <div className="flex flex-col md:flex-row">
                        <Sidebar />
                        <main className="flex-grow p-5">
                            <div className="text-4xl font-bold text-center">
                                <p>INVOICE DETAILS</p>
                            </div>
                    {/* Rest of the component layout */}
                        <div className="bg-gray-100 shadow-lg p-5 rounded-md mt-5 relative">
                    <form className="customervehicle-user-details-form" onSubmit={updateInvoice}>
                        <label className="font-bold">Invoice Id</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="invoiceId" value={invoiceDetails.invoiceId} onChange={handleInputChange} type="text" required />

                        <label className="font-bold">Customer Id</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="customerId" value={invoiceDetails.customerId} onChange={handleInputChange} type="text" required />

                        <label className="font-bold">Appointment Id</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="appointment" value={invoiceDetails.appointmentId} onChange={handleInputChange} type="text" required />

                        <label className="font-bold">Invoice Date</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="invoiceDate" value={invoiceDetails.invoiceDate} onChange={handleInputChange} type="text" required />

                        <label className="font-bold">Mechanic Notes</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="mechanicNotes" value={invoiceDetails.mechanicNotes} onChange={handleInputChange} type="text" required />

                        <label className="font-bold">Sum Of Service</label>
                        <input className="w-full p-4 rounded border border-gray-400 mb-5" name="sumOfServices" value={invoiceDetails.sumOfServices} onChange={handleInputChange} type="text" required />
                    </form>
                        </div>
                    {/* Delete confirmation and other UI elements */}
                        </main>
                </div>
                </div>
            ) : (
                <div className="flex-1 text-center">
                    {/* Error handling UI */}
                </div>
            )}
            <Footer />
        </div>
    );
}

export default InvoiceDetails;
