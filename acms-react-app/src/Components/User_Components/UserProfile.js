import React from 'react';
import { Link } from 'react-router-dom';

const UserProfile = () => {
  return (
    <div className="user-profile flex items-center">
      <div className="link pr-2">
        <Link to={"/"} className="text-black">Logout</Link>
      </div>
      <div className="user-image">
        <img src="/userImage.svg" alt="Profile" />
      </div>
    </div>
  );
};

export default UserProfile;
