    import React, { useState, useEffect } from 'react';

    function CustomerInfo({ updateCustomerId, updateVehicleId }) {
        const [customers, setCustomers] = useState([]);
        const [selectedCustomerId, setSelectedCustomerId] = useState('');
        const [selectedVehicleId, setSelectedVehicleId] = useState('');
        const [vehicles, setVehicles] = useState([]);

        useEffect(() => {
            console.log('Fetching customers...');
            fetch('http://localhost:8080/api/v1/customers', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Network response was not ok, status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Customers fetched successfully:', data);
                    setCustomers(data);
                })
                .catch(error => console.error('Error fetching customers:', error));
        }, []);




        const handleCustomerSelect = (event) => {
            const customerId = event.target.value;
            setSelectedCustomerId(customerId);
            updateCustomerId(customerId);


            // Log the selected customer ID
            console.log('Selected Customer ID:', customerId);

            // Build the URL for fetching vehicles
            const url = `http://localhost:8080/api/v1/customers/${customerId}/vehicles`;
            console.log('Fetching vehicles from:', url);

            // Fetch vehicles for the selected customer
            if (customerId) {
                fetch(url)
                    .then(response => response.json())
                    .then(data => setVehicles(data))
                    .catch(error => console.error('Error fetching vehicles:', error));
            } else {
                setVehicles([]);
            }
        };

        const handleVehicleSelect = (event) => {
            const vehicleId = event.target.value;
            setSelectedVehicleId(vehicleId);
            updateVehicleId(vehicleId);
        };

        return (
            <div>
                <div>
                    <label htmlFor="customer-select">Customer</label>
                    <select
                        id="customer-select"
                        onChange={handleCustomerSelect}
                        value={selectedCustomerId || ''}
                        style={{ minWidth: '100%', padding: '0.5rem' }}
                    >
                        <option key="default-customer" value="">Select a Customer</option>
                        {customers.map((customer) => (
                            <option key={customer.customerId} value={customer.customerId}>
                                {customer.firstName} {customer.lastName}
                            </option>
                        ))}
                    </select>
                </div>
                <div>
                    <label htmlFor="vehicle-select">Vehicle</label>
                    <select
                        id="vehicle-select"
                        disabled={!selectedCustomerId}
                        onChange={handleVehicleSelect}
                        style={{ minWidth: '100%', padding: '0.5rem' }}
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
