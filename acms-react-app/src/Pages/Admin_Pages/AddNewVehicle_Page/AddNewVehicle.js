import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import adminService from '../../../Services/admin.service.js';

function AddNewVehicle() {
    const {customerId} = useParams();
    const [vehicleDetails, setVehicleDetails] = useState({
        make: '',
        model: '',
        year: '',
        transmission_type: '',
        mileage: '',
    });

    const navigate = useNavigate()

    useEffect(() => {
        adminService.getCustomerById(customerId)
            .then(res => {
                if (res.status === 200) {
                    setVehicleDetails(res.data);
                }
            })
            .catch(error => {
                console.error("Error fetching customer details", error);
            });
    }, [customerId]);

    function handleInputChange(event) {
        const {name, value} = event.target;
        setVehicleDetails(prevState => ({
            ...prevState,
            [name]: value
        }));
    }

    function addVehicle(event) {
        event.preventDefault();

        const newVehicle = {
            customerId: customerId,
            make: vehicleDetails.make,
            model: vehicleDetails.model,
            year: vehicleDetails.year,
            transmission_type: vehicleDetails.transmission_type,
            mileage: vehicleDetails.mileage
        };

        adminService.addCustomerVehicle(customerId, newVehicle)
            .then(res => {
                if (res.status === 200) {
                    console.log("Vehicle has been successfully added!");
                    alert("Vehicle has been added!")
                    navigate(`/admin/customers/${customerId}/vehicles`);
                }
            })
            .catch(error => {
                console.error("Error adding new vehicle", error);
            });
    }
    return (
        <div>
            <Navbar />
            <div className="flex flex-col md:flex-row">
                <Sidebar customerId={customerId} />
                <main className="flex-grow p-5">
                    <div className="text-4xl font-bold text-center">
                        <p>VEHICLE FORM</p>
                    </div>
                    <div className="bg-gray-100 shadow-lg p-5 rounded-md mt-5 relative">
                        <form onSubmit={addVehicle}>
                            <label className="font-bold">Make</label>
                            <input className="w-full p-4 rounded border border-gray-400 mb-5" name="make" value={vehicleDetails.make}
                                   onChange={handleInputChange} type="text" required/>

                            <label className="font-bold">Model</label>
                            <input className="w-full p-4 rounded border border-gray-400 mb-5" name="model" value={vehicleDetails.model}
                                   onChange={handleInputChange} type="text" required/>

                            <label className="font-bold">Year</label>
                            <input className="w-full p-4 rounded border border-gray-400 mb-5" name="year" value={vehicleDetails.year}
                                   onChange={handleInputChange} type="text" required/>

                            <label className="font-bold">Transmission Type</label>
                            <select className="w-full p-4 rounded border border-gray-400 mb-5" name="transmission_type"
                                    value={vehicleDetails.transmission_type} onChange={handleInputChange} required>
                                <option value="">Select Transmission Type</option>
                                <option value="AUTOMATIC">Automatic</option>
                                <option value="MANUAL">Manual</option>
                            </select>

                            <label className="font-bold">Mileage</label>
                            <input className="w-full p-4 rounded border border-gray-400 mb-5" name="mileage" value={vehicleDetails.mileage}
                                   onChange={handleInputChange} type="text" required/>

                            <div className="flex justify-center">
                                <button className="bg-yellow-400 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" type="submit">Add Vehicle</button>
                            </div>
                        </form>
                    </div>
                    <div className="flex mt-5 justify-center">
                        <img src="/happy-young-man-and-car.png" alt="Yellow Car Side" className="w-full md:w-1/3 h-auto" />
                    </div>
                </main>
            </div>
            <Footer/>
        </div>
    );
}

export default AddNewVehicle;