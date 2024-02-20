import React, { useState, useEffect } from "react";
import moment from 'moment';
import './TimeSlots.css';
import customerService from "../../../../Services/customer.service";


function TimeSlots({ selectedDate, onTimeSelect }) {
    let intime = "09:00 AM";
    let outtime = "06:00 PM";
    const [timeSlots, setTimeSlots] = useState([]);
    const [loading, setLoading] = useState(false);

    function generateTimeSlots(startString, endString) {
        var start = moment(startString, 'hh:mm A');
        var end = moment(endString, 'hh:mm A');
        var slots = [];

        while (start <= end) {
            slots.push(start.format('HH:mm'));
            start.add(60, 'minutes');
        }

        return slots;
    }

    useEffect(() => {
        if (!selectedDate) {
            return;
        }
        setLoading(true);
        const formattedDate = selectedDate.format("YYYY-MM-DD");
        console.log("Formatted Date for API Call:", formattedDate);

        customerService.getAvailabilities(formattedDate)
            .then(response => {
                const availableSlots = generateTimeSlots(intime, outtime).map(slot => ({
                    time: slot,
                    available: response.data[slot] !== false
                }));
                setTimeSlots(availableSlots);
            })
            .catch(error => {
                console.error('Error fetching time slots:', error);
            })
            .finally(() => {
                setLoading(false);
            });
    }, [selectedDate]);



    const handleTimeClick = (time, available) => {
        if (available) {
            onTimeSelect(time);
        }
    };
    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className='grid grid-cols-2 gap-2'>
            {timeSlots.map((slot, index) => (
                <button
                    key={index}
                    onClick={() => handleTimeClick(slot.time, slot.available)}
                    className={`p-2 text-center rounded h-10 transition-colors duration-150 ${
                        slot.available ? 'bg-gray-200 hover:bg-yellow-100' : 'bg-gray-400 cursor-not-allowed'
                    }`}
                    disabled={!slot.available}
                >
                    {slot.time}
                </button>
            ))}
        </div>
    );


}

export default TimeSlots;

