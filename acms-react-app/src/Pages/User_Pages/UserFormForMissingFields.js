import axios from 'axios';
import React, { useState,useEffect } from 'react';

const UserForm = ({ customer, hideUserForm }) => {
  const [updatedCustomer, setUpdatedCustomer] = useState({ ...customer });
  const [validation, setValidation] = useState("");


  useEffect(() => {
    const token = sessionStorage.getItem('userToken');
    const provider = sessionStorage.getItem('provider');

    if (token) {
        if (provider === 'google') {
            axios.get(`http://localhost:8080/api/v1/auth/google-token-verification/${token}`)
                .then(res => {
                    setValidation(res.data);
                })
                .catch(error => {
                    console.error('Token error:', error);
                });
        } else if (provider === 'facebook') {
            axios.get(`http://localhost:8080/api/v1/auth/facebook-token-verification/${token}`)
                .then(res => {
                    setValidation(res.data); 
                })
                .catch(error => {
                    console.error('Token error:', error);
                });
        } else if (provider === 'instagram') {
            axios.get(`http://localhost:8080/api/v1/auth/instagram-token-verification/${token}`)
                .then(res => {
                    setValidation(res.data); 
                })
                .catch(error => {
                    console.error('Token error:', error);
                });
        }
    }
}, [sessionStorage.getItem('userToken'), sessionStorage.getItem('provider')]);

  const handleInputChange = (field, value) => {
    setUpdatedCustomer({
      ...updatedCustomer,
      [field]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (validation === "Token is valid and not expired.") {
      const token = sessionStorage.getItem('userToken');

      axios.get(`http://localhost:8080/api/v1/auth/${token}`)
        .then(res => {
          if (res.data.customerId === updatedCustomer.customerId) {
            axios.put(`http://localhost:8080/api/v1/customers/${updatedCustomer.customerId}`, updatedCustomer)
              .then(() => {
                console.log("updated customer: ", updatedCustomer);
                hideUserForm();
              })
              .catch(error => {
                console.error('Error updating customer', error);
              });
          } else {
            alert("You are not authorized to update this account");
          }
        })
        .catch(error => {
          console.error('Error logging in', error);
        });
    }
  };

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
  };
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
