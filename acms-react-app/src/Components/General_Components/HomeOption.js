import React from 'react';
import { useNavigate } from 'react-router-dom';
import authService from '../../Services/auth.service';

const HomeOption = ({ src, label, dynamicPath }) => {
  const navigate = useNavigate();

  function navigateToNext() {
    const currentUser = authService.getCurrentUser();

    if (!currentUser) return; // Ensure there is a current user

    const userRoles = currentUser.roles;

    if (dynamicPath) {
      navigate(dynamicPath(currentUser));
    } else {
      if (userRoles.includes('ROLE_CUSTOMER')) {
        navigate(`/user/${label}`);
      } else if (userRoles.includes('ROLE_ADMIN')) {
        navigate(`/admin/${label}`);
      }
    }
  }


  return (
    <div
      className="inline-block cursor-pointer text-center m-2"
      onClick={navigateToNext}
    >
      <h2 className="text-5xl mb-2">{label}</h2>
      <img src={src} className="max-w-full h-auto" alt={label} />
    </div>
  );
};

export default HomeOption;
