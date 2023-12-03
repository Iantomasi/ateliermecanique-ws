import React from 'react';
import { RouterProvider, createBrowserRouter} from 'react-router-dom';
import Login from './Pages/Login_Page/Login';
import Home from './Pages/Home_Page/Home'
import Signup from './Pages/Signup_Page/Signup'

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