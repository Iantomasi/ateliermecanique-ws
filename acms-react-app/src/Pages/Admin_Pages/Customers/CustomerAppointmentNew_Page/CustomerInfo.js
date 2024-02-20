import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom'; // Import useParams
import adminService from '../../../../Services/admin.service';

function CustomerInfo({ updateCustomerId, updateVehicleId }) {
    const [customers, setCustomers] = useState([]);
    const [selectedCustomerId, setSelectedCustomerId] = useState('');
    const [selectedVehicleId, setSelectedVehicleId] = useState('');
    const [vehicles, setVehicles] = useState([]);

    // Extract customerId from URL
    const { customerId } = useParams();

    useEffect(() => {
        adminService.getAllCustomers()
            .then(response => {
                if(response.status === 200){
                    setCustomers(response.data);
                    // If a customerId is found in the URL, use it as the selected customer
                    if (customerId) {
                        setSelectedCustomerId(customerId);
                        updateCustomerId(customerId); // Update the parent component or context with the customerId
                        // Fetch vehicles for the selected customer
                        adminService.getAllCustomerVehicles(customerId)
                            .then(response => {
                                if (response.status === 200) {
                                    setVehicles(response.data);
                                }
                            })
                            .catch(error => console.error('Error fetching vehicles:', error));
                    }
                }
            })
            .catch(error => console.error('Error fetching customers:', error));
    }, [customerId]); // Include customerId in the dependency array

    const handleCustomerSelect = (event) => {

        // event.preventDefault();
        // const customerId = event.target.value;
        // setSelectedCustomerId(customerId);

        if (customerId) {
            adminService.getAllCustomerVehicles(customerId)
                .then(response => {
                    if (response.status === 200) {
                        setVehicles(response.data);
                    }
                })
                .catch(error => console.error('Error fetching vehicles:', error));
        } else {
            setVehicles([]);
        }
    };

    adminService.getAllCustomerVehicles(customerId)
        .then(response => {
            if (response.status === 200) {
                setVehicles(response.data);
            }
        })
        .catch(error => console.error('Error fetching vehicles:', error));
    const handleVehicleSelect = (event) => {
        const vehicleId = event.target.value;
        setSelectedVehicleId(vehicleId);
        updateVehicleId(vehicleId);
    };

    return (
        <div>
            {/*<div>*/}
            {/*    <label htmlFor="customer-select">Customer</label>*/}
            {/*    <select*/}
            {/*        id="customer-select"*/}
            {/*        onChange={handleCustomerSelect}*/}
            {/*        value={selectedCustomerId || ''}*/}
            {/*        style={{ minWidth: '100%', padding: '0.5rem' }}*/}
            {/*        required*/}
            {/*    >*/}
            {/*        <option key="default-customer" value="">Select a Customer</option>*/}
            {/*        {customers.map((customer) => (*/}
            {/*            <option key={customer.id} value={customer.id}>*/}
            {/*                {customer.firstName} {customer.lastName}*/}
            {/*            </option>*/}
            {/*        ))}*/}
            {/*    </select>*/}
            {/*</div>*/}
            <div>
                <label htmlFor="vehicle-select">Vehicle</label>
                <select
                    id="vehicle-select"
                    onChange={handleVehicleSelect}
                    style={{ minWidth: '100%', padding: '0.5rem' }}
                    required
                >
                    <option key="default-vehicle" value="">Select a Vehicle</option>
                    {vehicles.map((vehicle) => (
                        <option key={vehicle.vehicleId} value={vehicle.vehicleId}>
                            {vehicle.model}
                        </option>
                    ))}
                </select>
            </div>
        </div>
    );
}

export default CustomerInfo;
