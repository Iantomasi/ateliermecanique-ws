import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import AuthService from '../../Services/auth.service.js';

const UserProfile = () => {
  const [userImage, setUserImage] = useState('/userImage.svg'); // Set default user image

  useEffect(() => {
    const fetchUser = async () => {
      const fetchedUser = AuthService.getCurrentUser();
      
      if (fetchedUser.picture) {
        setUserImage(fetchedUser.picture);
      }
    };

    fetchUser();
  }, []);

  const handleLogout = () => {
    AuthService.logout();
  };

  const handleError = () => {
    setUserImage('/userImage.svg');
  };

  return (
    <div className="user-profile flex items-center">
      <div className="link pr-2">
        <Link to={"/"} className="text-black" onClick={handleLogout}>
          Logout
        </Link>
      </div>
      <div className="user-image">
        <img
          src={userImage}
          alt="profile"
          className="rounded-full"
          width="56"
          height="74"
          onError={handleError}
        />
      </div>
    </div>
  );
};

export default UserProfile;
