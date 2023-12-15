import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import './AddNewVehicle.css';

function AddNewVehicle() {
    const {customerId} = useParams();
    const [vehicleDetails, setVehicleDetails] = useState({
        make: '',
        model: '',
        year: '',
        transmissionType: '',
        mileage: '',
    });

    const navigate = useNavigate()

    useEffect(() => {
        axios.get(`http://localhost:8080/api/v1/customers/${customerId}`)
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
            transmissionType: vehicleDetails.transmissionType,
            mileage: vehicleDetails.mileage
        };

        axios.post(`http://localhost:8080/api/v1/customers/${customerId}/vehicles`, newVehicle)
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
        <div className="add-vehicle-page">
            <Navbar/>
            <div className="add-vehicle-content">
                <aside className="add-vehicle-mechanic-display">
                    <MechanicDisplay/>
                    <Sidebar customerId={customerId}/>
                </aside>
                <main className="add-vehicle-main">
                    <div className="add-vehicle-top-of-form">
                        <p>ADD NEW VEHICLE</p>
                    </div>
                    <div className="add-vehicle-form-container">
                        <form className="vehicle-details-form" onSubmit={addVehicle}>
                            <label>Make</label>
                            <input className="input-field" name="make" value={vehicleDetails.make}
                                   onChange={handleInputChange} type="text" required/>

                            <label>Model</label>
                            <input className="input-field" name="model" value={vehicleDetails.model}
                                   onChange={handleInputChange} type="text" required/>

                            <label>Year</label>
                            <input className="input-field" name="year" value={vehicleDetails.year}
                                   onChange={handleInputChange} type="text" required/>

                            <label>Transmission Type</label>
                            <select className="input-field" name="transmissionType"
                                    value={vehicleDetails.transmissionType} onChange={handleInputChange} required>
                                <option value="">Select Transmission Type</option>
                                <option value="AUTOMATIC">Automatic</option>
                                <option value="MANUAL">Manual</option>
                            </select>

                            <label>Mileage</label>
                            <input className="input-field" name="mileage" value={vehicleDetails.mileage}
                                   onChange={handleInputChange} type="text" required/>

                            <div className="button-group">
                                <button className="save-button" type="submit">Add Vehicle</button>
                            </div>
                        </form>
                    </div>
                </main>
            </div>
            <Footer/>
        </div>
    );
}

export default AddNewVehicle;