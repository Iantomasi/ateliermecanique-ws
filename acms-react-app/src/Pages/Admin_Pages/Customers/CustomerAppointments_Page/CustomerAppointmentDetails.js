import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import NavBar from '../../../../Components/Navigation_Bars/Not_Logged_In/NavBar.js';
import dayjs from 'dayjs';
import Navbar from '../../../../Components/Navigation_Bars/Logged_In/NavBar.js';
import Footer from '../../../../Components/Footer/Footer.js';
import Sidebar from '../../../../Components/Navigation_Bars/Sidebar/Sidebar.js';
import CustomDateTimePicker from '../../../../Components/DateTimePicker/CustomDateTimePicker.js';
import adminService from '../../../../Services/admin.service.js';

function AppointmentDetails() {
  const { appointmentId } = useParams();
  const [appointmentDetails, setAppointmentDetails] = useState({
      customerId: '',
      vehicleId: '',
      appointmentDate: '',
      services: '',
      comments: '',
      status: ''
  });
  const [showConfirmation, setShowConfirmation] = useState(false);
  const navigate = useNavigate();

  const [publicContent, setPublicContent] = useState(null);
  const [message, setMessage] = useState('');

  

  useEffect(() => {
      adminService.getAppointmentById(appointmentId)
          .then(res => {
              if (res.status === 200) {
                setPublicContent(true);
                  setAppointmentDetails(res.data);
              }
          })
          .catch(error => {
              console.error("Error fetching appointment details", error);
              setPublicContent(false);
              if (error.response && error.response.status === 403) {
                
                setMessage({
                  status: 403,
                  error: 'Forbidden',
                  message: 'You do not have permission to access this resource.',
                });
              } else {
                
                setMessage(error.response.data);
              }
          });
  }, [appointmentId]);


  const handleDateTimeChange = (value) => {
    console.log("Received Value:", value); // Debug log
    const formattedDate = value ? dayjs(value).format("YYYY-MM-DDTHH:mm") : '';
    console.log("Formatted Date:", formattedDate); // Debug log
    setAppointmentDetails(prevState => ({
        ...prevState,
        appointmentDate: formattedDate
    }));
};

  const handleBackButtonClick = () => {
      navigate('/admin/appointments'); // Use navigate to navigate back to the list of all appointments page
  };


  function confirmDelete() {
      setShowConfirmation(true);
    }
  
    function cancelDelete() {
      setShowConfirmation(false);
    }
  
    function executeDelete() {
      adminService.deleteAppointment(appointmentId)
        .then(res => {
          if (res.status === 204) {
            alert('Appointment has been deleted!');
            setShowConfirmation(false);
            navigate(`/admin/customers/${appointmentDetails.customerId}/appointments`);
          }
        })
        .catch(err => {
          console.error('Error deleting appointment:', err);
          setShowConfirmation(false);
        });
    }

    function handleInputChange(event) {
      const { name, value } = event.target;
      setAppointmentDetails(prevState => ({
      ...prevState,
      [name]: value
    }));

    if (!appointmentDetails) {
        return <div>Loading...</div>;
      }
   }

   function updateAppointment(event){
    event.preventDefault();
  
    const formData = new FormData(event.target);
    const updatedAppointment = {
      customerId: formData.get('customerId'),
      vehicleId: formData.get('vehicleId'),
      // Use the state value for appointmentDate
      appointmentDate: appointmentDetails.appointmentDate,
      services: formData.get('services'),
      comments: formData.get('comments'),
      status: formData.get('status')
    };
  
    adminService.updateAppointment(appointmentId, updatedAppointment)
    .then(res => {
      if (res.status === 200) {
        setAppointmentDetails(res.data);
        alert('Appointment has been updated!');
      }
    })
    .catch(err => {
      console.error('Error updating appointment:', err);
    });    
  }
  return (
      <div className="flex flex-col min-h-screen">
        {publicContent ?(
        <div>
          <Navbar />
          <div className="flex">
            <Sidebar appointmentId={appointmentId} />  
            <main className="flex-grow p-5">
            <button className="mr-5 text-blue-500 hover:underline" onClick={handleBackButtonClick}>
            Back
            </button>
              <p className="text-4xl font-bold text-center">APPOINTMENT DETAILS</p>
            {appointmentDetails && (
            <div className="bg-gray-100 shadow-lg p-5 rounded-md mt-5 relative">
              <form onSubmit={updateAppointment}>
                  <label className='font-bold'>Customer ID</label>
                  <input className="w-full p-4 rounded border border-gray-400 mb-5" name="customerId" value={appointmentDetails.customerId} onChange={handleInputChange} type="text" required />
                
                  <label className='font-bold'>Vehicle ID</label>
                  <input className="w-full p-4 rounded border border-gray-400 mb-5" name="vehicleId" value={appointmentDetails.vehicleId} onChange={handleInputChange} type="text" required />
                                
                  <label className='font-bold'>Appointment Date</label>
                  <CustomDateTimePicker onChange={handleDateTimeChange} />

                                  
                  <label className="font-bold">Services</label>
                    <select
                      className="w-full p-4 rounded border border-gray-400 mb-5"
                      name="services"
                      value={appointmentDetails.services}
                      onChange={handleInputChange}
                      required
                    >
                      {console.log(appointmentDetails.services)}
                      <option value="">Select Appointment Services</option>
                      <option value="Air conditioning">Air conditioning</option>
                      <option value="Muffler">Muffler</option>
                      <option value="End of Manufacturer Warranty">End of Manufacturer's Warranty</option>
                      <option value="Exhaust System">Exhaust System</option>
                      <option value="Paint & body work">Paint & body work</option>
                      <option value="Preventive Maintenance">Preventive Maintenance</option>
                      <option value="Engine and Transmission Installation">Engine and Transmission Installation</option>
                      <option value="Steering & Suspension">Steering & Suspension</option>
                      <option value="Injection">Injection</option>
                      <option value="Alignment">Alignment</option>
                      <option value="Brakes">Brakes</option>
                    </select>
                          
                            
                  <label className='font-bold'>Comments</label>
                  <input className="w-full p-4 rounded border border-gray-400 mb-5" name="comments" value={appointmentDetails.comments} onChange={handleInputChange} type="text" required />
                          
                            
                  <label className="font-bold">Status</label>
                  <select className="w-full p-4 rounded border border-gray-400 mb-5" name="status" value={appointmentDetails.status} onChange={handleInputChange}  required>
                  <option value="">Select Appointment Status</option>
                  <option value="PENDING">Pending</option>
                  <option value="CONFIRMED">Confirmed</option>
                  <option value="CANCELLED">Cancelled</option>
                  </select>

                  <div className="flex justify-center space-x-10">

                  <button className="bg-yellow-400 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" type="submit">
                    Save
                  </button>
                  <button className="bg-red-500 border-none px-4 py-2 rounded font-bold transform transition duration-300 hover:scale-110" onClick={confirmDelete} type="button">
                    Delete
                  </button>
                  </div>

                      {showConfirmation && (
                  <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-80 flex justify-center items-center z-50">
                    <div className="absolute bg-gray-100 border border-gray-300 rounded-md shadow-lg p-6">
                      <p className="text-xl mb-4">Are you sure you want to delete {appointmentDetails.model}?</p>
                      <div className="flex justify-center space-x-5">
                        <button onClick={executeDelete} type="button" className="px-4 py-2 mr-2 bg-red-500 text-white rounded hover:bg-red-600 focus:outline-none focus:ring focus:ring-red-200">
                          Yes
                        </button>
                        <button onClick={cancelDelete} type="button" className="px-4 py-2 bg-yellow-400 focus:outline-none focus:ring focus:ring-gray-200">
                          No
                        </button>
                      </div>
                    </div>
                  </div>
                )}
            </form>
            </div>

            
          )}
            
            </main>
            </div>
          </div>
        ) : (
        <div className="flex-1 text-center">
          <NavBar />
          {publicContent === false ? <h1 className='text-4xl'>{message.status} {message.error} </h1> : 'Error'}
          {message && (
            <>
              <p className='text-2xl'>{message.message} </p>
            </>
          )}
        </div>)}
          <Footer />
      </div>
  );
}
export default AppointmentDetails;
