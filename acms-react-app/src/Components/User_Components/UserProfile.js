import React from 'react';
import { Link } from 'react-router-dom';

const UserProfile = () => {
  return (
    <div className="user-profile">
        <div className="link">
        <Link to={"/"}>Logout</Link>
        </div>
        <div className="user-image">
            <img src="/userImage.svg" alt="Profile" />
        </div>
    </div>
  );
};

export default UserProfile;