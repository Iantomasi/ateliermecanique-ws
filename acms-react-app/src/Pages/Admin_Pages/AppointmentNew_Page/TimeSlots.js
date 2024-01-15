import React, { useState } from 'react';
import moment from 'moment';
import './TimeSlots.css';

function TimeSlots({ onTimeSelect }) {
    let intime = "09:00 Am";
    let outtime = "06:00 Pm";
    const [result, setResult] = useState([]);
    const [selectedTime, setSelectedTime] = useState(null);

    console.log("Array", result);

    function intervals(startString, endString) {
        var start = moment(startString, 'hh:mm a');
        var end = moment(endString, 'hh:mm a');
        start.minutes(Math.ceil(start.minutes() / 60) * 60);

        var current = moment(start);

        while (current <= end) {
            let timeFormatted = current.format('HH:mm');
            if (result.includes(timeFormatted)) {
                return null;
            } else {
                setResult(prevResult => [...prevResult, timeFormatted]);
                current.add(60, 'minutes');
            }
        }
    }

    React.useEffect(() => {
        intervals(intime, outtime);
    }, [intime, outtime]);

    const handleTimeClick = (time) => {
        setSelectedTime(time);
        console.log('Selected Time:', time);
        onTimeSelect(time);
    };

    return (
        <div className='grid grid-cols-2 gap-2'>
            {result && result.length > 0 ? result.map((time, index) => (
                <button
                    key={index}
                    onClick={() => handleTimeClick(time)}
                    className={`p-2 text-center rounded h-10 transition-colors duration-150 ${
                        selectedTime === time ? 'bg-yellow-500 text-white' : 'bg-gray-200 hover:bg-yellow-100'
                    }`}
                >
                    {time}
                </button>
            )) : null}
        </div>
    );


}

export default TimeSlots;
