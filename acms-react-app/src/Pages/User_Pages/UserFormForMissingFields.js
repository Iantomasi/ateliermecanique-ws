import axios from 'axios';
import React, { useState,useEffect } from 'react';
import customerService from '../../Services/customer.service';
import authService from '../../Services/auth.service';

const UserForm = ({customer,hideUserForm }) => {
    const [updatedCustomer, setUpdatedCustomer] = useState({ ...customer });

    const handleInputChange = (field, value) => {
      setUpdatedCustomer({
        ...updatedCustomer,
        [field]: value,
      });
    };

    const handleSubmit = (event) => {
      event.preventDefault();
      
      customerService.updateCustomer(customer.id,updatedCustomer)
                .then(() => {
                  sessionStorage.setItem('user', JSON.stringify(updatedCustomer));
                  hideUserForm();
                })
                .catch(error => {
                  console.error('Error updating customer', error);
                });
      }

    const renderInputField = (fieldName, label) => {
      const formattedLabel = label.replace(/([A-Z])/g, ' $1').replace(/^./, (str) => str.toUpperCase());
    
      const inputType = fieldName.toLowerCase() === 'email' ? 'email' : 'text';
    
      return (
        <div key={fieldName} className="mb-4">
          <label htmlFor={fieldName} className="block text-gray-700 font-bold mb-2">
            {formattedLabel}:
          </label>
          <input
            type={inputType}
            id={fieldName}
            value={updatedCustomer[fieldName] || ''}
            onChange={(e) => handleInputChange(fieldName, e.target.value)}
            className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            required
          />
        </div>
      );
    }

    const nullFields = Object.keys(customer).filter((field) => customer[field] === null);

    return (
      <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4" onSubmit={handleSubmit}>
        <div className="mb-4">
          <span className="text-red-500 font-bold">OBLIGATORY: </span>
          <span className="text-black">Please fill in the missing fields for your account</span>
        </div>
        {nullFields.map((field) =>
          renderInputField(field, field.charAt(0).toUpperCase() + field.slice(1))
        )}
        <button
          type="submit"
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
        >
          Submit
        </button>
      </form>
    );
};


export default UserForm;
