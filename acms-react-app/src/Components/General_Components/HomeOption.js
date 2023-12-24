import React from 'react';
import { useNavigate } from 'react-router-dom';

const HomeOption = ({ src, label }) => {
  const navigate = useNavigate();

  function navigateToNext() {
    navigate(`/admin/${label}`);
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
