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

    const [publicContent, setPublicContent] = useState(true); // Define publicContent and its setter
    const [message, setMessage] = useState(''); // Define message and its setter
    const [showConfirmation, setShowConfirmation] = useState(false); // State to manage delete confirmation

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

        adminService.updateInvoice(invoiceId, invoiceDetails)
            .then(res => {
                if (res.status === 200) {
                    alert('Invoice has been updated!');
                    navigate(`/admin/invoices`);
                }
            })
            .catch(err => {
                console.error('Error updating invoice:', err);
            });

    }


    function confirmDelete() {
        setShowConfirmation(true);
    }

    function cancelDelete() {
        setShowConfirmation(false);
    }

    function deleteInvoice() {
        adminService.deleteInvoice(invoiceId)
            .then(res => {
                if (res.status === 204) {
                    alert('Invoice has been deleted!');
                    navigate(`/admin/invoices`);
                }
            })
            .catch(err => {
                console.error('Error deleting invoice:', err);
            });
        setShowConfirmation(false);
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

                        <button className="bg-blue-500 text-white px-4 py-2 rounded font-bold" type="submit"> Update Invoice </button>
                        <button className="bg-red-500 text-white px-4 py-2 rounded font-bold" type="button" onClick={confirmDelete}>
                        Delete Invoice
                        </button>
                    </form>
                        </div>
                        {showConfirmation && (
                                <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center z-50">
                                    <div className="bg-white p-5 rounded-lg shadow-lg">
                                        <p className="text-lg mb-2">Are you sure you want to delete this invoice?</p>
                                        <div className="flex justify-center space-x-4">
                                            <button className="bg-red-500 text-white px-4 py-2 rounded font-bold" onClick={deleteInvoice}>
                                                Yes
                                            </button>
                                            <button className="bg-gray-300 text-gray-700 px-4 py-2 rounded font-bold" onClick={cancelDelete}>
                                                No
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            )}
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
