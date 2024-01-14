import React from 'react';
import { useNavigate } from 'react-router-dom';
import authService from '../../Services/auth.service';

const HomeOption = ({ src, label }) => {
  const navigate = useNavigate();

  function navigateToNext() {
    const currentUser = authService.getCurrentUser();

    if (currentUser) {
      const userRoles = currentUser.roles;

      if (userRoles.includes('ROLE_CUSTOMER')) {
        navigate(`/customer/${label}`);
      } else if (userRoles.includes('ROLE_ADMIN')) {
        navigate(`/admin/${label}`);
      }
      // Add more conditions for other roles if needed
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
