import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import AuthService from '../../Services/auth.service.js';

const UserProfile = () => {
  const [userImage, setUserImage] = useState('/userImage.svg');
  const [userRoles, setUserRoles] = useState([]); // Store roles as an array
  const [userId, setUserId] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUser = async () => {
      const fetchedUser = AuthService.getCurrentUser();
      if (fetchedUser) {
        setUserRoles(fetchedUser.roles || []); // Adjust here to match your user object structure
        setUserId(fetchedUser.id);
        if (fetchedUser.picture) {
          setUserImage(fetchedUser.picture);
        }
      }
    };

    fetchUser();
  }, []);

  const handleLogout = () => {
    AuthService.logout();
    navigate('/login'); // Adjust as needed for your app's routing
  };

  const handleError = () => {
    setUserImage('/userImage.svg');
  };

  const handleProfileClick = () => {
    // Check for customer role in the roles array
    if (userRoles.includes('ROLE_CUSTOMER')) { // Adjust 'ROLE_CUSTOMER' as needed
      navigate(`/user/customers/${userId}`);
    }
  };

  return (
    <div className="user-profile flex items-center">
      <div className="link pr-2">
        <Link to="/" className="text-black" onClick={handleLogout}>
          Logout
        </Link>
      </div>
      <div className="user-image">
        <img
          src={userImage}
          alt="Profile"
          className="rounded-full cursor-pointer"
          width="56"
          height="56"
          onClick={handleProfileClick}
          onError={handleError}
        />
      </div>
    </div>
  );
};

export default UserProfile;
