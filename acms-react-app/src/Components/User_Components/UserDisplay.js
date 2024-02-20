import React from 'react';
import authservice from "../../services/authservice";
const UserDisplay = () => {

  const user = authservice.getCurrentUser();

  const handleError = () => {
    setUserImage('/userImage.svg');
  };


  return (
    <div className="flex items-center">
      <div className="flex items-center">
        <img src={user.picture} alt="Profile" className="w-12 h-auto mr-2" onError={handleError}/>
        <div className="flex flex-col">
          <div className="font-bold">{user.firstName}</div>
          <div className="font-light">{user.lastName}</div>
        </div>
      </div>
    </div>
  );
};

export default UserDisplay;


