import React from 'react';
import { RouterProvider, createBrowserRouter} from 'react-router-dom';
import Login from './Pages/Login_Page/Login';
import Home from './Pages/Home_Page/Home'
import Signup from './Pages/Signup_Page/Signup'
import Admin from './Pages/Admin_Pages/Admin';
import Customers from './Pages/Admin_Pages/Customers/Customers_Page/Customers';
import CustomerDetails from './Pages/Admin_Pages/Customers/CustomerDetails_Page/CustomerDetails';
import CustomerVehicles from './Pages/Admin_Pages/Customers/CustomerVehicles_Page/CustomerVehicles';
import CustomerVehicleDetails from './Pages/Admin_Pages/Customers/CustomerVehicleDetails_Page/CustomerVehicleDetails';
import AddNewVehicle from './Pages/Admin_Pages/AddNewVehicle_Page/AddNewVehicle';
import Appointments from './Pages/Admin_Pages/Appointments/Appointments_Page/Appointments';
import User from './Pages/User_Pages/User';
import CustomerAppointments from './Pages/Admin_Pages/Customers/CustomerAppointments_Page/CustomerAppointments';
import Calendar from './Pages/Admin_Pages/AppointmentNew_Page/Calendar';
import CustomerAppointmentDetails from './Pages/Admin_Pages/Customers/CustomerAppointments_Page/CustomerAppointmentDetails.js';
import CustomerCalendar from './Pages/Admin_Pages/CustomerAppointmentNew_Page/CustomerCalendar';

import AppointmentDetails from './Pages/Admin_Pages/Appointments/AppointmentDetails_Page/AppointmentDetails';


const router = createBrowserRouter([
  {
    children  :[
      {
        path : "/",
        element: <Home/>
      },
      {
        path : "/login",
        element: <Login/>
      },
      {
        path: "/signup",
        element: <Signup/>

      },
      {
        path : "/admin",
        element: <Admin/>
      }
      ,
      {
        path : "/admin/customers",
        element: <Customers/>
      }
      ,
      {
        path: "/admin/customers/:customerId",
        element: <CustomerDetails />,
      },
      {
        path: "/admin/customers/:customerId/vehicles",
        element: <CustomerVehicles />,
      },
      {
        path: "/admin/customers/:customerId/vehicles/:vehicleId",
        element: <CustomerVehicleDetails />,
      },
      {
        path: "/admin/customers/:customerId/vehicles/newVehicle",
        element: <AddNewVehicle/>,
      },
      {
        path : "/admin/appointments",
        element: <Appointments/>
      },
      {
        path: "/admin/customers/:customerId/appointments",
        element: <CustomerAppointments/>,
      },
      {
        path:"/admin/appointments/:appointmentId",
        element: <AppointmentDetails/>,
      },
      {
        path: "/admin/customers/:customerId/appointments/:appointmentId",
        element: <CustomerAppointmentDetails/>,
      },
      {
        path: "/admin/appointments/newAppointment",
        element: <Calendar />,

      },
      {
        path: "/admin/customers/:customerId/appointments/newAppointment",
        element: <CustomerCalendar />,

      },
      { 
        path:"/admin/customers/:customerId/appointments/:appointmentId",
        element: <AppointmentDetails/>,
      },
      {
        path: "/user",
        element: <User/>
      }

    ]
  }
])

function App() {
  return (

      <div>
        <RouterProvider router={router}/>
      </div>

  );
}

export default App;

