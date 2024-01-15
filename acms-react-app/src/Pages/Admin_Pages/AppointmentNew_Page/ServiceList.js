import React, { useState } from 'react';

function ServicesList({ onSelectService }) {
    const services = [
        "Air conditioning",
        "Muffler",
        "Car Inspection Before Purchase",
        "End of Manufacturer's Warranty",
        "Exhaust System",
        "Paint & body work"
    ];

    const [selectedService, setSelectedService] = useState(null);

    const handleSelectService = (service) => {
        setSelectedService(service);
        onSelectService(service);
    };

    return (
        <div className="bg-gray-200 p-4 max-w-sm rounded">
            <h3 className="font-bold text-lg mb-3">Services</h3>
            <ul>
                {services.map((service, index) => (
                    <li key={index} className="flex items-center mb-2">
                        <span className="flex-1">{service}</span>
                        <button
                            className="text-yellow-500 hover:text-yellow-700 ml-2 px-3 py-1 border border-yellow-500 rounded"
                            onClick={() => handleSelectService(service)}
                        >
                            Select
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ServicesList;
