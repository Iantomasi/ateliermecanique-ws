import React, { useState, useEffect } from "react";
import Navbar from '../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../Components/Footer/Footer.js';
import MechanicDisplay from '../../../Components/User_Components/MechanicDisplay.js';
import Sidebar from '../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import "./CustomerVehicles.css";
import axios from "axios";
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

<<<<<<< HEAD

import CustomerVehicleBlock  from '../CustomerVehicleDetails_Page/CustomerVehicleBlock.js';

=======
>>>>>>> 0bbc5e0690ad55cfcdba4dc945376bac24b283cb

function CustomerVehicles() {
    const { customerId } = useParams();
    const [vehicles, setVehicles] = useState([]);
    const navigate = useNavigate();

    const handleCustomerClick = (customerId) => {
        navigate(`/admin/customers/${customerId}/vehicles/newVehicle`);
        console.log(customerId);
    };

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
                        <p>VEHICLE LIST</p>
                        <div className="Search-box">
                            <input className="Search-box" type="text" placeholder="Search..."/>
                            <span className="search-icon">&#128269;</span>
                        </div>
                        <button
                            className="add-button"
                            onClick={() => handleCustomerClick(customerId)}
<<<<<<< HEAD
                        >Add+
=======
                        >
                            Add+
>>>>>>> 0bbc5e0690ad55cfcdba4dc945376bac24b283cb
                        </button>

                    </div>
                    <div className="vehicle-table-content">
                        <table>
                            <thead>
                                <tr>
                                    <th>VEHICLE ID</th>
                                    <th>CUSTOMER ID</th>
                                    <th>MAKE</th>
                                    <th>MODEL</th>
                                    <th>YEAR</th>
                                    <th>TRANSMISSION</th>
                                    <th>MILEAGE</th>
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