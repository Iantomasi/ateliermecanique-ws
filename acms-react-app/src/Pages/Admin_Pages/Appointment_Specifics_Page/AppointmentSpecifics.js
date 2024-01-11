import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';

function AppointmentDetails() {
    const { appointmentId } = useParams();
    const [appointmentDetails, setAppointmentDetails] = useState({
        customerId: '',
        vehicleId: '',
        appointmentDate: '',
        services: '',
        comments: '',
        status: ''
    });

    const navigate = useNavigate();

    useEffect(() => {
        axios.get(`http://localhost:8080/api/v1/appointments/${appointmentId}`)
            .then(res => {
                if (res.status === 200) {
                    setAppointmentDetails(res.data);
                }
            })
            .catch(error => {
                console.error("Error fetching appointment details", error);
            });
    }, [appointmentId]);


    if (!appointmentDetails) {
        return <div>Loading...</div>;
    }

    const handleBackButtonClick = () => {
        navigate('/admin/appointments'); // Use navigate to navigate back to the list of all appointments page
    };
    return (
        <div className="appointment-details-page">
            <Navbar />
            <div className="flex">
                <Sidebar appointmentId={appointmentId} />
                <main className="flex-grow p-5">

<<<<<<< HEAD
                    <button
                        className="mr-5 text-blue-500 hover:underline"
                        onClick={handleBackButtonClick}
                    >
                        Back
                    </button>

                    <p className="text-4xl font-bold text-center">APPOINTMENT DETAILS</p>
=======
                        <button
                            className="mr-5 text-blue-500 hover:underline"
                            onClick={handleBackButtonClick}
                        >
                            Back
                        </button>

                        <p className="text-4xl font-bold text-center">APPOINTMENT DETAILS</p>
>>>>>>> d9a826c (Rebase from origin)

                    {appointmentDetails && (
                        <div className="bg-gray-100 shadow-lg p-5 rounded-md mt-5 relative">
                            <div>
                                <label className='font-bold'>Customer ID</label>
                                <input className="w-full p-4 rounded border border-gray-400 mb-5" value={appointmentDetails.customerId} type="text" readOnly />
                            </div>
                            <div>
                                <label className='font-bold'>Vehicle ID</label>
                                <input className="w-full p-4 rounded border border-gray-400 mb-5" value={appointmentDetails.vehicleId} type="text" readOnly />
                            </div>
                            <div>
                                <label className='font-bold'>Appointment Date (YYYY-MM-DD H:MM)</label>
                                <input className="w-full p-4 rounded border border-gray-400 mb-5" value={appointmentDetails.appointmentDate} type="text" readOnly />
                            </div>
                            <div>
                                <label className='font-bold'>Services</label>
                                <input className="w-full p-4 rounded border border-gray-400 mb-5" value={appointmentDetails.services} type="text" readOnly />
                            </div>
                            <div>
                                <label className='font-bold'>Comments</label>
                                <input className="w-full p-4 rounded border border-gray-400 mb-5" value={appointmentDetails.comments} type="text" readOnly />
                            </div>
                            <div>
                                <label className='font-bold'>Status</label>
                                <input className="w-full p-4 rounded border border-gray-400 mb-5" value={appointmentDetails.status} type="text" readOnly />
                            </div>
                        </div>
                    )}
                </main>
            </div>
            <Footer />
        </div>
    );
}

export default AppointmentDetails;
