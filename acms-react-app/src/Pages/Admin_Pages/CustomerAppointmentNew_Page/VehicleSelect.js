import React, { useState, useEffect } from 'react';

function VehicleSelect({ customerId, updateVehicleId }) {
    const [selectedVehicleId, setSelectedVehicleId] = useState('');
    const [vehicles, setVehicles] = useState([]);

    useEffect(() => {
        // Only fetch vehicles if customerId is available
        if (customerId) {
            const url = `http://localhost:8080/api/v1/customers/${customerId}/vehicles`;
            console.log('Fetching vehicles from:', url);

            fetch(url)
                .then(response => response.json())
                .then(data => setVehicles(data))
                .catch(error => console.error('Error fetching vehicles:', error));
        } else {
            setVehicles([]);
        }
    }, [customerId]); // Dependency array with customerId

    const handleVehicleSelect = (event) => {
        const vehicleId = event.target.value;
        setSelectedVehicleId(vehicleId);
        updateVehicleId(vehicleId);
    };

    return (
        <div>
            <div>
                <label htmlFor="vehicle-select">Vehicle</label>
                <select
                    id="vehicle-select"
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

export default VehicleSelect;
