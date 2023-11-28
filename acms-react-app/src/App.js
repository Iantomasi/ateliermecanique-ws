import React from 'react';
import { RouterProvider, createBrowserRouter} from 'react-router-dom';
import Login from './Pages/Login_Page/Login';
import Home from './Pages/Home_Page/Home'

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