import React from 'react';

const UserDisplay = () => {
  return (
    <div className="flex items-center">
      <div className="flex items-center">
        <img src="/userImage.svg" alt="Profile" className="w-12 h-auto mr-2" />
        <div className="flex flex-col">
          <div className="font-bold">User</div>
          <div className="font-light">CMS Member</div>
        </div>
      </div>
    </div>
  );
};

export default UserDisplay;


