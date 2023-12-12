
import React, { useState, useEffect } from "react";
import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import "./CustomerVehicles.css";
import axios from "axios";
import { useParams } from 'react-router-dom';

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
        <>
            <Navbar />
            <div className="customer-details-content">
                <aside className="customer-details-mechanic-display">
                    <MechanicDisplay />
                    <Sidebar />
                </aside>
                <div className="vehicle-table">
                    <h2>Vehicle List</h2>
                    <table>
                        <thead>
                        <tr>
                            <th>Vehicle ID</th>
                            <th>Customer ID</th>
                            <th>Make</th>
                            <th>Model</th>
                            <th>Year</th>
                            <th>Transmission Type</th>
                            <th>Mileage</th>
                        </tr>
                        </thead>
                        <tbody>
                        {vehicles.map((vehicle) => (
                            <tr key={vehicle.vehicle_id}>
                                <td>{vehicle.vehicle_id}</td>
                                <td>{vehicle.customer_id}</td>
                                <td>{vehicle.make}</td>
                                <td>{vehicle.model}</td>
                                <td>{vehicle.year}</td>
                                <td>{vehicle.transmission_type}</td>
                                <td>{vehicle.mileage}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
            <Footer />
        </>
    );
}

export default CustomerVehicles;
