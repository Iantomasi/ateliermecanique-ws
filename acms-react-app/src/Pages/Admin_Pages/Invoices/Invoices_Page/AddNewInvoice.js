import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../../Components/Footer/Footer.js';
import adminService from '../../../../Services/admin.service.js';
import CustomDateTimePicker from '../../../../Components/DateTimePicker/CustomDateTimePicker.js';
import dayjs from 'dayjs';

function AddNewInvoice() {
    const navigate = useNavigate();
    const [invoiceDetails, setInvoiceDetails] = useState({
        customerId: '',
        appointmentId: '',
        invoiceDate: '',
        mechanicNotes: '',
        sumOfServices: ''
    });
    const [customers, setCustomers] = useState([]);
    const [appointments, setAppointments] = useState([]);

    useEffect(() => {
        adminService.getAllCustomers()
            .then(response => {
                if (response.status === 200) {
                    setCustomers(response.data);
                }
            })
            .catch(error => console.error('Error fetching customers:', error));
    }, []);

    const handleCustomerSelect = (event) => {
        const selectedCustomerId = event.target.value;
        setInvoiceDetails({ ...invoiceDetails, customerId: selectedCustomerId, appointmentId: '' });

        if (selectedCustomerId) {
            adminService.getAllCustomerAppointments(selectedCustomerId)
                .then(response => {
                    if (response.status === 200) {
                        const confirmedAppointments = response.data.filter(appointment => appointment.status === 'CONFIRMED');
                        setAppointments(confirmedAppointments);
                    }
                })
                .catch(error => console.error('Error fetching appointments:', error));
        } else {
            setAppointments([]);
        }
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setInvoiceDetails({ ...invoiceDetails, [name]: value });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
    
        const { customerId, appointmentId, invoiceDate, mechanicNotes, sumOfServices } = invoiceDetails;
    
        if (!customerId || !appointmentId) {
            alert("Customer ID and Appointment ID are required.");
            return;
        }
    
        const invoiceData = {
            customerId,
            appointmentId,
            invoiceDate, // This is now a text field, ensure it's in the correct format "yyyy-MM-ddTHH:mm"
            mechanicNotes,
            sumOfServices,
        };
    
        adminService.addInvoice(customerId, invoiceData)
            .then(response => {
                if (response.status === 200) {
                    alert("Invoice Created");
                    navigate('/admin/invoices');
                }
            })
            .catch(error => {
                console.error("Error creating invoice:", error);
            });
    };

    return (
        <div className="flex flex-col min-h-screen">
            <Navbar />
            <div className="flex flex-col md:flex-row">
                <main className="flex-grow p-5">
                    <div className="text-4xl font-bold text-center">
                        <p>INVOICE FORM</p>
                    </div>
                    <div className="bg-gray-100 shadow-lg p-5 rounded-md mt-5 relative">
                        <form onSubmit={handleSubmit}>
                            <label className="font-bold">Customer</label>
                            <select 
                                name="customerId" 
                                value={invoiceDetails.customerId} 
                                onChange={handleCustomerSelect}
                                className="w-full p-4 rounded border border-gray-400 mb-5"
                                required
                            >
                                <option value="">Select a Customer</option>
                                {customers.map(customer => (
                                    <option key={customer.id} value={customer.id}>
                                        {customer.firstName} {customer.lastName}
                                    </option>
                                ))}
                            </select>

                            <label className="font-bold">Appointment</label>
                            <select 
                                name="appointmentId" 
                                value={invoiceDetails.appointmentId} 
                                onChange={handleInputChange}
                                className="w-full p-4 rounded border border-gray-400 mb-5"
                                required
                            >
                                <option value="">Select an Appointment</option>
                                {appointments.map(appointment => (
                                    <option key={appointment.id} value={appointment.id}>
                                        {appointment.appointmentId}
                                    </option>
                                ))}
                            </select>

                            <label className="font-bold">Invoice Date</label>
<CustomDateTimePicker
    value={invoiceDetails.invoiceDate}
    onChange={(date) => setInvoiceDetails({ ...invoiceDetails, invoiceDate: date })}
    className="w-full mb-5"
    required
/>

                            <label className="font-bold">Mechanic Notes</label>
                            <textarea 
                                name="mechanicNotes" 
                                value={invoiceDetails.mechanicNotes} 
                                onChange={handleInputChange} 
                                className="w-full p-4 rounded border border-gray-400 mb-5"
                                required
                            />

                            <label className="font-bold">Sum of Services</label>
                            <input 
                                type="number" 
                                name="sumOfServices" 
                                value={invoiceDetails.sumOfServices} 
                                onChange={handleInputChange} 
                                className="w-full p-4 rounded border border-gray-400 mb-5"
                                required
                            />

                            <div className="flex justify-center">
                                <button 
                                    className="bg-yellow-400 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" 
                                    type="submit"
                                >
                                    Add Invoice
                                </button>
                            </div>
                        </form>
                    </div>
                </main>
            </div>
            <Footer/>
        </div>
    );
}

export default AddNewInvoice;
