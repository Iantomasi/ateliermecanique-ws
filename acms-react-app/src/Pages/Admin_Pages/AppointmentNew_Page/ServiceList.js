import React, { useState } from 'react';

function ServicesList({ onSelectService }) { // Add onSelectService as a prop
    // List of services to be rendered
    const services = [
        "Air conditioning",
        "Muffler",
        "Car Inspection Before Purchase",
        "End of Manufacturer's Warranty",
        "Exhaust System",
        // ... Add all other services here
        "Paint & body work"
    ];

    const [selectedService, setSelectedService] = useState(null);

    const handleSelectService = (service) => {
        setSelectedService(service);
        onSelectService(service); // This will call the function passed as a prop
    };

    return (
        <div className="bg-gray-200 p-4 max-w-sm rounded">
            <h3 className="font-bold text-lg mb-3">Services</h3>
            <ul>
                {services.map((service, index) => (
                    <li key={index} className="flex items-center mb-2">
                        <span className="flex-1">{service}</span>
                        <button
                            className="text-yellow-500 hover:text-yellow-700"
                            onClick={() => handleSelectService(service)}
                        >
                            {/* Button content like an icon or text */}
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ServicesList;
