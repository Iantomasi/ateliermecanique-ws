import React, { Component } from "react";   
import authService from "../Services/auth.service";

export default class Profile extends Component {
    constructor(props){
        super(props);

        this.state = {
            currentUser:authService.getCurrentUser()
        };
    }

    render(){
        const {currentUser} = this.state;

        return(
            <div className="container">
                <header className="jumbotron">
                    <h3>
                        <strong>{currentUser.username}</strong> Profile
                    </h3>
                </header>
                <p>
                    <strong>Token:</strong>{" "}
                    {currentUser.token.substring(0,20)} ...{" "}
                    {currentUser.token.substr(currentUser.token.length - 20)}
                </p>
                <p>
                    <strong>Id:</strong>{" "}
                    {currentUser.id}
                </p>
                <p>
                    <strong>Email:</strong>{" "}
                    {currentUser.email}
                </p>
                <strong>Authorities:</strong>
                <ul>
                    {currentUser.roles &&
                        currentUser.roles.map((role,index)=> <li key={index}>{role}</li>)}
                </ul>
            </div>
        );
    }
}