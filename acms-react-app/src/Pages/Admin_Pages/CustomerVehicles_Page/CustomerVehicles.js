import React {useState, useEffect} from "react";
import NavBar from "../../Components/Navigation_Bars/Not_Logged_In/NavBar.js";
import Footer from "../../Components/Footer/Footer.js";
import MechanicDisplay from "../../Components/User_Components/MechanicDisplay.js";
import CustomerBlock from "../CustomerDetails_Page/CustomerBlock.js";
import "./CustomerVehicles.css";
import axios from "axios";


function CustomerVehicles() {
    const { customerId } = useParams();
    const [vehicles, setVehicles] = useState([])

    useEffect(() => {
        getVehicles();
      }, []);


    function getVehicles(){
        axios.get(`http://localhost:8080/api/v1/customers/${customerId}/vehicles}`)
        .then(res=>{
            console.log('API response:', res); // Add this line
            if(res.status === 200){
                setVehicles(res.data);
            }
        })
        .catch(error =>{
            console.log(error);
        })





}