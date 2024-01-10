import React, { Component } from "react";

import userService from "../Services/user.service";

export default class BoardAdmin extends Component {
    constructor(props){
        super(props);

        this.state = {
            content:""
        };
    }

    componentDidMount(){
        userService.getAdminBoard().then(
            response => {
                this.setState({
                    content:response.data
                });
            },
            error => {
                this.setState({
                    content:(error.response && error.response.data && error.response.data.message) || error.message || error.toString()
                });
            }
        );
    }

    render(){
        return(
            <div className="container">
                <header className="jumbotron">
                    <h3>{this.state.content}</h3>
                </header>
            </div>
        );
    }
}
    