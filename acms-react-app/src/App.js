import React from 'react';
import { RouterProvider, createBrowserRouter} from 'react-router-dom';
import Login from './Pages/Login_Page/Login';
import Home from './Pages/Home_Page/Home'
import Signup from './Pages/Signup_Page/Signup'
import Admin from './Pages/Admin_Pages/Admin';
import Customers from './Pages/Admin_Pages/Customers_Page/Customers';
import CustomerDetails from './Pages/Admin_Pages/CustomerDetails_Page/CustomerDetails';
import CustomerVehicles from './Pages/Admin_Pages/CustomerVehicles_Page/CustomerVehicles';
<<<<<<< HEAD
import CustomerVehicleDetails from './Pages/Admin_Pages/CustomerVehicleDetails_Page/CustomerVehicleDetails';
=======
import AddNewVehicle from './Pages/Admin_Pages/AddNewVehicle_Page/AddNewVehicle';

>>>>>>> 210b79d (Styling front end)
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
<<<<<<< HEAD
        path: "/admin/customers/:customerId/vehicles/:vehicleId",
        element: <CustomerVehicleDetails />,
=======
        path: "/admin/customers/:customerId/vehicles/newVehicle",
        element: <AddNewVehicle />,
>>>>>>> 210b79d (Styling front end)
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