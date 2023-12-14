import React, { useState, useEffect } from "react";
import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import "./CustomerVehicles.css";
import axios from "axios";
import { useParams } from 'react-router-dom';

import CustomerVehicleBlock  from '../CustomerVehicleDetails_Page/CustomerVehicleBlock.js';


function CustomerVehicles() {
    const { customerId } = useParams();
    const [vehicles, setVehicles] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/v1/customers/${customerId}/vehicles`)
            .then(res => {
                console.log('API response:', res);
                if (res.status === 200) {
                    setVehicles(res.data);
                }
            })
            .catch(error => {
                console.log(error);
            });
    }, [customerId]);

    return (
        <div>
            <Navbar />
            <div className="vehicle-details-content">
                <aside className="customer-details-mechanic-display">
                    <MechanicDisplay />
                    <Sidebar />
                </aside>
                <div className="vehicle-table">
                    <div className="vehicle-top-of-table">
                        <p>Vehicle List</p>
                        <div className="Search-box">
                            <input className="Search-box" type="text" placeholder="Search..."/>
                            <span className="search-icon">&#128269;</span>
                        </div>
                        <button className="add-button">Add+</button>
                    </div>
                    <div className="vehicle-table-content">
                        <table>
                            <thead>
                                <tr>
                                    <th>Vehicle ID</th>
                                    <th>Customer ID</th>
                                    <th>Make</th>
                                    <th>Model</th>
                                    <th>Year</th>
                                    <th>Transmission</th>
                                    <th>Mileage</th>
                                </tr>
                            </thead>
                            <tbody>
                                {vehicles.map(vehicle => (
                                    <CustomerVehicleBlock key={vehicle.vehicleId} vehicle={vehicle} customerId={customerId} />
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
}

export default CustomerVehicles;