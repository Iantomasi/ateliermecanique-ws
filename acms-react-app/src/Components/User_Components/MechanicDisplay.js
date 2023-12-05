import React from 'react';
import './MechanicDisplay.css';

const MechanicDisplay = () => {
  return (
    <div className="display">
      <div className="user-info">
        <div className="user-image">
          <img src="/userImage.svg" alt="Profile" />
        </div>
        <div className="details">
          <div className="name">Mechanic</div>
          <div className="role">CMS Member</div>
        </div>
      </div>
    </div>
  );
};

export default MechanicDisplay;
