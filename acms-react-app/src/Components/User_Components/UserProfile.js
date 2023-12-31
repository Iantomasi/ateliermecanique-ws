import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const UserProfile = () => {
  const [userImage, setUserImage] = useState('/userImage.svg'); // Set default user image
  const [user, setUser] = useState({});
  const [provider, setProvider] = useState('');

  useEffect(() => {
    const token = sessionStorage.getItem('userToken');
    if (token) {
      const userObject = JSON.parse(sessionStorage.getItem('user'));
      const storedProvider = sessionStorage.getItem('provider');

      if (storedProvider === 'google') {
        if (userObject.picture) {
          setUserImage(userObject.picture);
        }
      } else if (storedProvider === 'facebook') {
        // Fetch Facebook profile picture using Facebook Graph API
        axios.get(`https://graph.facebook.com/v14.0/${userObject.id}/picture?type=large`)
          .then(response => {
            setUserImage(response.request.responseURL);
          })
          .catch(error => {
            console.error('Error fetching profile picture', error);
            setUserImage('/defaultImage.svg'); // Set default image on error
          });
      }

      setUser(userObject);
      setProvider(storedProvider);
    }
  }, []);

  const handleLogout = () => {
    sessionStorage.removeItem('userToken');
    sessionStorage.removeItem('provider');
    sessionStorage.removeItem('user');
    console.log("user logged out");
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
