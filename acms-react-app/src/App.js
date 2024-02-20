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
import AddNewVehicle from './Pages/Admin_Pages/Customers/AddNewVehicle_Page/AddNewVehicle.js';
import Appointments from './Pages/Admin_Pages/Appointments/Appointments_Page/Appointments';
import User from './Pages/User_Pages/User';
import CustomerAppointments from './Pages/Admin_Pages/Customers/CustomerAppointments_Page/CustomerAppointments';
import Calendar from './Pages/Admin_Pages/Appointments/AppointmentNew_Page/Calendar.js';
import CustomerAppointmentDetails from './Pages/Admin_Pages/Customers/CustomerAppointments_Page/CustomerAppointmentDetails.js';
import CustomerCalendar from './Pages/Admin_Pages/Customers/CustomerAppointmentUpdate_Page/CustomerCalendar.js';
import AppointmentDetails from './Pages/Admin_Pages/Appointments/AppointmentDetails_Page/AppointmentDetails.js';
import Invoices from './Pages/Admin_Pages/Invoices/Invoices_Page/Invoices.js';
import CustomerInvoices from './Pages/Admin_Pages/Customers/CustomerInvoices_Page/CustomerInvoices.js';
import AddNewInvoice from './Pages/Admin_Pages/Invoices/Invoices_Page/AddNewInvoice.js';
import InvoiceDetails from './Pages/Admin_Pages/Invoices/Invoices_Page/InvoiceDeails.js';
import Contact from './Pages/Contact_Pages/Contact.js';
import Reviews from './Pages/Admin_Pages/Reviews/Reviews_Page/Reviews.js';
import ReviewDetails from './Pages/Admin_Pages/Reviews/Reviews_Page/ReviewDetails.js';
import ResetPasswordRequest from './Pages/ResetPassword_Pages/ResetPasswordRequest.js';
import ResetPassword from './Pages/ResetPassword_Pages/ResetPassword.js';
import AddReview from './Pages/Admin_Pages/Customers/AddNewReview_Page/AddNewReview.js';
import Services from './Pages/Services_Pages/Services.js';
import About from './Pages/About_Pages/About.js';

import CustomerNewAppointment from './Pages/Admin_Pages/Customers/CustomerAppointmentNew_Page/CustomerNewCalendar.js';
import CustomerReview from './Pages/Admin_Pages/Customers/Reviews_Page/Reviews.js';
import NewCustomerReview from './Pages/Admin_Pages/Customers/Reviews_Page/AddNewReview.js';

const router = createBrowserRouter([
  {
    children  :[
      {
        path : "/",
        element: <Home/>
      },
      {
        path : "/contact",
        element: <Contact/>
      },
      {
        path : "/about",
        element: <About/>
      },
      {
        path : "/services",
        element: <Services/>
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
        path:"/admin/invoices",
        element: <Invoices/>, 
      },
      {
        path: "/admin/customers/:customerId/invoices",
        element: <CustomerInvoices/>,
      },
      {
        path: "/admin/invoices/newInvoice",
        element: <AddNewInvoice/>,

      },

      {
        path: "/admin/invoices/:invoiceId",
        element: <InvoiceDetails/>,
      },
      
      {
        path: "/admin/reviews",
        element: <Reviews/>
      },
      {
        path: "/admin/reviews/:reviewId",
        element: <ReviewDetails/>
      },
      {
        path: "/admin/customers/:customerId/invoices/:invoiceId",
        element: <InvoiceDetails/>,
      },
      {
        path: "/user",
        element: <User/>
      },
      {
        path: "/user/customers/:customerId",
        element: <CustomerDetails />,
      },
      {
        path: "/user/customers/:customerId/vehicles",
        element: <CustomerVehicles />,
      },
      {
        path: "/user/customers/:customerId/vehicles/:vehicleId",
        element: <CustomerVehicleDetails />,
      },
      {
        path: "/user/customers/:customerId/vehicles/newVehicle",
        element: <AddNewVehicle/>,
      },
      {
        path: "/user/customers/:customerId/appointments",
        element: <CustomerAppointments/>,
      },
      {
        path: "/user/customers/:customerId/appointments/:appointmentId",
        element: <CustomerAppointmentDetails/>,
      },
      {
        path: "/user/customers/:customerId/appointments/newAppointment",
        element: <CustomerNewAppointment />,

      },
      {
        path: "/user/customers/:customerId/invoices",
        element: <CustomerInvoices/>,
      },
      {
        path: "/user/customers/:customerId/invoices/:invoiceId",
        element: <InvoiceDetails/>,
      },
      {
        path: "/user/customers/:customerId/reviews",
        element: <CustomerReview/>
      },
      {
        path: "/user/reviews/:reviewId",
        element: <ReviewDetails/>
      },
      {
        path: "/reset-password-request",
        element: <ResetPasswordRequest/>
      },
      {
        path: "/reset-password/:token",
        element: <ResetPassword/>
      },
      {
        path: "/reviews/:customerId/newReview",
        element: <NewCustomerReview/>
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

