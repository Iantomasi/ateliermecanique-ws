import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const UserProfile = () => {
  const [userImage, setUserImage] = useState('/userImage.svg'); // Set default user image
  const [user, setUser] = useState({});
  const [provider, setProvider] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('userToken');
    if (token) {
      const userObject = JSON.parse(localStorage.getItem('user'));
      const storedProvider = localStorage.getItem('provider');

      if (storedProvider === 'google') {
        if (userObject.picture) {
          setUserImage(userObject.picture);
        }
      } else if (storedProvider === 'facebook') {
        if (userObject.picture && userObject.picture.data && userObject.picture.data.url) {
          setUserImage(userObject.picture.data.url);
          console.log(userObject.picture.data.url);
        }
      }

      setUser(userObject);
      setProvider(storedProvider);
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('userToken');
    localStorage.removeItem('provider');
    localStorage.removeItem('user'); 
  };

  return (
    <div className="user-profile flex items-center">
      <div className="link pr-2">
        <Link to={"/"} className="text-black" onClick={handleLogout}>Logout</Link>
      </div>
      <div className="user-image">
        <img
          src={userImage}
          alt="profile"
          className="rounded-full"
          width="56"
          height="74"
        />
      </div>
    </div>
  );
};

export default UserProfile;
