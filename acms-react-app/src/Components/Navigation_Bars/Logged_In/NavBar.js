import React, { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import UserProfile from '../../User_Components/UserProfile';
import { useNavigate } from 'react-router-dom';
import authService from '../../../Services/auth.service';


function Navbar() {
    const navigate = useNavigate();
    const [userRole, setUserRole] = useState(null);


    useEffect(() => {
        const currentUser = authService.getCurrentUser();

        if (currentUser) {
            setUserRole(currentUser.roles.includes('ROLE_ADMIN') ? 'admin' : 'user');
        }
    }, []);

    const handleLogoClick = () => {
        if (userRole === 'admin') {
            navigate('/admin');
        } else if (userRole === 'user') {
            navigate('/user');
        }
    };

    return (
        <header className="bg-white">
            <nav className="flex justify-between items-center p-4">
                <div className="flex">
                    <h1 className="text-4xl font-bold">Atelier Mecanique</h1>
                </div>

                <div className="flex items-center">
                    <div className="mr-0" onClick={handleLogoClick} style={{ cursor: 'pointer' }}>
                        <img src="/logo.svg" alt="app logo"/>
                    </div>

                    <div className="flex items-center">
                        <a href="/about" className="text-gray-700 hover:text-gray-900 px-4 py-1">About</a>
                        <a href="/services" className="text-gray-700 hover:text-gray-900 px-4 py-1">Services</a>
                        <a href="/contact" className="text-gray-700 hover:text-gray-900 px-4 py-1">Contact</a>
                    </div>
                </div>

                <div className="flex items-center">
                    <UserProfile />
                </div>
            </nav>
            <div className="w-full h-px bg-black"></div>
        </header>
    );
}

export default Navbar;