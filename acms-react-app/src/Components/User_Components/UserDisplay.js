import React ,{useState,useEffect}from 'react';
import authService from '../../Services/auth.service.js';
const UserDisplay = () => {

  const [userImage, setUserImage] = useState('/userImage.svg');
  const user = authService.getCurrentUser();

  useEffect(() => {
    if (user.picture) {
      setUserImage(user.picture);
    }
  }, [user.picture]);
  const handleError = () => {
    setUserImage('/userImage.svg');
  };


  return (
    <div className="flex items-center">
      <div className="flex items-center">
        <img src={userImage} alt="Profile" className="w-12 h-auto mr-2 rounded-full" onError={handleError}/>
        <div className="flex flex-col">
          <div className="font-bold">{user.firstName}</div>
          <div className="font-light">{user.lastName}</div>
        </div>
      </div>
    </div>
  );
};

export default UserDisplay;


