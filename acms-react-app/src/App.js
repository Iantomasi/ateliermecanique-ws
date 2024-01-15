import React from 'react';
import { RouterProvider, createBrowserRouter} from 'react-router-dom';
import Login from './Pages/Login_Page/Login';
import Home from './Pages/Home_Page/Home'
import Signup from './Pages/Signup_Page/Signup'
import Admin from './Pages/Admin_Pages/Admin';
import Customers from './Pages/Admin_Pages/Customers_Page/Customers';
import CustomerDetails from './Pages/Admin_Pages/CustomerDetails_Page/CustomerDetails';
import CustomerVehicles from './Pages/Admin_Pages/CustomerVehicles_Page/CustomerVehicles';
import CustomerVehicleDetails from './Pages/Admin_Pages/CustomerVehicleDetails_Page/CustomerVehicleDetails';
import AddNewVehicle from './Pages/Admin_Pages/AddNewVehicle_Page/AddNewVehicle';
import Appointments from './Pages/Admin_Pages/Appointments_Page/Appointments';
import User from './Pages/User_Pages/User';
import CustomerAppointments from './Pages/Admin_Pages/CustomerAppointments_Page/CustomerAppointments';

import AppointmentSpecifics from './Pages/Admin_Pages/AppointmentSpecifics_Page/AppointmentSpecifics';
import Calendar from './Pages/Admin_Pages/AppointmentNew_Page/Calendar';
import CustomerAppointmentDetails from './Pages/Admin_Pages/CustomerAppointmentDetails_Page/CustomerAppointmentDetails';

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
        element: <AppointmentSpecifics/>,
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