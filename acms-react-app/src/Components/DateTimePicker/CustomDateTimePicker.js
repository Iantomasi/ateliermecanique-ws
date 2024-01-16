import React, { useState } from "react";
import DateTimePicker from 'react-datetime-picker';
import 'react-datetime-picker/dist/DateTimePicker.css';
import 'react-calendar/dist/Calendar.css';
import 'react-clock/dist/Clock.css';
import './DatePickerCss.css';  // Import your custom CSS file

function CustomDateTimePicker({ onChange }) {
    const [value, setValue] = useState(new Date());

    const handleChange = (newValue) => {
        setValue(newValue);
        if (onChange) {
            onChange(newValue);
        }
    };

    return (
<div className="p-5 custom-datetime-picker">    
        <DateTimePicker 
                onChange={handleChange} 
                value={value} 
                format="y-MM-dd HH:mm"  // 24-hour format
                // Additional props for customization
            />
        </div>
    );
}


export default CustomDateTimePicker;